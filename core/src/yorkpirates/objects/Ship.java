package yorkpirates.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import yorkpirates.GameScreen;

/**
 * Ship object for AI-controlled ships and player-controlled ships.
 */
public abstract class Ship implements GameObject, HasTransform {
    private int health;

    private Rectangle transform;
    private float rotation;
    private Vector2 velocity;
    private Vector2 firingVelocity;
    private ShipController controller;
    private long lastFired;
    private final float renderScale = 1f;

    private final Texture[] textures = new Texture[8];
    private int textureIndex;
    private Vector2[] firingPoints;

    protected int speed = 400;

    protected abstract ShipController createController();

    /**
     * Initialise fields for a Ship object.
     * @param game Main GameScreen
     */
    @Override
    public void create(GameScreen game) {
        loadTextures();
        controller = createController();

        health = 20;
        velocity = new Vector2();
        firingVelocity = new Vector2(1, 0);

        transform = new Rectangle();
        transform.width = 100;
        transform.height = 200;
        transform.x = 0;
        transform.y = 0;

        lastFired = 1000000000;
    }

    /**
     * Load the textures required to render the Ship object.
     */
    private void loadTextures() {
        final String prefix = "ship/", postfix = ".png";
        final String[] texturePaths = {
                "E", "NE", "N", "NW", "W", "SW", "S", "SE"
        };

        for (int i = 0; i < texturePaths.length; i++) {
            final String fullPath = prefix + texturePaths[i] + postfix;
            textures[i] = new Texture(Gdx.files.internal(fullPath));
        }

        // The coordinates of the frontal cannon in each texture.
        firingPoints = new Vector2[] {
                new Vector2(211, -167),
                new Vector2(195, -90),
                new Vector2(117, -67),
                new Vector2(38, -90),
                new Vector2(22, -167),
                new Vector2(38, -227),
                new Vector2(117, -248),
                new Vector2(196, -227)
        };

        for (int i = 0; i < firingPoints.length; i++) {
            Vector2 origin = new Vector2(textures[i].getWidth()/2f, -textures[i].getHeight()/2f);
            firingPoints[i].sub(origin).scl(renderScale);
        }

        textureIndex = 0;
    }

    /**
     * Update velocity, prevent ship leaving the edge of the game world, update velocity at which Cannonballs
     * should be fired,update the ship's rotation and control whether the ship should fire.
     * @param game Main GameScreen
     * @param delta float value to update velocity.
     */
    @Override
    public void update(final GameScreen game, final float delta) {
        velocity = new Vector2(controller.calculateVelocity()).scl(speed * delta);

        // Ensure there is always a velocity for cannonballs to use,
        // even if the player fires while the ship is stationary.
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
            rotation = velocity.angleDeg();
        }

        if (controller.shouldFire()) {
            tryToFire(game);
        }
    }

    /**
     * Render the Ship object in the corresponding GameScreen.
     * @param game Main GameScreen
     */
    @Override
    public void render(GameScreen game) {
        GameScreen.Batches batches = game.batches;

        updateTextureIndex();

        final Texture texture = textures[textureIndex];
        final int texWidth = texture.getWidth();
        final int texHeight = texture.getHeight();

        batches.world.begin();
        batches.world.draw(
                texture, transform.x - texWidth/2f, transform.y - texHeight/2f,
                texWidth/2f, texHeight/2f, texWidth, texHeight,
                renderScale, renderScale, 0, 0, 0, texWidth, texHeight,
                false, false
        );
        batches.world.end();
    }

    /**
     * Update the texture to match the Ship's rotation.
     */
    private void updateTextureIndex() {
        textureIndex = Math.round(rotation * textures.length / 360);
    }

    @Override
    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }

    public int getHealth() {
        return health;
    }

    public void modHealth(int amount) {
        health += amount;
        // TODO: implement death
    }

    @Override
    public Rectangle getTransform() {
        return new Rectangle(transform);
    }

    public float getRotation() {
        return rotation;
    }

    public Vector2 getVelocity() {
        return new Vector2(velocity);
    }

    /**
     * Fire a Cannonball if at least one second has elapsed since the last time a Cannonball was fired.
     * @param game Main GameScreen
     */
    private void tryToFire(final GameScreen game) {
        //allow player to fire at most once per second
        if (TimeUtils.nanoTime() - lastFired > 1000000000) {
            Vector2 spawnPoint = transform.getPosition(new Vector2());
            spawnPoint.add(firingPoints[textureIndex]);

            Cannonball cannonball = new Cannonball(firingVelocity, spawnPoint.x, spawnPoint.y);
            game.addObject(cannonball);
            lastFired = TimeUtils.nanoTime();
        }
    }

    @Override
    public int getDepth() {
        return 0;
    }
}
