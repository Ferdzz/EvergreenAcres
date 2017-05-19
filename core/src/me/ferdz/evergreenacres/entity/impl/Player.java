package me.ferdz.evergreenacres.entity.impl;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.google.common.eventbus.Subscribe;

import me.ferdz.evergreenacres.entity.AbstractEntity;
import me.ferdz.evergreenacres.entity.EnumDirection;
import me.ferdz.evergreenacres.item.Item;
import me.ferdz.evergreenacres.rendering.AnimationImpl;
import me.ferdz.evergreenacres.rendering.EnumHumanAnimationType;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.screen.GameScreen;
import me.ferdz.evergreenacres.ui.ItemBar;
import me.ferdz.evergreenacres.utils.Utils;
import me.ferdz.evergreenacres.utils.Values;
import me.ferdz.evergreenacres.utils.input.InputEvents;

public class Player extends AbstractEntity {
	private static final float SPEED = 100F;
	private static final float ACCELERATION = 2F;
	
	private HashMap<EnumHumanAnimationType, AnimationImpl> animations;
	private EnumHumanAnimationType currentAnimation;
	private EnumDirection currentDirection;
	private Body body;
	private Particle dustParticle;
	
	public Player() {
		// Init the animations
		this.initAnimations();
		// Set current direction to down
		this.currentDirection = EnumDirection.DOWN;
		// Init the dust particle
		this.dustParticle = new Particle(new Texture(Gdx.files.internal("s_kickdust1_strip8.png")), 12, 15, 0.08f);
		// Register the event bus
		Values.bus.register(this);
	}
	
	private void initAnimations() {
		this.animations = new HashMap<EnumHumanAnimationType, AnimationImpl>();

		// Load player animation textures
		Texture sheet = new Texture(Gdx.files.internal("player/light.png"));
		TextureRegion[][] tmp = TextureRegion.split(sheet, 64, 64);
		for (EnumHumanAnimationType type : EnumHumanAnimationType.getActionTypes()) {
			// Skip the first two frames of the animation for WALK_UP and WALK_DOWN
			int startIndex;
			if (type == EnumHumanAnimationType.WALK_UP || type == EnumHumanAnimationType.WALK_DOWN)
				startIndex = 1;
			else
				startIndex = 0;
			// TODO: Move those frames to the EnumHumanAnimationType
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

		this.currentAnimation = EnumHumanAnimationType.STILL_DOWN;

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
		shape.setAsBox(7.9F, 7.9F); // height of 8 because 2.5D means 16 / 2
		 						  	// 7.9 so we can fit through boxes of half tile

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.0003f;
		fixtureDef.friction = 0.1f;
		
		body.createFixture(fixtureDef);
		shape.dispose();
	}
	
	@Override
	public void update(float delta) {
		dustParticle.update(delta);
		
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
					currentDirection = EnumDirection.UP;
				} else if (s) {
					currentAnimation = EnumHumanAnimationType.WALK_DOWN;
					currentDirection = EnumDirection.DOWN;
				}
			} else if (w && s) {
				if (a) {
					currentAnimation = EnumHumanAnimationType.WALK_LEFT;
					currentDirection = EnumDirection.LEFT;
				} else if (d) {
					currentAnimation = EnumHumanAnimationType.WALK_RIGHT;
					currentDirection = EnumDirection.RIGHT;
				}
			// Single & diagonal direction cases	
			} else if (a) {
				currentAnimation = EnumHumanAnimationType.WALK_LEFT;
				currentDirection = EnumDirection.LEFT;
			} else if (d) {
				currentAnimation = EnumHumanAnimationType.WALK_RIGHT;
				currentDirection = EnumDirection.RIGHT;
			} else if (s) {
				currentAnimation = EnumHumanAnimationType.WALK_DOWN;
				currentDirection = EnumDirection.DOWN;
			} else if (w) {
				currentAnimation = EnumHumanAnimationType.WALK_UP;
				currentDirection = EnumDirection.UP;
			}
		}
			
		direction = direction.scl(ACCELERATION);
		body.applyLinearImpulse(direction, body.getWorldCenter(), true);
		body.setLinearVelocity(body.getLinearVelocity().limit(SPEED));
		
		// Dirt particle animation
		TiledMapTileLayer layer = (TiledMapTileLayer) GameScreen.instance.getCurrentArea().getMap().getLayers().get(Values.LAYER_GROUND);
		Vector2 position = Utils.toTilePos(body.getPosition());
		Cell cell = layer.getCell((int)position.x, (int)position.y);
		if (cell != null) {
			String type = (String) cell.getTile().getProperties().get(Values.KEY_TYPE);
			if (Values.TYPE_DIRT.equals(type)) { // If the tile under the player is dirt
				if (dustParticle.getPosition() == null) {
					Vector2 particlePos = body.getWorldCenter().cpy();
					particlePos.y -= 8;
					particlePos.x -= 4;
					dustParticle.setPosition(particlePos);
					dustParticle.resetAnimation();
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		dustParticle.render(batch);
		
		TextureRegion texture = animations.get(currentAnimation).getKeyFrame(Gdx.graphics.getDeltaTime(), true);
		float width = texture.getRegionWidth() * 0.8F;
		float height = texture.getRegionHeight() * 0.8F;
		batch.draw(texture, body.getPosition().x - width / 2, body.getPosition().y - (height / 2) + 15F,
				width, height);
		
		// Draw selector square
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			Vector2 tilePosition = Utils.offsetPos(Utils.toTilePos(this.getPosition()), currentDirection);
			Vector2 screenPosition = Utils.toWorldPos(tilePosition);
			TextureRegion region = Textures.getSelectedGroundTileTexture();
			batch.draw(region, screenPosition.x, screenPosition.y);
		}
	}
	
	// Action handlers
	@Subscribe
	public void onLeftClick(InputEvents.LeftClickEvent event) {
		ItemBar bar = GameScreen.instance.getItemBar();
		Item item = bar.getSelectedItem();
		if (item != null) {
			item.onItemUse(this, GameScreen.instance.getCurrentArea());
		}
	}

	@Override
	public void dispose() {
		for (AnimationImpl animationImpl : animations.values()) {
			animationImpl.dispose();
		}
		dustParticle.dispose();
	}
	
	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public EnumDirection getCurrentDirection() {
		return currentDirection;
	}
}
