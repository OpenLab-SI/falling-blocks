package si.openlab.falling_blocks;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Square extends Actor {
    Texture img;

    Square(float x, float y, Color color, AssetManager assets) {
        setSize(100, 100);
        setPosition(x, y);
        setColor(color);

        img = assets.get("square.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(img, getX(), getY(), getWidth(), getHeight());
    }
}
