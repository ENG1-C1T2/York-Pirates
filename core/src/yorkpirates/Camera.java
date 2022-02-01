package yorkpirates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import yorkpirates.objects.Ship;

/**
 * The camera used by our game, that tracks the player's ship.
 */
public class Camera extends OrthographicCamera {
    public Camera() {
        super(1920, 1080);
    }

    /**
     * Change the camera's position according to the given ship's location, so this ship is always
     * at the centre of the screen.
     * @param target Ship to be tracked.
     */
    public void trackShip(Ship target) {
        final Rectangle transform = target.getTransform();

        position.x = transform.x;
        position.y = transform.y;

        update();
    }
}
