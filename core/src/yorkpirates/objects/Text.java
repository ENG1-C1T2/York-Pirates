package yorkpirates.objects;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import yorkpirates.GameScreen;

public class Text implements GameObject {
    private String message;
    private int x;
    private int y;
    private int size;

    private final BitmapFont font = new BitmapFont();

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

        batches.world.begin();
        font.draw(batches.world, message, x, y);
        batches.world.end();
    }

    @Override
    public void dispose() {
        font.dispose();
    }

    @Override
    public int getDepth() {
        return -1000;
    }
}
