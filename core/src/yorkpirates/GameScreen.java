package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * The main gameplay screen where the player controls their ship.
 */
public class GameScreen implements Screen {
    private final YorkPirates game;

    Array<Cannonball> cannonballs;
    Texture cannonballTex;
    long lastFired;

    public GameScreen(final YorkPirates game) {
        this.game = game;

        cannonballs = new Array<>();
        cannonballTex = new Texture (Gdx.files.internal("tempCannonball.png"));
        lastFired = 1000000000;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Fill the screen with a blue colour.
        ScreenUtils.clear(0, 0.6f, 1, 1);

        game.playerController.steering();
        game.playerShip.render();
        game.AIController.steering();
        game.AIShip.render();

        game.batch.begin();
        game.batch.draw(game.playerShip.shipImage, game.playerShip.ship.x, game.playerShip.ship.y, 50, 50, 100, 100, 1, 1, game.playerShip.rotation);
        game.batch.draw(game.AIShip.shipImage, game.AIShip.ship.x, game.AIShip.ship.y, 50, 50, 100, 100, 1, 1, game.AIShip.rotation);
        if (cannonballs.size > 0) {
            for (Cannonball cannonball : cannonballs) {
                game.batch.draw(cannonballTex, cannonball.x, cannonball.y, 100, 100);
            }
        }
        game.batch.end();




        Iterator<Cannonball> iter = cannonballs.iterator();
        while (iter.hasNext()) {
            Cannonball cannonball = iter.next();
            cannonball.render();
            //remove and dispose cannonball objects once they have visibly left the screen
            if ((cannonball.x < 0 - 100) || (cannonball.x > 1920)) {
                cannonball.dispose();
                iter.remove();
            }
            if ((cannonball.y < 0 - 100) || (cannonball.y > 1080)) {
                cannonball.dispose();
                iter.remove();
            }


        }

        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            //allow player to fire at most once per second
            if (TimeUtils.nanoTime() - lastFired > 1000000000) {
                Cannonball cannonball;
                cannonball = new Cannonball(game.playerController.firingVelocity, game.playerShip.ship.x, game.playerShip.ship.y);
                cannonballs.add(cannonball);
                cannonball.render();
                lastFired = TimeUtils.nanoTime();
            }
        }




    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        cannonballTex.dispose();
    }
}
