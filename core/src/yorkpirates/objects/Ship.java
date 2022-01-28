package yorkpirates.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import yorkpirates.GameScreen;

public abstract class Ship implements GameObject {
    private int health;

    private Rectangle transform;
    private float rotation;
    private Vector2 velocity;
    private Vector2 firingVelocity;
    private ShipController controller;
    private long lastFired;

    private Texture shipTex;
    private TextureRegion shipImage;

    //speed value arbitrary for now - subject to change with testing
    private final int speed = 300;

    protected abstract ShipController createController();

    @Override
    public void create(GameScreen game) {
        shipTex = new Texture(Gdx.files.internal("ship.png"));
        shipImage = new TextureRegion(shipTex);
        controller = createController();

        health = 20;
        velocity = new Vector2();
        firingVelocity = new Vector2(0, speed);

        transform = new Rectangle();
        transform.width = 100;
        transform.height = 200;
        transform.x = 0;
        transform.y = 0;

        lastFired = 1000000000;
    }

    @Override
    public void update(final GameScreen game) {
        velocity = controller.calculateVelocity().scl(speed);

        //ensure there is always a velocity for cannonballs to use if the player fires while the ship is stationary
        if ((velocity.x != 0) || (velocity.y != 0)) {
            firingVelocity.x = velocity.x;
            firingVelocity.y = velocity.y;
        }

        transform.x += velocity.x * Gdx.graphics.getDeltaTime();
        transform.y += velocity.y * Gdx.graphics.getDeltaTime();

        if (!((velocity.x == 0) & (velocity.y == 0))) {
            rotation = (float) Math.toDegrees((Math.atan2(-velocity.x, velocity.y)));
        }

        if (controller.shouldFire()) {
            tryToFire(game);
        }
    }

    @Override
    public void render(GameScreen.Batches batches) {
        batches.world.draw(
            shipImage, transform.x, transform.y,
            transform.width/2, transform.height/2, transform.width, transform.height,
            1, 1, rotation
        );
    }

    @Override
    public void dispose() {
        shipTex.dispose();
    }

    public int getHealth() {
        return health;
    }

    public void modHealth(int amount) {
        health += amount;
        // TODO: implement death
    }

    public Rectangle getTransform() {
        return new Rectangle(transform);
    }

    public float getRotation() {
        return rotation;
    }

    public Vector2 getVelocity() {
        return new Vector2(velocity);
    }

    private void tryToFire(final GameScreen game) {
        //allow player to fire at most once per second
        if (TimeUtils.nanoTime() - lastFired > 1000000000) {
            Cannonball cannonball;
            cannonball = new Cannonball(firingVelocity, transform.x + transform.width/2, transform.y + transform.height/2);
            game.addObject(cannonball);
            lastFired = TimeUtils.nanoTime();
        }
    }
}
