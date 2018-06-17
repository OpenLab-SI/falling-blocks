package si.openlab.falling_blocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FallingBlocks extends ApplicationAdapter {
    AssetManager assets;
    OrthographicCamera camera;
    FitViewport viewport;
    Stage stage;
    World world;

    @Override
    public void create() {
        assets = new AssetManager();

        // zacne nalagati square.png kot teksturo
        assets.load("square.png", Texture.class);
        // pocaka, da se nalozijo vse datoteke
        assets.finishLoading();

        camera = new OrthographicCamera();

        // ustvari viewport, ki omeji velikost igre na velikost okna
        viewport = new FitViewport(7.2f, 4.8f, camera);
        // centriraj igro
        viewport.apply(true);

        // dodana scena (ali oder) nase igre
        // na tem "stage" se bo nahajala vecina elementov nase igre
        stage = new Stage(viewport);

        // dodan svet za fizikalne objekte
        world = new World(new Vector2(0, -9.8f), true);

        // nastavimo, da stage procesira vhode (klike ipd.)
        Gdx.input.setInputProcessor(stage);

        // dodamo kvadrate
        stage.addActor(new Square(0, 0, Color.RED, assets));
        stage.addActor(new Square(1.5f, 1.5f, Color.BLUE, assets));
        stage.addActor(new Square(3, 3, Color.GREEN, assets));
    }

    @Override
    public void render() {
        // pobrisi zaslon
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // uporabi kamero
        camera.update();

        // izrisemo celotno sceno
        stage.draw();
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