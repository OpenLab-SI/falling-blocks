package si.openlab.falling_blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Square {

    int x;
    int y;

    Texture img;
    Color color;

    boolean clicked;

    Square(int x, int y, Texture img, Color color) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.color = color;
        this.clicked = false;
    }

    // kvadrat se izrise sam, povemo mu le, s katerim batchom
    void draw(SpriteBatch batch) {
        if (!clicked) {
            batch.setColor(color);
            batch.draw(img, x, y, 100, 100);
        }
    }

    // preveri in si zapomni, ce je kvadrat kliknjen
    // this.x in this.y sta od kvadrata
    // x in y sta od miske
    void checkClicked(int x, int y) {
        if (x >= this.x && x <= this.x + 100 && y >= this.y && y <= this.y + 100) {
            this.clicked = true;
        }
    }

}
