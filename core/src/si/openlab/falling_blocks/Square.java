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
    static float size = 1;
    static float halfSize = size / 2;

    static Color[] colors = {
            Color.valueOf("#ffb133"),
            Color.valueOf("#33ff6d"),
            Color.valueOf("#33c2ff"),
            Color.valueOf("#ff3333"),
            Color.valueOf("#c233ff")
    };

    // staticna polja za definicije oblike in telesa
    // (staticna, ker so enaka za vse kvadrate)
    static BodyDef bodyDef;
    static FixtureDef fixtureDef;

    static {
        // doloci obliko nasega kvadrata
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfSize, halfSize);

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

        setSize(size, size);
        setPosition(x, y);
        setColor(color);

        Texture texture = game.assets.get("square.png");
        // TextureRegion je del teksture, v temu primeru, cela tekstura
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
            // polozaji v Box2D (fiziki) so v srediscu predmetov, v libGDX (graficni del) pa v levem spodnjem kotu
            bodyDef.position.set(getX() + halfSize, getY() + halfSize);
            // ustvari telo
            body = game.world.createBody(bodyDef);
            // doda telesu kvadrat (sele tu to telo postane kvadrat)
            body.createFixture(fixtureDef);
        }
    }

    @Override
    public void act(float delta) {
        Vector2 position = body.getPosition();
        // polozaji v Box2D (fiziki) so v srediscu predmetov, v libGDX (graficni del) pa v levem spodnjem kotu
        setPosition(position.x - halfSize, position.y - halfSize);
        // koti v Box2D (fiziki) so v radianih, libGDX (graficni del) pa uporablja stopinje
        setRotation(body.getAngle() * 180 / MathUtils.PI);

        // potisnemo kocko navzdol
        body.setLinearVelocity(0, -3);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        // za nastavljanje rotacije, je potrebno podati se nekaj drugih stvari
        // tukaj podamo: polozaj (x in y), center vrtenja (x in y), velikost (sirina in visina), razteg (po x in y), rotacijo
        batch.draw(img, getX(), getY(), halfSize, halfSize, getWidth(), getHeight(), 1, 1, getRotation());
    }
}
