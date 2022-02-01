package yorkpirates.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import java.lang.Math;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import yorkpirates.GameScreen;

public class College implements GameObject, HasTransform {
    private int width;
    private int height;
    private int x;
    private int y;
    private long lastFired;
    String name;
    int state; // 0 = Ally, 1 = Destroyed, 2 = Enemy
    Rectangle transform;

    Texture collegeTex;

    public College (String name, double x, double y, int state) {
        this.name = name;
        this.x = (int)x;
        this.y = (int)y;
        this.state = state;

        width = 140;
        height = 140;

        lastFired = 1000000000;

    }

    @Override
    public void create(GameScreen game) {
        collegeTex = new Texture (Gdx.files.internal("college.png"));
        transform = new Rectangle(x, y, width, height);

        Text label = new Text(name, x-width/2, y+height/2+40, 2);
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

        batches.world.begin();
        batches.world.draw(collegeTex,
                transform.x - transform.width/2, transform.y - transform.height/2,
                transform.width, transform.height);
        batches.world.end();
    }

    private void tryToFire(final GameScreen game) {
        if (TimeUtils.nanoTime() - lastFired > 1200000000) {
            Vector2 spawnPoint = transform.getPosition(new Vector2());
            Vector2 direction = game.player.getTransform().getPosition(new Vector2()).sub(spawnPoint).nor();

            int distanceFromCentre = 30;
            Cannonball cannonball = new Cannonball(direction,
                    spawnPoint.x+distanceFromCentre*direction.x,
                    spawnPoint.y+distanceFromCentre*direction.y);
            game.addObject(cannonball);
            lastFired = TimeUtils.nanoTime();
        }
    }

    @Override
    public void dispose() {
        collegeTex.dispose();
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
