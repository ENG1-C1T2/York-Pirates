package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Cannonball implements GameObject {
    Vector2 velocity;
    Rectangle cannonball;
    float x;
    float y;


    public Cannonball (Vector2 velocity, float x, float y) {
        this.velocity = new Vector2(velocity.x * 2, velocity.y * 2);
        this.x = x;
        this.y = y;
    }


    @Override
    public void create(YorkPirates game) {
        cannonball = new Rectangle();
        cannonball.width = 100;
        cannonball.height = 100;
    }


    @Override
    public void render() {
        x += velocity.x * Gdx.graphics.getDeltaTime();
        y += velocity.y * Gdx.graphics.getDeltaTime();

    }

    @Override
    public void dispose() {

    }
}