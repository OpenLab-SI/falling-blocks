package si.openlab.falling_blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
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
        bodyDef.fixedRotation = false;
    }

    TextureRegion img;
    Body body;
    FallingBlocks game;

    Square(float x, float y, Color color, FallingBlocks game) {
        this.game = game;

        setSize(1, 1);
        setPosition(x, y);
        setColor(color);

        Texture texture = game.assets.get("square.png");
        img = new TextureRegion(texture);

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
    protected void setParent(Group parent) {
        super.setParent(parent);

        if (parent == null) {
            // odstrani telo iz sveta
            game.world.destroyBody(body);
            // pobrise telo
            body = null;
        } else {
            // postavi kvadrat na pravi polozaj
            bodyDef.position.set(getX() + 0.5f, getY() + 0.5f);
            // ustvari telo
            body = game.world.createBody(bodyDef);
            // doda telesu kvadrat (sele tu to telo postane kvadrat)
            body.createFixture(fixtureDef);
        }
    }

    @Override
    public void act(float delta) {
        Vector2 position = body.getPosition();
        setPosition(position.x - 0.5f, position.y - 0.5f);
        setRotation(body.getAngle() * 180 / MathUtils.PI);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(img, getX(), getY(), 0.5f, 0.5f, getWidth(), getHeight(), 1, 1, getRotation());
    }
}
