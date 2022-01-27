package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.lang.Math;

public class AIController implements ShipController {
    Vector2 velocity;
    long lastVelChange = 200000000;
    int health;
    Vector2 location;
    final int speed = 300;

    public AIController() {
        velocity = new Vector2();
        health = 20;
        location = new Vector2();
    }


    @Override
    public void steering() {
        //ensure AI ship changes its velocity at most once every two seconds
        if (TimeUtils.nanoTime() - lastVelChange > 2000000000) {
            //select random direction for AI ship to travel
            double x = Math.random();
            if (x >= 0.5) {
                velocity.x = speed;
            } else {
                velocity.x = -speed;
            }

            double y = Math.random();
            if (y >= 0.5) {
                velocity.y = speed;
            } else {
                velocity.y = -speed;
            }
            lastVelChange = TimeUtils.nanoTime();
        }

        //prevent the ship from leaving the screen
        if (location.x < 0) {
            velocity.x = 300;
        }
        if (location.x > 1920 - 100) {
            velocity.x = -300;
        }
        if (location.y < 0) {
            velocity.y = 300;
        }
        if (location.y > 1080 - 100) {
            velocity.y = -300;
        }

        location.x += velocity.x * Gdx.graphics.getDeltaTime();
        location.y += velocity.y * Gdx.graphics.getDeltaTime();



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