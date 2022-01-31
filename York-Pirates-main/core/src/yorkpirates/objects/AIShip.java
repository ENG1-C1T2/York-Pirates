package yorkpirates.objects;

public class AIShip extends Ship {
    @Override
    protected ShipController createController() {
        return new AIController();
    }
}
