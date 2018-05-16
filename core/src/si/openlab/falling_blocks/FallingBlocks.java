package si.openlab.falling_blocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FallingBlocks extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;

    OrthographicCamera camera;
    FitViewport viewport;

    boolean firstClicked;
    boolean secondClicked;
    boolean thirdClicked;

    @Override
    public void create() {
        camera = new OrthographicCamera();

        // ustvari viewport, ki omeji velikost igre na velikost okna
        viewport = new FitViewport(720, 480, camera);
        // centriraj igro
        viewport.apply(true);

        batch = new SpriteBatch();
        img = new Texture("square.png");
    }

    @Override
    public void render() {
        // pobrisi zaslon
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isButtonPressed(0)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x >= 0 && x <= 0 + 100 && y >= 0 && y <= 0 + 100) {
                firstClicked = true;
            }

            if (x >= 200 && x <= 200 + 100 && y >= 150 && y <= 150 + 100) {
                secondClicked = true;
            }

            if (x >= 400 && x <= 400 + 100 && y >= 300 && y <= 300 + 100) {
                thirdClicked = true;
            }
        }

        // uporabi kamero
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // izrisi igro
        batch.begin();

        if (!firstClicked) {
            batch.setColor(1, 0, 0, 1);
            batch.draw(img, 0, 0, 100, 100);
        }

        if (!secondClicked) {
            batch.setColor(0, 1, 0, 1);
            batch.draw(img, 200, 150, 100, 100);
        }

        if (!thirdClicked) {
            batch.setColor(0, 0, 1, 1);
            batch.draw(img, 400, 300, 100, 100);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // posodobi viewport z novo velikostjo zalona
        // (popravi parametre kamere, da ta prikazuje celo igro, omejeno z velikostjo okna)
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}