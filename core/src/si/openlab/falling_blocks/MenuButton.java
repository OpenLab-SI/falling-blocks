package si.openlab.falling_blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class MenuButton extends Actor {
    static float width = 300;
    static float heigth = 40;

    String text;
    BitmapFont font;
    TextureRegion background;

    MenuButton(float x, float y, String text, GameScreen screen) {
        this.text = text;

        setPosition(x, y);
        setSize(width, heigth);

        font = screen.game.assets.get("game.ttf");

        Texture texture = screen.game.assets.get("square.png");
        // TextureRegion je del teksture, v temu primeru, cela tekstura
        background = new TextureRegion(texture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, getX(), getY(), getWidth(), getHeight());

        float y = getY() + (getHeight() + font.getLineHeight()) / 2;

        font.setColor(Color.BLACK);
        font.draw(batch, text, getX(), y, getWidth(), Align.center, false);
        font.setColor(Color.WHITE);
    }
}
