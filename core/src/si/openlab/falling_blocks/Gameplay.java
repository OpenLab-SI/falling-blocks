package si.openlab.falling_blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Gameplay extends GameScreen {
    OrthographicCamera camera;
    FitViewport viewport;

    Stage stage;
    World world;

    Box2DDebugRenderer debugRenderer;

    int levelScore;
    float timer;

    float dropDelay;
    int remainingSquares;
    int requiredScore;

    Gameplay(FallingBlocks game) {
        super(game);

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
        super.render(delta);

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
            if (levelScore < requiredScore) {
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
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        // posodobi viewport z novo velikostjo zalona
        // (popravi parametre kamere, da ta prikazuje celo igro, omejeno z velikostjo okna)
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
        debugRenderer.dispose();
    }
}
