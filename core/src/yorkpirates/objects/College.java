package yorkpirates.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.lang.Math;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import yorkpirates.GameScreen;

public class College implements GameObject, HasTransform {
    private int size;
    private int x;
    private int y;
    private long lastFired;
    private ShapeRenderer shapeRenderer;
    String name;
    int state; // 0 = Ally, 1 = Destroyed, 2 = Enemy
    Rectangle transform;

    public College (String name, double x, double y, int state) {
        this.name = name;
        this.x = (int)x;
        this.y = (int)y;
        this.state = state;
        shapeRenderer = new ShapeRenderer();

        size = 140;

        lastFired = 1000000000;

    }

    @Override
    public void create(GameScreen game) {
        transform = new Rectangle(x, y, size, size);

        Text label = new Text(name, x-size/2, y+size/2+40, 2);
        game.addObject(label);
    }

    @Override
    public void update(final GameScreen game, final float delta) {
        if (state == 2){
            // try to fire if player is close
            Rectangle playerRect = game.player.getTransform();
            double shipDistance = Math.sqrt(Math.pow(x-playerRect.x,2)+Math.pow(y-playerRect.y,2));
            if (shipDistance < 900) {
                tryToFire(game);
            }
        }
    }

    @Override
    public void render(GameScreen game) {
        GameScreen.Batches batches = game.batches;

        final Color water = new Color(0.122f, 0.406f, 0.725f, 1f);
        final Color wetSand = new Color(0.789f, 0.702f, 0.461f, 1f);
        final Color drySand = new Color(0.848f, 0.755f, 0.495f, 1f);
        final Color grass = new Color(0.105f, 0.545f, 0.239f, 1f);

        Gdx.gl20.glClear(GL20.GL_STENCIL_BUFFER_BIT);

        // Wet sand circle
        shapeRenderer.setProjectionMatrix(batches.world.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(wetSand);
        shapeRenderer.circle(x,y,size/2f);
        shapeRenderer.end();

        // Dry sand circle
        shapeRenderer.setProjectionMatrix(batches.world.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(drySand);
        shapeRenderer.circle(x,y,size*11/28f);
        shapeRenderer.end();

        // Grass sand circle
        shapeRenderer.setProjectionMatrix(batches.world.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(grass);
        shapeRenderer.circle(x,y,size*9/28f);
        shapeRenderer.end();
    }

    private void tryToFire(final GameScreen game) {
        if (TimeUtils.nanoTime() - lastFired > 1200000000) {
            Vector2 spawnPoint = transform.getPosition(new Vector2());
            Vector2 direction = game.player.getTransform().getPosition(new Vector2()).sub(spawnPoint).nor();

            int distanceFromCentre = size/2;
            Cannonball cannonball = new Cannonball(direction,
                    spawnPoint.x+distanceFromCentre*direction.x,
                    spawnPoint.y+distanceFromCentre*direction.y);
            game.addObject(cannonball);
            lastFired = TimeUtils.nanoTime();
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    @Override
    public int getDepth() {
        return 200;
    }

    @Override
    public Rectangle getTransform() {
        return transform;
    }
}
