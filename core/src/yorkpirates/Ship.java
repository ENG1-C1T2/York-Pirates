package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public abstract class Ship implements GameObject {
    ShipController controller;
    Vector2 velocity;
    int health;
    Texture shipTex;
    Rectangle ship;
    float rotation;
    TextureRegion shipImage;
    Vector2 firingVelocity;
    long lastFired;

    //speed value arbitrary for now - subject to change with testing
    final int speed = 300;

    protected abstract ShipController createController();

    @Override
    public void create(YorkPirates game) {
        shipTex = new Texture(Gdx.files.internal("ship.png"));
        shipImage = new TextureRegion(shipTex);
        controller = createController();

        health = 20;
        velocity = new Vector2();
        firingVelocity = new Vector2(0, speed);

        ship = new Rectangle();
        ship.width = 100;
        ship.height = 200;
        ship.x = 0;
        ship.y = 0;

        lastFired = 1000000000;
    }

    @Override
    public void update(final YorkPirates game) {
        controller.steering();
        velocity = controller.getVelocity().scl(speed);

        //ensure there is always a velocity for cannonballs to use if the player fires while the ship is stationary
        if ((velocity.x != 0) || (velocity.y != 0)) {
            firingVelocity.x = velocity.x;
            firingVelocity.y = velocity.y;
        }

        ship.x += velocity.x * Gdx.graphics.getDeltaTime();
        ship.y += velocity.y * Gdx.graphics.getDeltaTime();

        if (!((velocity.x == 0) & (velocity.y == 0))) {
            rotation = (float) Math.toDegrees((Math.atan2(-velocity.x, velocity.y)));
        }

        if (controller.shouldFire()) {
            tryToFire(game);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
            shipImage, ship.x, ship.y,
            ship.width/2, ship.height/2, ship.width, ship.height,
            1, 1, rotation
        );
    }

    @Override
    public void dispose() {
        shipTex.dispose();
    }

    public void tryToFire(final YorkPirates game) {
        //allow player to fire at most once per second
        if (TimeUtils.nanoTime() - lastFired > 1000000000) {
            Cannonball cannonball;
            cannonball = new Cannonball(firingVelocity, ship.x + ship.width/2, ship.y + ship.height/2);
            game.addObject(cannonball);
            lastFired = TimeUtils.nanoTime();
        }
    }
}
