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
    Texture shipTex;
    Rectangle ship;
    float rotation;
    TextureRegion shipImage;

    public Ship(ShipController controller) {
        this.controller = controller;
        health = 20;
        velocity = new Vector2();


    }




    @Override
    public void create(YorkPirates game) {
        shipTex = new Texture(Gdx.files.internal("tempShipImage.png"));
        shipImage = new TextureRegion(shipTex);

        ship = new Rectangle();
        ship.width = 100;
        ship.height = 100;
        ship.x = 0;
        ship.y = 0;

    }

    public void takeDamage() {
        health -= 1;
    }

    @Override
    public void render() {
        velocity = controller.getVelocity();



        if (ship.x < 0) {
            velocity.x = 300;
        }
        if (ship.x > 1920 - 100) {
            velocity.x = -300;
        }
        if (ship.y < 0) {
            velocity.y = 300;
        }
        if (ship.y > 1080 - 100) {
            velocity.y = -300;
        }

        ship.x += velocity.x * Gdx.graphics.getDeltaTime();
        ship.y += velocity.y * Gdx.graphics.getDeltaTime();


        if (!((velocity.x == 0) & (velocity.y == 0))) {
            rotation = (float) Math.toDegrees((Math.atan2(-velocity.x, velocity.y)));
        }
    }


    @Override
    public void dispose() {
        shipTex.dispose();

    }
}

