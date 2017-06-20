package me.ferdz.evergreenacres.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Textures {
	public enum ItemTexture {
		WATERING_CAN(0, 0),
		HOE(0, 1),
		POTATO_POUCH(1, 0);
		
		private int x, y;
		private ItemTexture(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public TextureRegion getTexture() {
			return getItemTextures()[x][y];
		}
	}
	
	public enum IconTexture {
		DOOR;
		
		private Pixmap pixmap;
		private IconTexture() {
		}
		
		public Pixmap getPixmap() {
			if (pixmap == null) 
				pixmap = new Pixmap(Gdx.files.internal("homegrown/ui/icons_" + this.ordinal() + ".png"));
			return pixmap;
		}
	}
	
	public enum CropTexture {
		POTATO_0(0, 0),
		POTATO_1(0, 1),
		POTATO_2(0, 2),
		POTATO_3(0, 3),
		POTATO_4(0, 4),
		POTATO_5(0, 5),
		POTATO_6(0, 6),
		DEAD_0(1, 0),
		DEAD_1(1, 1),
		DEAD_2(1, 2);
		
		private int x, y;
		private CropTexture(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public TextureRegion getTexture() {
			return getCropTextures()[x][y];
		}
	}
	
	private static TextureRegion[][] itemTextures;
	public static TextureRegion[][] getItemTextures() {
		if (itemTextures == null)
			itemTextures = TextureRegion.split(new Texture(Gdx.files.internal("homegrown/ui/items.png")), 16, 16);
		return itemTextures;
	}
	
	private static TextureRegion[][] uiTextures;
	public static TextureRegion[][] getUiTextures() {
		if (uiTextures == null)
			uiTextures = TextureRegion.split(new Texture(Gdx.files.internal("homegrown/ui/ui.png")), 16, 16);
		return uiTextures;
	}
	
	private static TextureRegion[][] cropTextures;
	public static TextureRegion[][] getCropTextures() {
		if (cropTextures == null)
			cropTextures = TextureRegion.split(new Texture(Gdx.files.internal("homegrown/environment/crops.png")), 16, 32);
		return cropTextures;
	}
	
	public static TextureRegion getSelectedGroundTileTexture() {
		return getUiTextures()[3][0];
	}
}
