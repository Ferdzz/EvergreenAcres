package me.ferdz.evergreenacres.rendering

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

object Textures {
	enum class ItemTexture private constructor(x: Int, y: Int) {
		WATERING_CAN(0, 0),
		HOE(0, 1);

		val x: Int
		val y: Int

		init {
			this.x = x
			this.y = y
		}
	}
	
	@JvmStatic
	val items: Array<Array<TextureRegion>> by lazy {
		TextureRegion.split(Texture(Gdx.files.internal("homegrown/ui/items.png")), 16, 16)
	}
	
	@JvmStatic
	fun getTextureRegion(texture: ItemTexture): TextureRegion {
		return items[texture.x][texture.y];
	}
	
	@JvmStatic
	val uiTextures: Array<Array<TextureRegion>> by lazy {
		TextureRegion.split(Texture(Gdx.files.internal("homegrown/ui/ui.png")), 16, 16)
	}
	
	@JvmStatic
	val selectedGroundTileTexture: TextureRegion by lazy {
		uiTextures[3][0]		
	}
}