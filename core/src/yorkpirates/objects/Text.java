package yorkpirates.objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import yorkpirates.GameScreen;

/**
 * A static line of text in the game world.
 */
public class Text implements GameObject {
    private final String message;
    private final int x;
    private final int y;

    private final BitmapFont font = new BitmapFont();

    /**
     * Creates a new Text object.
     * @param message The message to display.
     * @param x The x position.
     * @param y The y position.
     * @param scale The font scale to use.
     */
    public Text (String message, int x, int y, int scale) {
        this.message = message;
        this.x = x;
        this.y = y;

        font.getData().setScale(scale);
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
