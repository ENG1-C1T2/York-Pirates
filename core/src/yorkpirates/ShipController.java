package yorkpirates;

import com.badlogic.gdx.math.Vector2;

/**
 *Controller for ship objects in the game.
 */

public interface ShipController{
    void steering();
    boolean shouldFire();
    Vector2 getVelocity();
}