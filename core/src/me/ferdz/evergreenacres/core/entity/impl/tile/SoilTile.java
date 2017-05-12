package me.ferdz.evergreenacres.core.entity.impl.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.core.entity.EnumDirection;
import me.ferdz.evergreenacres.core.screen.GameScreen;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.utils.Utils;

public class SoilTile extends Tile {

	public SoilTile(Vector2 position) {
		super(position);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		AbstractArea area = GameScreen.instance.getCurrentArea(); 
		if (area instanceof FarmArea) {
			FarmArea farmArea = (FarmArea) area;
			boolean up, left, down, right;
			up = isSoil(EnumDirection.UP, farmArea);
			left = isSoil(EnumDirection.LEFT, farmArea);
			down = isSoil(EnumDirection.DOWN, farmArea);
			right = isSoil(EnumDirection.RIGHT, farmArea);
			
			TextureRegion texture = EnumSoilTexture.getConnectedTextureRegion(up, left, down, right);
			batch.draw(texture, position.x * texture.getRegionWidth(), position.y * texture.getRegionHeight());
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
}
