package me.ferdz.evergreenacres.entity.impl.object;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import lombok.Getter;
import me.ferdz.evergreenacres.entity.AbstractEntity;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.screen.GameRenderer;
import me.ferdz.evergreenacres.screen.GameScreen;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.InterpolationWrapper;
import me.ferdz.evergreenacres.utils.Values;

import java.util.Random;

public class TreeObject extends AbstractEntity {

    @Getter private Rectangle rectangle;
    @Getter private Body body;
    private int hitCounter = 0;
    private InterpolationWrapper interpolation;

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
                if (interpolation != null) {
                    float val = interpolation.progress(Gdx.graphics.getDeltaTime() * 3);

                    batch.draw(leavesTexture,
                            rectangle.x - (leavesTexture.getRegionWidth() / 2 - Values.TILE_WIDTH / 2),
                            rectangle.y + Values.TILE_HEIGHT,
                            leavesTexture.getRegionWidth() / 2,
                            0,
                            leavesTexture.getRegionWidth(),
                            leavesTexture.getRegionHeight(),
                            1,
                            1,
                            val * 8); // Render the leaves
                } else {
                    batch.draw(leavesTexture,
                            rectangle.x - (leavesTexture.getRegionWidth() / 2 - Values.TILE_WIDTH / 2),
                            rectangle.y + Values.TILE_HEIGHT,
                            0,
                            0,
                            leavesTexture.getRegionWidth(),
                            leavesTexture.getRegionHeight(),
                            1,
                            1,
                            0); // Render the leaves

                }

            }
        });

        TextureRegion trunkTexture = Textures.SummerTextures.TREE_TRUNK.getTexture();
        batch.draw(trunkTexture, rectangle.x, rectangle.y);
    }

    public void onAxeHit(Player player, AbstractArea area) {
        this.interpolation = new InterpolationWrapper(Interpolation.smooth);
        this.interpolation.setComesBack(true);

        // Check the player facing position to animate the tree in the proper direction
        if (player.getPosition().x > this.rectangle.getX()) {
            this.interpolation.setBackwards(false);
        } else {
            this.interpolation.setBackwards(true);
        }

        this.hitCounter++;
        if (this.hitCounter == 4) {
            // Destroy the tree
            area.getEntities().remove(this);
            area.getWorld().destroyBody(this.getBody());
            this.dispose();
        }
    }

    @Override
    public Vector2 getPosition() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void dispose() {

    }
}
