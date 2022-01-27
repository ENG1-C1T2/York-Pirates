package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Cannonball implements GameObject {
    Vector2 velocity;
    Rectangle cannonball;
    Texture cannonballTex;
    float x;
    float y;

    public Cannonball (Vector2 velocity, float x, float y) {
        this.velocity = new Vector2(velocity.x * 2, velocity.y * 2);
        this.x = x;
        this.y = y;
        this.cannonballTex = new Texture (Gdx.files.internal("tempCannonball.png"));
    }

    @Override
    public void create(YorkPirates game) {
        cannonball = new Rectangle();
        cannonball.width = 100;
        cannonball.height = 100;
    }

    @Override
    public void update(final YorkPirates game) {
        x += velocity.x * Gdx.graphics.getDeltaTime();
        y += velocity.y * Gdx.graphics.getDeltaTime();

        //remove and dispose cannonball objects once they have visibly left the screen
        if (!game.camera.frustum.boundsInFrustum(
                x, y, 0,
                cannonball.width/2, cannonball.height/2, 0)
        ) {
            System.out.println("Removed cannonball.");
            game.removeObject(this);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(cannonballTex, x - 50, y - 50, 100, 100);
    }

    @Override
    public void dispose() {
        cannonballTex.dispose();
    }
}
