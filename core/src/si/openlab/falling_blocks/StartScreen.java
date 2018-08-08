package si.openlab.falling_blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StartScreen extends GameScreen {
    OrthographicCamera camera;
    ScreenViewport viewport;
    Stage stage;

    StartScreen(final FallingBlocks game) {
        super(game);

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        float centerX = (viewport.getWorldWidth() - MenuButton.width) / 2;
        float topY = viewport.getWorldHeight();

        stage.addActor(new MenuButton(centerX, topY - 100, "New game", this));
        stage.addActor(new MenuButton(centerX, topY - 200, "Continue", this));

        stage.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getTarget() instanceof MenuButton) {
                    MenuButton menuButton = (MenuButton) event.getTarget();

                    if (menuButton.text.equals("New game")) {
                        game.level = 1;
                        game.score = 0;
                        game.setScreen(new Gameplay(game));
                    }

                    if (menuButton.text.equals("Continue")) {
                        game.setScreen(new Gameplay(game));
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        viewport.apply();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        super.dispose();

    }
}
