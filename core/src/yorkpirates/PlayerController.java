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
        if (leftPressed()) {
            velocity.x = -speed;
        }
        else if (rightPressed()) {
            velocity.x = speed;
        }
        else {
            velocity.x = 0;
        }

        if (upPressed()) {
            velocity.y = speed;
        }
        else if (downPressed()) {
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

    private boolean upPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W);
    }

    private boolean leftPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
    }

    private boolean downPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S);
    }

    private boolean rightPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
    }
}
