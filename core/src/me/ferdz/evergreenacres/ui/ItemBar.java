package me.ferdz.evergreenacres.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.google.common.eventbus.Subscribe;

import me.ferdz.evergreenacres.core.entity.IRenderable;
import me.ferdz.evergreenacres.core.entity.IUpdatable;
import me.ferdz.evergreenacres.core.item.Item;
import me.ferdz.evergreenacres.utils.Values;
import me.ferdz.evergreenacres.utils.input.InputEvents;

public class ItemBar implements IUpdatable, IRenderable, Disposable {
	public static final int ITEMS_COUNT = 9;
	private static final int BOTTOM_PADDING = 60;
	
	private Item[] items;
	private TextureRegion[][] textures;
	private int selectedIndex;
	public ItemBar() {
		this.items = new Item[ITEMS_COUNT];
		this.textures = TextureRegion.split(new Texture(Gdx.files.internal("ui/homegrown/ui.png")), Values.TILE_WIDTH, Values.TILE_HEIGHT);
		this.selectedIndex = ITEMS_COUNT / 2;
		
		Values.bus.register(this);
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
		// Selected upper bound
		batch.draw(textures[0][0], x + ((selectedIndex - 1) * width), (width * 2) - BOTTOM_PADDING, width, width);
		batch.draw(textures[0][1], x + (selectedIndex * width), (width * 2) - BOTTOM_PADDING, width, width);
		batch.draw(textures[0][2], x + ((selectedIndex + 1) * width), (width * 2) - BOTTOM_PADDING, width, width);
		// Selected content
		batch.draw(textures[1][0], x + ((selectedIndex - 1) * width), (width * 1) - BOTTOM_PADDING, width, width);
		batch.draw(textures[1][1], x + (selectedIndex * width), (width * 1) - BOTTOM_PADDING, width, width);
		batch.draw(textures[1][2], x + ((selectedIndex + 1) * width), (width * 1) - BOTTOM_PADDING, width, width);
		// Selected lower bound
		batch.draw(textures[2][0], x + ((selectedIndex - 1) * width), (width * 0) - BOTTOM_PADDING, width, width);
		batch.draw(textures[2][1], x + (selectedIndex * width), (width * 0) - BOTTOM_PADDING, width, width);
		batch.draw(textures[2][2], x + ((selectedIndex + 1) * width), (width * 0) - BOTTOM_PADDING, width, width);
	}
	
	@Subscribe
	public void onScroll(InputEvents.ScrollEvent event) {
		int destinationIndex = this.selectedIndex - event.getAmount();
		if (destinationIndex < 0) {
			destinationIndex = ITEMS_COUNT - 1;
		} else if (destinationIndex > ITEMS_COUNT - 1) {
			destinationIndex = 0;
		}
		this.selectedIndex = destinationIndex;
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
