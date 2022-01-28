package yorkpirates.objects;

public class AIShip extends Ship {
    {
        speed = 200;
    }

    @Override
    protected ShipController createController() {
        return new AIController();
    }
}
