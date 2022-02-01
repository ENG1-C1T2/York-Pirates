package yorkpirates.objects;

import com.badlogic.gdx.math.Vector2;

/**
 *Controller for ship objects in the game.
 */
public interface ShipController {
    /**
     * Calculates the velocity that the corresponding ship should use depending on which keys the player presses.
     * @return Vector representing the x and y components of the ship's velocity.
     */
    Vector2 calculateVelocity();

    /**
     * Returns true if the player presses the key to fire.
     */
    boolean shouldFire();
}
