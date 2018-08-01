package si.openlab.falling_blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StartScreen extends GameScreen {
    OrthographicCamera camera;
    ScreenViewport viewport;
    SpriteBatch batch;
    BitmapFont font;

    StartScreen(FallingBlocks game) {
        super(game);

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        batch = new SpriteBatch();
        font = game.assets.get("title.ttf");
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isTouched()) {
            game.setScreen(new Gameplay(game));
            return;
        }

        viewport.apply();

        String text = "Level " + game.level;

        GlyphLayout layout = new GlyphLayout(font, text);

        float x = (Gdx.graphics.getWidth() - layout.width) / 2f;
        float y = (Gdx.graphics.getHeight() + layout.height) / 2f;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, text, x, y);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
