package yorkpirates.objects;

import com.badlogic.gdx.math.Vector2;

/**
 *Controller for ship objects in the game.
 */
public interface ShipController{
    Vector2 calculateVelocity();
    boolean shouldFire();
}
