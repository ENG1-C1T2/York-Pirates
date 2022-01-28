package yorkpirates.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import yorkpirates.GameScreen;
import yorkpirates.objects.GameObject;

public class MovementHint implements GameObject {
    private Texture tex;

    @Override
    public void create(GameScreen game) {
        tex = new Texture(Gdx.files.internal("movementHint.png"));
    }

    @Override
    public void update(GameScreen game, final float delta) {

    }

    @Override
    public void render(GameScreen game) {
        GameScreen.Batches batches = game.batches;

        batches.screen.enableBlending();
        batches.screen.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batches.screen.begin();
        batches.screen.draw(tex, (float) Gdx.graphics.getWidth() / 2 - 218, 75);
        batches.screen.end();
    }

    @Override
    public void dispose() {
        tex.dispose();
    }

    @Override
    public int getDepth() {
        return -1000;
    }
}
