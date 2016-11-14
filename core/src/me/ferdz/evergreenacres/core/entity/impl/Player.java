package me.ferdz.evergreenacres.core.entity.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import me.ferdz.evergreenacres.core.entity.AbstractEntity;

public class Player extends AbstractEntity {
	private static final float SPEED = 40F;

	private Texture texture;
	private Body body;
	
	public Player() {
		texture = new Texture(Gdx.files.internal("player.png"));
	}
	
	public void createBody(World world, Vector2 position) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position.sub(0, 16));
		bodyDef.linearDamping = 8F;
		bodyDef.allowSleep = false;
		bodyDef.fixedRotation = true;
		
		body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(8, 7.9F); // height of 8 because 2.5D means 16 / 2
		 						  // also 7.9 so we can fit through boxes of half tile

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.0003f;
		fixtureDef.friction = 0.01f;
		
		body.createFixture(fixtureDef);
		shape.dispose();
	}
	
	@Override
	public void update(float delta) {
		Vector2 direction = new Vector2();
		if(Gdx.input.isKeyPressed(Keys.A)) {
			direction.x = -1;
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			direction.x = 1;
		}
		if(Gdx.input.isKeyPressed(Keys.W)) {
			direction.y = 1;
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			direction.y = -1;
		}
		if(direction.isZero())
			return;
		
		direction = direction.scl(SPEED * delta);
		body.applyLinearImpulse(direction, body.getPosition(), true);
		body.getLinearVelocity().set(body.getLinearVelocity().limit(SPEED));
	}

	@Override
	public void render(Batch batch) {
		batch.draw(texture, body.getPosition().x - (float)(texture.getWidth() / 2), body.getPosition().y - (float) (texture.getHeight() / 2) + 8F);
	}
	
	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}

	@Override
	public void dispose() {
		texture.dispose();
	}
}