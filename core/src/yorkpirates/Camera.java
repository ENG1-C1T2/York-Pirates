package yorkpirates;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {
    public Camera() {
        super(1920, 1080);
    }

    public void trackShip(Ship target) {
        position.x = target.transform.x + target.transform.width/2;
        position.y = target.transform.y + target.transform.height/2;

        update();
    }
}
