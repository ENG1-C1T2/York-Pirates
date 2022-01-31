package yorkpirates.objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import yorkpirates.GameScreen;

public class Text implements GameObject {
    String message;
    int x;
    int y;
    int size;
    BitmapFont font = new BitmapFont();

    public Text (String message, int x, int y, int size) {
        this.message = message;
        this.x = x;
        this.y = y;
        this.size = size;

        font.getData().setScale(size);

    }

    @Override
    public void create(GameScreen game) {

    }

    @Override
    public void update(GameScreen game, final float delta) {

    }

    @Override
    public void render(GameScreen game) {
        GameScreen.Batches batches = game.batches;

//        batches.screen.enableBlending();
//        batches.screen.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batches.world.begin();
        font.draw(batches.world, message, x, y);
        batches.world.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public int getDepth() {
        return -1000;
    }
}
