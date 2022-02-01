package yorkpirates.objects;

import com.badlogic.gdx.math.Rectangle;

/**
 * Any object incorporating a Rectangle that must be transformed.
 */
public interface HasTransform {
    Rectangle getTransform();
}
