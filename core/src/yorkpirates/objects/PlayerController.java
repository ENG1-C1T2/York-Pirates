package yorkpirates.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

/**
 * Controller for player-controlled ships.
 */
public class PlayerController implements ShipController {
    /**
     * Calculates the velocity that the corresponding ship should use depending on which keys the player presses.
     * @return Vector representing the x and y components of the ship's velocity.
     */
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

    /**
     * Returns true if the player presses the key to fire.
     */
    @Override
    public boolean shouldFire() {
        return Gdx.input.isKeyPressed(Input.Keys.F);
    }

    /**
     * Returns true if the player presses the up arrow key or W.
     */
    private boolean upPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W);
    }

    /**
     * Returns true if the player presses the left arrow key or A.
     */
    private boolean leftPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
    }

    /**
     * Returns true if the player presses the down arrow key or S.
     */
    private boolean downPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S);
    }

    /**
     * Returns true if the player presses the right arrow key or D.
     */
    private boolean rightPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
    }
}
