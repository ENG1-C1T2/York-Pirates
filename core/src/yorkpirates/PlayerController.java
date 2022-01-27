package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PlayerController implements ShipController {
    Vector2 velocity;
    Vector2 firingVelocity;

    //speed value arbitrary for now - subject to change with testing
    final int speed = 300;

    public PlayerController() {
        velocity = new Vector2();
        firingVelocity = new Vector2();
    }


    @Override
    public void steering() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -speed;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = speed;
        }
        else {
            velocity.x = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocity.y = speed;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocity.y = -speed;
        }
        else {
            velocity.y = 0;
        }

        //ensure there is always a velocity for cannonballs to use if the player fires while the ship is stationary
        if ((velocity.x != 0) || (velocity.y != 0)) {
            firingVelocity.x = velocity.x;
            firingVelocity.y = velocity.y;
        }
    }




    @Override
    public boolean shouldFire() {
        return false;
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public void takeDamage() {

    }



}