package yorkpirates;

import com.badlogic.gdx.Gdx;

public class PlayerShip extends Ship {
    @Override
    public void create(YorkPirates game) {
        super.create(game);

        // Centre the ship on game start
        ship.x = (Gdx.graphics.getWidth() - ship.width) / 2;
        ship.y = (Gdx.graphics.getHeight() - ship.height) / 2 ;
    }

    @Override
    protected ShipController createController() {
        return new PlayerController();
    }
}
