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
    protected int speed = 400;

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
    public void update(final GameScreen game, final float delta) {
        velocity = new Vector2(controller.calculateVelocity()).scl(speed * delta);

        //ensure there is always a velocity for cannonballs to use if the player fires while the ship is stationary
        if ((velocity.x != 0) || (velocity.y != 0)) {
            firingVelocity.x = velocity.x;
            firingVelocity.y = velocity.y;
        }

        Vector2 targetPosition = transform.getPosition(new Vector2())
                .add(velocity);

        // Prevent the ship from leaving the world bounds,
        // defined as a radius from the centre.
        final int waterRadius = game.waterSim.getWaterRadius();
        if (targetPosition.len() > waterRadius) {
            targetPosition.nor().scl(waterRadius);
        }

        transform.setPosition(targetPosition);

        if (!((velocity.x == 0) & (velocity.y == 0))) {
            rotation = (float) Math.toDegrees((Math.atan2(-velocity.x, velocity.y)));
        }

        if (controller.shouldFire()) {
            tryToFire(game);
        }
    }

    @Override
    public void render(GameScreen game) {
        GameScreen.Batches batches = game.batches;

        batches.world.begin();
        batches.world.draw(
            shipImage, transform.x - transform.width/2, transform.y - transform.height/2,
            transform.width/2, transform.height/2, transform.width, transform.height,
            1, 1, rotation
        );
        batches.world.end();
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
            cannonball = new Cannonball(firingVelocity, transform.x, transform.y);
            game.addObject(cannonball);
            lastFired = TimeUtils.nanoTime();
        }
    }

    @Override
    public int getDepth() {
        return 0;
    }
}
