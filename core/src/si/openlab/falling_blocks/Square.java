package si.openlab.falling_blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Square extends Actor {
    // staticna polja za definicije oblike in telesa
    // (staticna, ker so enaka za vse kvadrate)
    static BodyDef bodyDef;
    static FixtureDef fixtureDef;

    static {
        // doloci obliko nasega kvadrata
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);

        // doloci fizikalne lastnosti kvadrata
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 500;

        // doloci lastnoti telesa (kvadata) (znotraj fizikalnega pogona)
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
    }

    Texture img;
    Body body;
    FallingBlocks game;

    Square(float x, float y, Color color, FallingBlocks game) {
        this.game = game;

        setSize(1, 1);
        setPosition(x, y);
        setColor(color);

        img = game.assets.get("square.png");

        // postavi kvadrat na pravi polozaj
        bodyDef.position.set(getX() + 0.5f, getY() + 0.5f);
        // ustvari telo
        body = game.world.createBody(bodyDef);
        // doda telesu kvadrat (sele tu to telo postane kvadrat)
        body.createFixture(fixtureDef);

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
