package yorkpirates.objects;

import com.badlogic.gdx.math.Rectangle;

/**
 * Any object whose position can be described with a Rectangle.
 */
public interface HasTransform {
    /**
     * Get this object's transform.
     * @return A Rectangle representing this object's position in the world.
     */
    Rectangle getTransform();
}
