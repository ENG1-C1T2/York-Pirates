package yorkpirates.objects;

/**
 * Any ship controlled by an AIController.
 */
 public class AIShip extends Ship {
    {
        speed = 200;
    }

    @Override
    protected ShipController createController() {
        return new AIController();
    }
}
