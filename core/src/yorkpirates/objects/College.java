package yorkpirates.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import yorkpirates.GameScreen;

public class College implements GameObject, HasTransform {
    int width;
    int height;
    String name;
    int x;
    int y;
    Rectangle transform;

    Texture collegeTex;

    public College (String name, double x, double y) {
        this.name = name;
        this.x = (int)x;
        this.y = (int)y;

        this.width = 140;
        this.height = 140;

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
