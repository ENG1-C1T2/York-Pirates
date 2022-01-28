package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PlayerController implements ShipController {
    @Override
    public Vector2 calculateVelocity() {
        Vector2 velocity = new Vector2(0, 0);

        if (leftPressed()) {
            velocity.x -= 1;
        }
        if (rightPressed()) {
            velocity.x += 1;
        }

        if (upPressed()) {
            velocity.y += 1;
        }
        if (downPressed()) {
            velocity.y -= 1;
        }

        return velocity.nor();
    }

    @Override
    public boolean shouldFire() {
        return Gdx.input.isKeyPressed(Input.Keys.F);
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
