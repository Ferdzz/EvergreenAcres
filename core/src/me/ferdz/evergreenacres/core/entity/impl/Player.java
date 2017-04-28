package me.ferdz.evergreenacres.core.entity.impl;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import me.ferdz.evergreenacres.core.entity.AbstractEntity;
import me.ferdz.evergreenacres.core.rendering.AnimationImpl;
import me.ferdz.evergreenacres.core.rendering.EnumHumanAnimationType;
import me.ferdz.evergreenacres.core.screen.GameScreen;

public class Player extends AbstractEntity {
	private static final float SPEED = 100F;
	private static final float ACCELERATION = 2F;
	
	private HashMap<EnumHumanAnimationType, AnimationImpl> animations;
	private EnumHumanAnimationType currentAnimation;
	private Body body;
	private Animation<TextureRegion> dustAnimation;
	
	public Player() {
		this.animations = new HashMap<EnumHumanAnimationType, AnimationImpl>();

		// Load player animation textures
		// TODO: Move this to a cleaner more generic place
		Texture sheet = new Texture(Gdx.files.internal("player/light.png"));
		TextureRegion[][] tmp = TextureRegion.split(sheet, 64, 64);
		for (EnumHumanAnimationType type : EnumHumanAnimationType.getActionTypes()) {
			// Skip the first two frames of the animation for WALK_UP and WALK_DOWN
			int startIndex;
			if (type == EnumHumanAnimationType.WALK_UP || type == EnumHumanAnimationType.WALK_DOWN)
				startIndex = 1;
			else
				startIndex = 0;
			ArrayList<TextureRegion> frames = new ArrayList<TextureRegion>();
			for (int i = startIndex; i < type.getLength(); i++) {
				frames.add(tmp[type.ordinal()][i]);
			}
			AnimationImpl animation = new AnimationImpl(0.12F, frames.toArray(new TextureRegion[frames.size()]));
			this.animations.put(type, animation);
		}
		
		// Init the still animations
		this.animations.put(EnumHumanAnimationType.STILL_UP, new AnimationImpl(1, tmp[EnumHumanAnimationType.WALK_UP.ordinal()][0]));
		this.animations.put(EnumHumanAnimationType.STILL_LEFT, new AnimationImpl(1, tmp[EnumHumanAnimationType.WALK_LEFT.ordinal()][0]));
		this.animations.put(EnumHumanAnimationType.STILL_DOWN, new AnimationImpl(1, tmp[EnumHumanAnimationType.WALK_DOWN.ordinal()][0]));
		this.animations.put(EnumHumanAnimationType.STILL_RIGHT, new AnimationImpl(1, tmp[EnumHumanAnimationType.WALK_RIGHT.ordinal()][0]));

		this.currentAnimation = EnumHumanAnimationType.CAST_DOWN;
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
		fixtureDef.friction = 0.1f;
		
		body.createFixture(fixtureDef);
		shape.dispose();
	}
	
	@Override
	public void update(float delta) {
		Vector2 direction = new Vector2();
		boolean w = false,
				a = false,
				s = false,
				d = false;
		if(Gdx.input.isKeyPressed(Keys.W)) {
			direction.y += 1;
			w = true;
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			direction.y -= 1;
			s = true;
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			direction.x -= 1;
			a = true;
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			direction.x += 1;
			d = true;
		}
		if(direction.isZero()) { // If body stopped moving
			if (body.getLinearVelocity().len() < 35) {
				body.setLinearVelocity(Vector2.Zero);
				// Set the animation to a still image when not moving
				
				switch (currentAnimation) {
				case WALK_UP:
					currentAnimation = EnumHumanAnimationType.STILL_UP;
					break;
				case WALK_LEFT:
					currentAnimation = EnumHumanAnimationType.STILL_LEFT;
					break;
				case WALK_DOWN:
					currentAnimation = EnumHumanAnimationType.STILL_DOWN;
					break;
				case WALK_RIGHT:
					currentAnimation = EnumHumanAnimationType.STILL_RIGHT;
					break;
				}
				
			}
			return;
		} else {
			// Edge cases
			if (a && d) {
				if (w) {
					currentAnimation = EnumHumanAnimationType.WALK_UP;
				} else if (s) {
					currentAnimation = EnumHumanAnimationType.WALK_DOWN;
				}
			} else if (w && s) {
				if (a) {
					currentAnimation = EnumHumanAnimationType.WALK_LEFT;
				} else if (d) {
					currentAnimation = EnumHumanAnimationType.WALK_RIGHT;
				}
			// Single & diagonal direction cases	
			} else if (a) {
				currentAnimation = EnumHumanAnimationType.WALK_LEFT;
			} else if (d) {
				currentAnimation = EnumHumanAnimationType.WALK_RIGHT;
			} else if (s) {
				currentAnimation = EnumHumanAnimationType.WALK_DOWN;
			} else if (w) {
				currentAnimation = EnumHumanAnimationType.WALK_UP;
			}
		}
			
		direction = direction.scl(ACCELERATION);
		body.applyLinearImpulse(direction, body.getWorldCenter(), true);
		body.setLinearVelocity(body.getLinearVelocity().limit(SPEED));
		
		// Dirt particle animation
		TiledMapTileLayer layer = (TiledMapTileLayer) GameScreen.instance.getCurrentArea().getMap().getLayers().get("Ground");
		String type = (String) layer.getCell(Math.round(body.getPosition().x / layer.getTileWidth()), Math.round(body.getPosition().y / layer.getTileHeight())).getTile().getProperties().get("type");
		if ("dirt".equals(type)) { // If the tile under the player is dirt
			Texture sheet = new Texture(Gdx.files.internal("s_kickdust1_strip8.png"));
			dustAnimation = new AnimationImpl(0.1F, TextureRegion.split(sheet, 12, 15)[0]);
		}
	}

	@Override
	public void render(Batch batch) {
		TextureRegion texture = animations.get(currentAnimation).getKeyFrame(Gdx.graphics.getDeltaTime(), true);
		float width = texture.getRegionWidth() * 0.8F;
		float height = texture.getRegionHeight() * 0.8F;
		batch.draw(texture, body.getPosition().x - width / 2, body.getPosition().y - (height / 2) + 15F,
				width, height);
		if (dustAnimation != null)
			batch.draw(dustAnimation.getKeyFrame(Gdx.graphics.getDeltaTime(), false), body.getPosition().x, body.getPosition().y);
	}
	
	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public Vector2 getCenterPosition() {
		return body.getWorldCenter();
	}

	@Override
	public void dispose() {
		for (AnimationImpl animationImpl : animations.values()) {
			for (TextureRegion region : animationImpl.getKeyFrames()) {
				region.getTexture().dispose();
			}
		}
//		texture.dispose();
	}
}
