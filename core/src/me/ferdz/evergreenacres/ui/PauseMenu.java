package me.ferdz.evergreenacres.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;
import me.ferdz.evergreenacres.utils.Values;
import me.ferdz.evergreenacres.utils.input.InputEvents;

import java.io.File;

public class PauseMenu extends Table {

    @Getter private boolean isGamePaused;

    public PauseMenu() {
        isGamePaused = false;

        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        TextButton.TextButtonStyle btnResumeStyle = skin.get("TextButton", TextButton.TextButtonStyle.class);
        TextButton btnResume = new TextButton("Resume", btnResumeStyle);

        add(btnResume);

        Values.bus.register(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        draw(batch, parentAlpha);

        super.draw(batch, parentAlpha);
    }

    @Subscribe
    public void onKeyTyped(InputEvents.KeyPressedEvent event) {
        if (event.getKey() == Input.Keys.ESCAPE) {
            isGamePaused = !isGamePaused;
            System.out.println(isGamePaused);
        }
    }

}
