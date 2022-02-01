package yorkpirates.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import yorkpirates.GameScreen;

/**
 * Makes everything behind it seem to have a fabric-y texture.
 */
public class FabricFilter implements GameObject {
    private final Texture fabric;

    /**
     * Creates a new FabricFilter.
     */
    public FabricFilter() {
        fabric = new Texture(Gdx.files.internal("fabric.png"));
        fabric.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void create(GameScreen game) {

    }

    @Override
    public void update(GameScreen game, float delta) {

    }

    @Override
    public void render(GameScreen game) {
        GameScreen.Batches batches = game.batches;

        batches.screen.enableBlending();
        batches.screen.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
        batches.screen.begin();
        batches.screen.draw(fabric,
                0, 0,
                (int) game.camera.position.x, (int) -game.camera.position.y,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batches.screen.end();
        batches.screen.disableBlending();
    }

    @Override
    public void dispose() {
        fabric.dispose();
    }

    @Override
    public int getDepth() {
        return 400;
    }
}
