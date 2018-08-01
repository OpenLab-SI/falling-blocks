package si.openlab.falling_blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class GameScreen extends ScreenAdapter {
    FallingBlocks game;
    OrthographicCamera camera;
    ScreenViewport viewport;
    SpriteBatch batch;
    BitmapFont font;

    GameScreen(FallingBlocks game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        batch = new SpriteBatch();
        font = game.assets.get("game.ttf");
    }

    @Override
    public void render(float delta) {
        // pobrisi zaslon
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        String levelText = "Level: " + game.level;
        String scoreText = "Score: " + game.score;

        GlyphLayout scoreLayout = new GlyphLayout();
        scoreLayout.setText(font, scoreText);

        font.draw(
                batch,
                levelText,
                16,
                Gdx.graphics.getHeight() - scoreLayout.height);

        font.draw(
                batch,
                scoreText,
                Gdx.graphics.getWidth() - scoreLayout.width - 16,
                Gdx.graphics.getHeight() - scoreLayout.height);

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
