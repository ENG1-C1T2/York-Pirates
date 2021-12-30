package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ship implements GameObject {
    ShipController controller;
    Vector2 velocity;
    int health;
    Texture shipImageTex;
    Rectangle ship;
    float rotation;
    TextureRegion shipImage;

    public Ship(ShipController controller) {
        this.controller = controller;
        //trying 20 health for now - subject to change
        health = 20;
        velocity = new Vector2(0,0);

    }




    @Override
    public void create(YorkPirates game) {
        shipImageTex = new Texture(Gdx.files.internal("tempShipImage.png"));
        shipImage = new TextureRegion(shipImageTex);

        ship = new Rectangle();
        ship.width = 100;
        ship.height = 100;
        ship.x = 0;
        ship.y = 0;

    }

    @Override
    public void render() {
        velocity = controller.getVelocity();

        ship.x += velocity.x * Gdx.graphics.getDeltaTime();
        ship.y += velocity.y * Gdx.graphics.getDeltaTime();

        if (!((velocity.x == 0) & (velocity.y == 0))) {
            rotation = (float) Math.toDegrees((Math.atan2((double) -velocity.x, (double) velocity.y)));
        }
    }


    @Override
    public void dispose() {
        shipImageTex.dispose();

    }
}

