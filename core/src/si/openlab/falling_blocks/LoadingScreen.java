package si.openlab.falling_blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoadingScreen extends ScreenAdapter {
    FallingBlocks game;
    BitmapFont font;

    OrthographicCamera camera;
    ScreenViewport viewport;
    SpriteBatch batch;

    LoadingScreen(FallingBlocks game) {
        this.game = game;

        game.assets.finishLoadingAsset("title.ttf");
        font = game.assets.get("title.ttf");

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        if (game.assets.update()) {
            game.setScreen(new StartScreen(game));
            return;
        }

        // pobrisi zaslon
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();

        GlyphLayout layout = new GlyphLayout(font, "Loading...");

        float x = (Gdx.graphics.getWidth() - layout.width) / 2f;
        float y = (Gdx.graphics.getHeight() + layout.height) / 2f;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Loading...",  x, y);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
