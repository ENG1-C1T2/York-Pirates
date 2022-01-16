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
    Vector2 firingVelocity;

    public Ship(ShipController controller) {
        this.controller = controller;
        health = 20;
        velocity = new Vector2();
        firingVelocity = new Vector2();


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

    @Override
    public void render() {
        velocity = controller.getVelocity();


        //ensure there is always a velocity for cannonballs to use if the player fires while the ship is stationary
        if ((velocity.x != 0) || (velocity.y != 0)) {
            firingVelocity.x = velocity.x;
            firingVelocity.y = velocity.y;
        }

        ship.x += velocity.x * Gdx.graphics.getDeltaTime();
        ship.y += velocity.y * Gdx.graphics.getDeltaTime();

        if (!((velocity.x == 0) & (velocity.y == 0))) {
            rotation = (float) Math.toDegrees((Math.atan2((double) -velocity.x, (double) velocity.y)));
        }
    }


    @Override
    public void dispose() {
        shipTex.dispose();

    }
}

