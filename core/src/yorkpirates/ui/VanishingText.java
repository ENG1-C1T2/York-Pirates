package yorkpirates.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import yorkpirates.GameScreen;
import yorkpirates.objects.GameObject;
import yorkpirates.objects.HasTransform;

/**
 * A line of text that floats up and fades out.
 */
public class VanishingText implements GameObject, HasTransform {
    private static final int FONT_SCALE = 2;
    private static final float SPEED = 30;
    private static final float FADE_MUL = 0.7f;

    private final Rectangle transform;
    private final BitmapFont font;
    private final StringSupplier supplier;
    private final Color color;

    private Vector2 offset;

    /**
     * Creates a new VanishingText object.
     *
     * @param x The x position for the text to start at.
     * @param y The y position for the text to start at.
     * @param supplier A StringSupplier that returns the text to be printed.
     * @param r The red value of the text color.
     * @param g The green value of the text color.
     * @param b The blue value of the text color.
     */
    public VanishingText(float x, float y, StringSupplier supplier, float r, float g, float b) {
        transform = new Rectangle(x, y, 0, 0);
        offset = null;
        font = new BitmapFont();
        font.getData().setScale(FONT_SCALE);
        font.setColor(256, 256, 256, 1);

        this.supplier = supplier;

        color = new Color(r, g, b, 1);
    }

    @Override
    public void create(GameScreen game) {

    }

    @Override
    public void update(GameScreen game, float delta) {
        // Move up and update the colour.
        transform.y += SPEED * delta;
        color.a -= FADE_MUL * delta;

        if (color.a <= 0) {
            game.removeObject(this);
            return;
        }

        // For some reason, drawing using the screen batch results in weird white boxes around the text.
        // So as a bit of a hack, just set this printout to manually follow the camera around.

        offset = new Vector2(game.camera.position.x, game.camera.position.y);
    }

    @Override
    public void render(GameScreen game) {
        GameScreen.Batches batches = game.batches;

        // Prevents flickering where the text is rendered before matching its
        // position to the camera.
        if (offset == null) {
            return;
        }

        batches.world.begin();
        font.setColor(color);
        font.draw(batches.world, supplier.get(),
                offset.x + transform.x, offset.y + transform.y);
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
