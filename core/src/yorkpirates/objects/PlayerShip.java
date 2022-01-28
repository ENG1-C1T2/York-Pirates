package yorkpirates.objects;

import yorkpirates.GameScreen;

public class PlayerShip extends Ship {
    @Override
    protected ShipController createController() {
        return new PlayerController();
    }

    @Override
    public void update(GameScreen game, float delta) {
        super.update(game, delta);
    }
}
