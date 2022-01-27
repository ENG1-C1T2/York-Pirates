package yorkpirates;

public class PlayerShip extends Ship {
    @Override
    protected ShipController createController() {
        return new PlayerController();
    }
}
