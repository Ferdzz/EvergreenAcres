package me.ferdz.evergreenacres.entity.impl.tile;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import lombok.Getter;
import lombok.Setter;
import me.ferdz.evergreenacres.entity.AbstractHoverTile;
import me.ferdz.evergreenacres.entity.EnumDirection;
import me.ferdz.evergreenacres.environment.Crop;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.rendering.Textures.IconTexture;
import me.ferdz.evergreenacres.screen.GameScreen;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Utils;
import me.ferdz.evergreenacres.utils.Values;

public class SoilTile extends AbstractHoverTile {

	@Setter @Getter private boolean isWet = false;
	@Setter @Getter private Crop crop;

	public SoilTile(Vector2 position) {
		super(position, 
			  new Rectangle(position.x * 16, position.y * 16, Values.TILE_WIDTH, Values.TILE_HEIGHT));
	}
	
	@Override
	public void render(SpriteBatch batch) {
		AbstractArea area = GameState.get().getCurrentArea(); 
		if (area instanceof FarmArea) {
			boolean up, left, down, right;
			up = isSoil(EnumDirection.UP);
			left = isSoil(EnumDirection.LEFT);
			down = isSoil(EnumDirection.DOWN);
			right = isSoil(EnumDirection.RIGHT);
			
			TextureRegion texture = EnumSoilTexture.getConnectedTextureRegion(up, left, down, right, false);
			Vector2 renderPos = Utils.toWorldPos(position);
			batch.draw(texture, renderPos.x, renderPos.y);
			if (this.isWet) {
				up = isWet(EnumDirection.UP);
				left = isWet(EnumDirection.LEFT);
				down = isWet(EnumDirection.DOWN);
				right = isWet(EnumDirection.RIGHT);
				
				texture = EnumSoilTexture.getConnectedTextureRegion(up, left, down, right, true);
				batch.draw(texture, renderPos.x, renderPos.y);
			}

			// Render the current stage of the crop
			if (this.crop != null) {
				Vector2 playerPos = GameState.get().getPlayer().getPosition();
				Random random = new Random((long)(this.position.x + this.position.y));
				if (playerPos.y < renderPos.y + Values.TILE_HEIGHT) {
					batch.draw(this.crop.getCurrentTexture(random), renderPos.x, renderPos.y);					
				} else {
					// Render the crop delayed
					GameScreen.instance.getGameRenderer().queueRender(() -> {
						batch.draw(this.crop.getCurrentTexture(random), renderPos.x, renderPos.y);					
					});
				}
			}
		}
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
	
	public void nextDay() {
		if (this.crop != null) {
			this.crop.grow(this.isWet);
		}
		this.isWet = false;
	}
	
	private boolean isSoil(EnumDirection direction) {
		Vector2 offset = Utils.offsetPos(position, direction);
		try {
			if (GameState.get().getSoil()[(int) offset.x][(int) offset.y] instanceof SoilTile) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// This means that the tile checking is outside the map
			return false;
		}
		
		return false;
	}
	
	private boolean isWet(EnumDirection direction) {
		Vector2 offset = Utils.offsetPos(position, direction);
		try {
			return ((SoilTile)GameState.get().getSoil()[(int) offset.x][(int) offset.y]).isWet;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void onInteract() {
		// TODO: Make plant harvestable
	}

	@Override
	public IconTexture getCursorIcon() {
		if (this.crop != null) {
			return crop.getCropType().getIcon();
		}
		return null;
	}
}
