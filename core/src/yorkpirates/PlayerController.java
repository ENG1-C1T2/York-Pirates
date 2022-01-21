package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PlayerController implements ShipController {
    Vector2 velocity;

    //speed value arbitrary for now - subject to change with testing
    final int speed = 300;

    public PlayerController() {
        velocity = new Vector2(0,0);
    }

    @Override
    public void steering() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -speed;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = speed;
        }
        else {
            velocity.x = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocity.y = speed;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocity.y = -speed;
        }
        else {
            velocity.y = 0;
        }
    }

    @Override
    public boolean shouldFire() {
        return Gdx.input.isKeyPressed(Input.Keys.F);
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }
}