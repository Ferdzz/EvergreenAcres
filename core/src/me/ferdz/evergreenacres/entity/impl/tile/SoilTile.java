package me.ferdz.evergreenacres.entity.impl.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import lombok.Getter;
import lombok.Setter;
import me.ferdz.evergreenacres.entity.EnumDirection;
import me.ferdz.evergreenacres.environment.Crop;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Utils;

public class SoilTile extends Tile {

	@Setter @Getter private boolean isWet = false;
	@Setter @Getter private Crop crop;
	
	public SoilTile(Vector2 position) {
		super(position);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		AbstractArea area = GameState.get().getCurrentArea(); 
		if (area instanceof FarmArea) {
			FarmArea farmArea = (FarmArea) area;
			boolean up, left, down, right;
			up = isSoil(EnumDirection.UP, farmArea);
			left = isSoil(EnumDirection.LEFT, farmArea);
			down = isSoil(EnumDirection.DOWN, farmArea);
			right = isSoil(EnumDirection.RIGHT, farmArea);
			
			TextureRegion texture = EnumSoilTexture.getConnectedTextureRegion(up, left, down, right, false);
			Vector2 renderPos = Utils.toWorldPos(position);
			batch.draw(texture, renderPos.x, renderPos.y);
			if (this.isWet) {
				up = isWet(EnumDirection.UP, farmArea);
				left = isWet(EnumDirection.LEFT, farmArea);
				down = isWet(EnumDirection.DOWN, farmArea);
				right = isWet(EnumDirection.RIGHT, farmArea);
				
				texture = EnumSoilTexture.getConnectedTextureRegion(up, left, down, right, true);
				batch.draw(texture, renderPos.x, renderPos.y);
			}

			// Render the current stage of the crop
			if (this.crop != null) {
				batch.draw(this.crop.getCurrentTexture(), renderPos.x, renderPos.y);
			}
		}
	}
	
	private boolean isSoil(EnumDirection direction, FarmArea area) {
		Vector2 offset = Utils.offsetPos(position, direction);
		try {
			if (area.soil[(int) offset.x][(int) offset.y] instanceof SoilTile) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// This means that the tile checking is outside the map
			return false;
		}
		
		return false;
	}
	
	private boolean isWet(EnumDirection direction, FarmArea area) {
		Vector2 offset = Utils.offsetPos(position, direction);
		try {
			return ((SoilTile)area.soil[(int) offset.x][(int) offset.y]).isWet;
		} catch (Exception e) {
			return false;
		}
	}
}
