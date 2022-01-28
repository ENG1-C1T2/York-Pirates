package yorkpirates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import yorkpirates.objects.Ship;

public class Camera extends OrthographicCamera {
    public Camera() {
        super(1920, 1080);
    }

    public void trackShip(Ship target) {
        final Rectangle transform = target.getTransform();

        position.x = transform.x + transform.width/2;
        position.y = transform.y + transform.height/2;

        update();
    }
}
