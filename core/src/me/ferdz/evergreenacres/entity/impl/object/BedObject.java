package me.ferdz.evergreenacres.entity.impl.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceActionImpl;

import lombok.val;
import me.ferdz.evergreenacres.entity.AbstractEntity;
import me.ferdz.evergreenacres.entity.EnumDirection;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.tile.SoilTile;
import me.ferdz.evergreenacres.entity.impl.tile.Tile;
import me.ferdz.evergreenacres.rendering.EnumHumanAnimationType;
import me.ferdz.evergreenacres.screen.GameScreen;
import me.ferdz.evergreenacres.ui.AnimationFactory;
import me.ferdz.evergreenacres.utils.GameState;

public class BedObject extends AbstractEntity {
	
	private Rectangle rectangle;

	public BedObject(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	@Override
	public void update(float delta) {
		Player player = GameState.get().getPlayer();
		Rectangle playerRect = new Rectangle(player.getPosition().x, player.getPosition().y, 7.5f, 7.5f);
		if (!GameState.get().isChangingArea() && rectangle.contains(playerRect)) {
			// If the player is inside the bed, transition to the next day
			GameState.get().setChangingArea(true);
			GameScreen.instance.getGameRenderer().getMapRenderer().setAction(new SequenceActionImpl(
					AnimationFactory.getDarkenAction(),
					Actions.delay(0.5f),
					new RunnableAction() { 
						@Override
						public void run() {
							// Move the player outside of the bed
							val player = GameState.get().getPlayer();
							val body = player.getBody();
							player.setCurrentAnimation(EnumHumanAnimationType.STILL_LEFT);
							player.setCurrentDirection(EnumDirection.LEFT);
							body.setTransform(rectangle.x, rectangle.y, body.getAngle());

							// Do the processing to go to the next day
							for (int i = 0; i < GameState.get().getSoil().length; i++) {
								Tile[] row = GameState.get().getSoil()[i];
								for (int j = 0; j < row.length; j++) {
									Tile tile = row[j];
									if (tile instanceof SoilTile) {
										((SoilTile) tile).nextDay();
									}
								}
							}
						}
					},
					AnimationFactory.getLightenAction(),
					new RunnableAction() {
						public void run() {
							GameState.get().setChangingArea(false);
						}
					}
					));
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public Vector2 getPosition() {
		throw new UnsupportedOperationException();
	}
}
