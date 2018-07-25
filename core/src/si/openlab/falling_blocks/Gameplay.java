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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
    float timer;

    float dropDelay;
    int remainingSquares;
    int requiredScore;

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


        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                // ce ni pritisnjen presledek, ne naredimo nic
                if (keycode != 62) {
                    return false;
                }

                for (Actor actor : stage.getActors()) {
                    // zanimajo nas samo kvadrati
                    if (actor instanceof Square) {
                        // spremenimo igralca v kvadrat, if stavek poskrbi, da je to mogoce
                        Square square = (Square) actor;

                        // rahlo zavrtimo kvadrat
                        square.body.applyAngularImpulse(100, true);
                    }
                }

                return true;
            }
        });

        // dodan svet za fizikalne objekte
        world = new World(new Vector2(0, 0), true);

        // nastavimo, da stage procesira vhode (klike ipd.)
        Gdx.input.setInputProcessor(stage);

        hudCamera = new OrthographicCamera();
        hudViewport = new ScreenViewport(hudCamera);
        hud = new SpriteBatch();

        // omogoca izris fizikalnega modela igre (uporabno za iskanje napak)
        debugRenderer = new Box2DDebugRenderer();

        // nastavimo vrednosti za level
        if (game.level == 1) {
            dropDelay = 1.5f;
            remainingSquares = 10;
            requiredScore = 5;
        } else if (game.level == 2) {
            dropDelay = 1f;
            remainingSquares = 30;
            requiredScore = 20;
        } else {
            dropDelay = 0.5f;
            remainingSquares = 100;
            requiredScore = 80;
        }
    }

    @Override
    public void render(float delta) {
        // pobrisi zaslon
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timer += delta;

        if (timer > dropDelay && remainingSquares > 0) {
            timer -= dropDelay;
            remainingSquares--;

            float x = MathUtils.random(viewport.getWorldWidth() - Square.size);
            float y = viewport.getWorldHeight();
            Color color = Square.colors[MathUtils.random(Square.colors.length - 1)];

            stage.addActor(new Square(x, y, color, this));
        }

        if (stage.getActors().size == 0 && remainingSquares == 0) {
            if (score < requiredScore) {
                game.setScreen(new GameOverScreen("You lose!", game));
            } else if (game.level < 3) {
                game.level++;
                game.setScreen(new StartScreen(game));
            } else {
                game.setScreen(new GameOverScreen("You win!", game));
            }
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
