package me.ferdz.evergreenacres.core.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Textures {
	
	public enum ItemTexture {
		WATERING_CAN(0, 0),
		HOE(0, 1);
		
		private int x, y;
		private ItemTexture(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
	}
	private static TextureRegion[][] items;
	public static TextureRegion getTextureRegion(ItemTexture item) {
		if (items == null)
			items = TextureRegion.split(new Texture(Gdx.files.internal("ui/homegrown/items.png")), 16, 16);
		return items[item.x][item.y];
	}
}
