package yorkpirates.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import yorkpirates.GameScreen;

public class Cannonball implements GameObject, HasTransform {
    Vector2 velocity;
    Texture cannonballTex;

    private final Rectangle transform;

    public Cannonball (Vector2 velocity, float x, float y) {
        this.velocity = new Vector2(velocity).nor();
        this.cannonballTex = new Texture (Gdx.files.internal("cannonball.png"));
        this.transform = new Rectangle(x, y, 20, 20);
    }

    @Override
    public void create(GameScreen game) {

    }

    @Override
    public void update(final GameScreen game, final float delta) {
        int speed = 800;
        transform.x += velocity.x * speed * delta;
        transform.y += velocity.y * speed * delta;

        //remove and dispose cannonball objects once they have visibly left the screen
        if (!game.camera.frustum.boundsInFrustum(
                transform.x, transform.y, 0,
                transform.width/2, transform.height/2, 0)
        ) {
            game.removeObject(this);
        }
    }

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
