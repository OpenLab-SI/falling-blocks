package si.openlab.falling_blocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class FallingBlocks extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;

    // seznam vseh kvadratov na zaslonu
    // tega je nujno treba narediti (tako kot kvadrate)
    List<Square> squares = new ArrayList<Square>();

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("square.png");

        // vsak kvadrat ima podatke, kje je, katere barve je in ce je kliknjen
        squares.add(new Square(0, 0, img, Color.RED));
        squares.add(new Square(200, 150, img, Color.GREEN));
        squares.add(new Square(400, 300, img, Color.BLUE));
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

            // v zanki stejemo od 0 do stevila kvadratov (od 0 do 3)
            // in preverimo ce je katerikoli kliknjen
            for (int i = 0; i < squares.size(); i++) {
                squares.get(i).checkClicked(x, y);
            }
        }

        // izrisi igro
        batch.begin();

        // izrisemo vse kvadrate s trenutnim batch-om
        for (int i = 0; i < squares.size(); i++) {
            squares.get(i).draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
