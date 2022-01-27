package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PlayerController implements ShipController {
    Vector2 velocity;
    Vector2 firingVelocity;
    int health;
    Vector2 location;

    //speed value arbitrary for now - subject to change with testing
    final int speed = 300;

    public PlayerController() {
        velocity = new Vector2();
        firingVelocity = new Vector2();
        health = 20;
        location = new Vector2();
    }


    @Override
    public void steering() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -speed;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = speed;
        }
        else {
            velocity.x = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocity.y = speed;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocity.y = -speed;
        }
        else {
            velocity.y = 0;
        }


        //prevent the ship from leaving the screen
        if (location.x < 0) {
            location.x = 0;
        }
        if (location.x > 1920 - 100) {
            location.x = 1920 - 100;
        }
        if (location.y < 0) {
            location.y = 0;
        }
        if (location.y > 1080 - 100) {
            location.y = 1080 - 100;
        }

        location.x += velocity.x * Gdx.graphics.getDeltaTime();
        location.y += velocity.y * Gdx.graphics.getDeltaTime();

        //ensure there is always a velocity for cannonballs to use if the player fires while the ship is stationary
        if ((velocity.x != 0) || (velocity.y != 0)) {
            firingVelocity.x = velocity.x;
            firingVelocity.y = velocity.y;
        }
    }

    @Override
    public boolean shouldFire() {
        return false;
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public Vector2 getLocation() { return location; }

    @Override
    public void takeDamage() {
        health -= 1;
    }



}