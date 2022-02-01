package yorkpirates.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import yorkpirates.GameScreen;
import yorkpirates.objects.GameObject;
import yorkpirates.objects.HasTransform;

/**
 * A UI element that prints a changeable block of text at a fixed point
 * on the screen every frame.
 */
public class Printout implements GameObject, HasTransform {
    private static final int FONT_SCALE = 2;

    private final Rectangle transform;
    private final BitmapFont font;
    private final StringSupplier supplier;
    private final int align;

    private Vector2 offset;

    /**
     * Creates a new Printout object.
     *
     * @param x The x position for the text to start at.
     * @param y The y position for the text to start at.
     * @param width The desired width of the text block.
     * @param align The alignment to use.
     * @param supplier A StringSupplier that returns the text to be printed.
     */
    public Printout(float x, float y, float width, int align, StringSupplier supplier) {
        transform = new Rectangle(x, y, width, 0);
        offset = new Vector2();
        font = new BitmapFont();
        font.getData().setScale(FONT_SCALE);
        font.setColor(256, 256, 256, 1);

        this.align = align;
        this.supplier = supplier;
    }

    @Override
    public void create(GameScreen game) {

    }

    @Override
    public void update(GameScreen game, float delta) {
        // For some reason, drawing using the screen batch results in weird white boxes around the text.
        // So as a bit of a hack, just set this printout to manually follow the camera around.

        offset = new Vector2(game.camera.position.x, game.camera.position.y);
    }

    @Override
    public void render(GameScreen game) {
        GameScreen.Batches batches = game.batches;

        batches.world.begin();
        font.draw(batches.world, supplier.get(),
                offset.x + transform.x, offset.y + transform.y,
                transform.width, align, true);
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

    @Override
    public Rectangle getTransform() {
        return transform;
    }
}
