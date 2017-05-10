package me.ferdz.evergreenacres.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;

import me.ferdz.evergreenacres.core.entity.IRenderable;
import me.ferdz.evergreenacres.core.entity.IUpdatable;
import me.ferdz.evergreenacres.core.item.Item;
import me.ferdz.evergreenacres.utils.Values;

public class ItemBar implements IUpdatable, IRenderable, Disposable {
	public static final int ITEMS_COUNT = 9;
	private static final int BOTTOM_PADDING = 60;
	
	private Item[] items;
	private TextureRegion[][] textures;
	public ItemBar() {
		this.items = new Item[ITEMS_COUNT];
		this.textures = TextureRegion.split(new Texture(Gdx.files.internal("ui/homegrown/ui.png")), Values.TILE_WIDTH, Values.TILE_HEIGHT);
	}
	
	@Override
	public void update(float delta) {
		
	}
	
	@Override
	public void render(Batch batch) {
		float middle = Gdx.graphics.getWidth() / 2;
		float width = Values.TILE_WIDTH * 6;
		float x = middle - (ITEMS_COUNT * width) / 2;
		// Left bound
		batch.draw(textures[1][3], x - width, (width * 1) - BOTTOM_PADDING, width, width);
		for (int i = 0; i < ITEMS_COUNT; i++) {
			// Upper bound
			batch.draw(textures[0][4], x + (i * width), (width * 2) - BOTTOM_PADDING, width, width);
			// Cell content
			batch.draw(textures[1][4], x + (i * width), (width * 1) - BOTTOM_PADDING, width, width);
			// Lower bound
			batch.draw(textures[2][4], x + (i * width), (width * 0) - BOTTOM_PADDING, width, width);
		}

	}

	@Override
	public void dispose() {
		for (TextureRegion[] textureRegions : textures) {
			for (TextureRegion textureRegion : textureRegions) {
				textureRegion.getTexture().dispose();
			}
		}
	}
}
