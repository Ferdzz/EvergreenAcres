package me.ferdz.evergreenacres.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.google.common.eventbus.Subscribe;

import me.ferdz.evergreenacres.core.entity.IRenderable;
import me.ferdz.evergreenacres.core.entity.IUpdatable;
import me.ferdz.evergreenacres.core.item.Item;
import me.ferdz.evergreenacres.core.item.ItemHoe;
import me.ferdz.evergreenacres.core.item.ItemWaterCan;
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
		this.items[0] = new ItemWaterCan();
		this.items[1] = new ItemHoe();
		this.textures = TextureRegion.split(new Texture(Gdx.files.internal("homegrown/ui/ui.png")), Values.TILE_WIDTH, Values.TILE_HEIGHT);
		this.selectedIndex = ITEMS_COUNT / 2;
		
		Values.bus.register(this);
	}
	
	@Override
	public void update(float delta) {
		
	}
	
	@Override
	public void render(SpriteBatch batch) {
		float middle = Gdx.graphics.getWidth() >> 1;
		float scale = Values.TILE_WIDTH * 6;
		float x = middle - (ITEMS_COUNT * scale) / 2;
		float upperY = (scale * 2) - BOTTOM_PADDING;
		float contentY = (scale * 1) - BOTTOM_PADDING;
		float lowerY = -BOTTOM_PADDING;
		
		// Left bound
		batch.draw(textures[1][3], x - scale, contentY, scale, scale);
		for (int i = 0; i < ITEMS_COUNT; i++) {
			// Upper bound
			batch.draw(textures[0][4], x + (i * scale), upperY, scale, scale);
			// Cell content
			batch.draw(textures[1][4], x + (i * scale), contentY, scale, scale);
			// Lower bound
			batch.draw(textures[2][4], x + (i * scale), lowerY, scale, scale);
		}
		// Selected upper bound
		batch.draw(textures[0][0], x + ((selectedIndex - 1) * scale), upperY, scale, scale);
		batch.draw(textures[0][1], x + (selectedIndex * scale), upperY, scale, scale);
		batch.draw(textures[0][2], x + ((selectedIndex + 1) * scale), upperY, scale, scale);
		// Selected content
		batch.draw(textures[1][1], x + (selectedIndex * scale), contentY, scale, scale);
		// Selected lower bound
		batch.draw(textures[2][0], x + ((selectedIndex - 1) * scale), lowerY, scale, scale);
		batch.draw(textures[2][1], x + (selectedIndex * scale), lowerY, scale, scale);
		batch.draw(textures[2][2], x + ((selectedIndex + 1) * scale), lowerY, scale, scale);
		
		// Draw items
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				items[i].renderInInventory(batch, (int) (x + (i * scale)), (int) contentY, scale);
			}
		}
		// Selected left bound
		batch.draw(textures[1][0], x + ((selectedIndex - 1) * scale), contentY, scale, scale);
		// Selected right bound
		batch.draw(textures[1][2], x + ((selectedIndex + 1) * scale), contentY, scale, scale);
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
	
	public Item[] getItems() {
		return items;
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
}
