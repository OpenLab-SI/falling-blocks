package si.openlab.falling_blocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FallingBlocks extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;

    Square firstSquare;
    Square secondSquare;
    Square thirdSquare;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("square.png");

        // vsak kvadrat ima podatke, kje je, katere barve je in ce je kliknjen
        firstSquare = new Square(0, 0, img, Color.RED);
        secondSquare = new Square(200, 150, img, Color.GREEN);
        thirdSquare = new Square(400, 300, img, Color.BLUE);
    }

    @Override
    public void render() {
        // pobrisi zaslon
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // preveri ce je kliknjen kateri izmed kvadratov
        if (Gdx.input.isButtonPressed(0)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            firstSquare.checkClicked(x, y);
            secondSquare.checkClicked(x, y);
            thirdSquare.checkClicked(x, y);
        }

        // izrisi igro
        batch.begin();

        firstSquare.draw(batch);
        secondSquare.draw(batch);
        thirdSquare.draw(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
