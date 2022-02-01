package yorkpirates.objects;

import com.badlogic.gdx.math.Rectangle;

/**
 * Any object whose position can be described with a Rectangle.
 */
public interface HasTransform {
    Rectangle getTransform();
}
