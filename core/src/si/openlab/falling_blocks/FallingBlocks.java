package si.openlab.falling_blocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FallingBlocks extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("square.png");
    }

    @Override
    public void render() {
        // pobrisi zaslon
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // izrisi igro
        batch.begin();

        batch.setColor(1, 0, 0, 1);
        batch.draw(img, 0, 0, 100, 100);

        batch.setColor(0, 1, 0, 1);
        batch.draw(img, 200, 150, 100, 100);

        batch.setColor(0, 0, 1, 1);
        batch.draw(img, 400, 300, 100, 100);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
