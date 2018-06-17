package si.openlab.falling_blocks;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Square extends Actor {
    Texture img;

    Square(float x, float y, Color color, AssetManager assets) {
        setSize(1, 1);
        setPosition(x, y);
        setColor(color);

        img = assets.get("square.png");

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                event.getTarget().remove();
                // alternativno: Square.this.remove();

                // povemo, da smo uspesno obdelali dogodek (dotik)
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(img, getX(), getY(), getWidth(), getHeight());
    }
}
