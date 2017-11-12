package me.ferdz.evergreenacres.entity.impl.object;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.ferdz.evergreenacres.entity.AbstractEntity;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.screen.GameRenderer;
import me.ferdz.evergreenacres.screen.GameScreen;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Values;

public class TreeObject extends AbstractEntity {

    private Rectangle rectangle;
    public TreeObject(Rectangle rectangle) {
        super();

        this.rectangle = rectangle;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        GameScreen.instance.getGameRenderer().queueRender(new Runnable() {
            @Override
            public void run() {
                TextureRegion leavesTexture = Textures.SummerTextures.TREE_LEAVES.getTexture();
                batch.draw(leavesTexture, rectangle.x - (leavesTexture.getRegionWidth() / 2 - Values.TILE_WIDTH / 2), rectangle.y + Values.TILE_HEIGHT); // Render the leaves
            }
        });
        TextureRegion trunkTexture = Textures.SummerTextures.TREE_TRUNK.getTexture();
        batch.draw(trunkTexture, rectangle.x, rectangle.y);
    }

    @Override
    public Vector2 getPosition() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void dispose() {

    }
}
