package si.openlab.falling_blocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FallingBlocks extends ApplicationAdapter {
    AssetManager assets;
    OrthographicCamera camera;
    FitViewport viewport;

    @Override
    public void create() {
        assets = new AssetManager();

        // zacne nalagati square.png kot teksturo
        assets.load("square.png", Texture.class);
        // pocaka, da se nalozijo vse datoteke
        assets.finishLoading();

        camera = new OrthographicCamera();

        // ustvari viewport, ki omeji velikost igre na velikost okna
        viewport = new FitViewport(720, 480, camera);
        // centriraj igro
        viewport.apply(true);
    }

    @Override
    public void render() {
        // pobrisi zaslon
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // uporabi kamero
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        // posodobi viewport z novo velikostjo zalona
        // (popravi parametre kamere, da ta prikazuje celo igro, omejeno z velikostjo okna)
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}