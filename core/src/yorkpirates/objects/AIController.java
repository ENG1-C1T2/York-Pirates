package yorkpirates.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class AIController implements ShipController {
    private final Vector2 velocity;
    long lastVelChange = 200000000;
    int health;
    Vector2 location;

    public AIController() {
        health = 20;
        location = new Vector2();
        velocity = new Vector2(0, 1);
        System.out.println(velocity);
    }

    @Override
    public Vector2 calculateVelocity() {
//        ensure AI ship changes its velocity at most once every two seconds
        if (TimeUtils.nanoTime() - lastVelChange > 2000000000) {
            //select random direction for AI ship to travel
            double x = Math.random();
            if (x >= 0.5) {
                velocity.x = 1;
            } else {
                velocity.x = -1;
            }

            double y = Math.random();
            if (y >= 0.5) {
                velocity.y = 1;
            } else {
                velocity.y = -1;
            }
            lastVelChange = TimeUtils.nanoTime();
        }

        //prevent the ship from leaving the screen
        if (location.x < 0) {
            velocity.x = 1;
        }
        if (location.x > 1920 - 100) {
            velocity.x = -1;
        }
        if (location.y < 0) {
            velocity.y = 1;
        }
        if (location.y > 1080 - 100) {
            velocity.y = -1;
        }

        return velocity;
    }

    @Override
    public boolean shouldFire() {
        return false;
    }
}
