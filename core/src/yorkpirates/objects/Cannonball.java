package yorkpirates.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import yorkpirates.GameScreen;

/**
 * Cannonball object fired by ships during combat.
 */
public class Cannonball implements GameObject, HasTransform {
    Vector2 velocity;
    Texture cannonballTex;

    private final Rectangle transform;

    /**
     *
     * @param velocity Velocity at which the cannonball is fired
     * @param x Initial x location
     * @param y Initial y location
     */
    public Cannonball (Vector2 velocity, float x, float y) {
        this.velocity = new Vector2(velocity).nor();
        this.cannonballTex = new Texture (Gdx.files.internal("cannonball.png"));
        this.transform = new Rectangle(x, y, 20, 20);
    }

    @Override
    public void create(GameScreen game) {

    }

    /**
     * Update location and remove Cannonball object once it has left the edge of the game world.
     * @param game GameScreen where the Cannonball must be updated.
     * @param delta float value to update location.
     */
    @Override
    public void update(final GameScreen game, final float delta) {
        int speed = 800;
        transform.x += velocity.x * speed * delta;
        transform.y += velocity.y * speed * delta;

        if (!game.camera.frustum.boundsInFrustum(
                transform.x, transform.y, 0,
                transform.width/2, transform.height/2, 0)
        ) {
            game.removeObject(this);
        }
    }

    /**
     * Render the Cannonball object in the corresponding GameScreen.
     * @param game GameScreen where the Cannonball is rendered.
     */
    @Override
    public void render(GameScreen game) {
        GameScreen.Batches batches = game.batches;

        batches.world.begin();
        batches.world.draw(cannonballTex,
                transform.x - transform.width/2, transform.y - transform.height/2,
                transform.width, transform.height);
        batches.world.end();
    }

    @Override
    public void dispose() {
        cannonballTex.dispose();
    }

    @Override
    public int getDepth() {
        return 0;
    }

    @Override
    public Rectangle getTransform() {
        return transform;
    }
}
