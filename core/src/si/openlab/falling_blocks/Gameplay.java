package si.openlab.falling_blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Gameplay extends ScreenAdapter {
    FallingBlocks game;
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

    Gameplay(FallingBlocks game) {
        this.game = game;
        font = game.assets.get("game.ttf");

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
    public void render(float delta) {
        // pobrisi zaslon
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timer += delta;

        if (timer > 0.5f && remainingSquares > 0) {
            timer -= 0.5f;
            remainingSquares--;

            float x = MathUtils.random(viewport.getWorldWidth() - Square.size);
            float y = viewport.getWorldHeight();
            Color color = Square.colors[MathUtils.random(Square.colors.length - 1)];

            stage.addActor(new Square(x, y, color, this));
        }

        if (stage.getActors().size == 0 && remainingSquares == 0) {
            game.setScreen(new StartScreen(game));
            return;
        }

        // simuliranje fizike sveta
        world.step(delta, 6, 2);

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
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        hud.dispose();
        debugRenderer.dispose();
    }
}
