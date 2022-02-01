package yorkpirates.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Align;
import yorkpirates.GameScreen;
import yorkpirates.events.DestroyedCollege;
import yorkpirates.events.KilledEnemy;
import yorkpirates.ui.Printout;

/**
 * Enables some pressing certain keys to trigger
 * special functionality.
 * For debugging/demonstration purposes.
 */
 public class DevPowers implements GameObject {
    @Override
    public void create(GameScreen game) {
       game.addObject(new Printout(
               25 - Gdx.graphics.getWidth() / 2f,
               45 - Gdx.graphics.getHeight() / 2f,
               Gdx.graphics.getWidth(),
               Align.left,
               () -> "DEV POWERS: Spoof events: 'O' -> KilledEnemy; 'P' -> DestroyedCollege"
       ));
    }

    @Override
    public void update(GameScreen game, float delta) {
       if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
          // Kill an enemy
          game.events.trigger(new KilledEnemy());
       }
       if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
          // Destroy a college
          game.events.trigger(new DestroyedCollege());
       }
    }

    @Override
    public void render(GameScreen game) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getDepth() {
        return 0;
    }
}
