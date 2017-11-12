package me.ferdz.evergreenacres.entity.impl.object;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import lombok.Getter;
import me.ferdz.evergreenacres.entity.AbstractEntity;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.screen.GameRenderer;
import me.ferdz.evergreenacres.screen.GameScreen;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Values;

public class TreeObject extends AbstractEntity {

    @Getter private Rectangle rectangle;
    @Getter private Body body;
    public TreeObject(AbstractArea area, Rectangle rectangle) {
        super();

        this.rectangle = rectangle;
        this.createBody(area);
    }

    private void createBody(AbstractArea area) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(this.rectangle.x + Values.TILE_WIDTH / 2, this.rectangle.y + Values.TILE_HEIGHT / 2));

        body = area.getWorld().createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8F, 8F);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        shape.dispose();
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
