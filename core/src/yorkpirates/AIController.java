package yorkpirates;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.lang.Math;

public class AIController implements ShipController {
    Vector2 velocity;
    long lastVelChange = 200000000;

    final int speed = 300;

    public AIController() {velocity = new Vector2();}


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
    public void takeDamage() {

    }


}