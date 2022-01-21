package yorkpirates.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import yorkpirates.GameObject;
import yorkpirates.YorkPirates;

public class MovementHint implements GameObject {
    private Texture tex;

    @Override
    public void create(YorkPirates game) {
        tex = new Texture(Gdx.files.internal("movementHint.png"));
    }

    @Override
    public void update(YorkPirates game) {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(tex, (float) Gdx.graphics.getWidth() / 2 - 218, 75);
    }

    @Override
    public void dispose() {
        tex.dispose();
    }
}
