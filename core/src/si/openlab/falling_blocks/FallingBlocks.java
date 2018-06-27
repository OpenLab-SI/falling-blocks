package si.openlab.falling_blocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class FallingBlocks extends ApplicationAdapter {
    AssetManager assets;
    BitmapFont font;

    OrthographicCamera camera;
    FitViewport viewport;

    Stage stage;
    World world;

    OrthographicCamera hudCamera;
    ScreenViewport hudViewport;
    SpriteBatch hud;

    Box2DDebugRenderer debugRenderer;

    int score;
    int remainingSquares = 10;
    float timer;

    @Override
    public void create() {
        assets = new AssetManager();

        FileHandleResolver resolver = new InternalFileHandleResolver();
        assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreeTypeFontLoaderParameter fontParameter = new FreeTypeFontLoaderParameter();
        fontParameter.fontFileName = "whitrabt.ttf";
        fontParameter.fontParameters.size = 24;

        // zacne nalagati square.png kot teksturo
        assets.load("square.png", Texture.class);
        assets.load("font.ttf", BitmapFont.class, fontParameter);

        // pocaka, da se nalozijo vse datoteke
        assets.finishLoading();

        font = assets.get("font.ttf");

        camera = new OrthographicCamera();

        // ustvari viewport, ki omeji velikost igre na velikost okna
        viewport = new FitViewport(7.2f, 4.8f, camera);
        // centriraj igro
        viewport.apply(true);

        // dodana scena (ali oder) nase igre
        // na tem "stage" se bo nahajala vecina elementov nase igre
        stage = new Stage(viewport);

        // dodan svet za fizikalne objekte
        world = new World(new Vector2(0, 0), true);

        // nastavimo, da stage procesira vhode (klike ipd.)
        Gdx.input.setInputProcessor(stage);

        hudCamera = new OrthographicCamera();
        hudViewport = new ScreenViewport(hudCamera);
        hud = new SpriteBatch();

        // omogoca izris fizikalnega modela igre (uporabno za iskanje napak)
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render() {
        // pobrisi zaslon
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timer += Gdx.graphics.getDeltaTime();

        if (timer > 0.5f && remainingSquares > 0) {
            timer -= 0.5f;
            remainingSquares--;

            float x = MathUtils.random(viewport.getWorldWidth() - Square.size);
            float y = viewport.getWorldHeight();
            Color color = Square.colors[MathUtils.random(Square.colors.length - 1)];

            stage.addActor(new Square(x, y, color, this));
        }

        if (stage.getActors().size == 0 && remainingSquares == 0) {
            // konec igre
        }

        // simuliranje fizike sveta
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        // uporabi kamer
        viewport.apply();

        // posodobi polozaje kvadratov
        // bolj splosno, posodobi lastnosti, ki so odvisne od fizike
        stage.act();

        // izrisemo celotno sceno
        stage.draw();

        // izrisemo fizikalni model igre
        debugRenderer.render(world, camera.combined);

        hudViewport.apply();
        hud.setProjectionMatrix(hudCamera.combined);
        hud.begin();

        String scoreText = Integer.toString(score);

        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, scoreText);

        font.draw(hud, scoreText, Gdx.graphics.getWidth() - layout.width - 16, Gdx.graphics.getHeight() - layout.height);

        hud.end();
    }

    @Override
    public void resize(int width, int height) {
        // posodobi viewport z novo velikostjo zalona
        // (popravi parametre kamere, da ta prikazuje celo igro, omejeno z velikostjo okna)
        viewport.update(width, height);
        hudViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        assets.dispose();
        world.dispose();
        hud.dispose();
        debugRenderer.dispose();
    }
}