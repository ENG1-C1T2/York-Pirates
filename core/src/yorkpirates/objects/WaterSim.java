package yorkpirates.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import yorkpirates.GameScreen;

@SuppressWarnings("FieldCanBeLocal") // Don't want to recalculate them every frame.
public class WaterSim implements GameObject {
    private final int TIDE_RADIUS = 300;
    private final int TIDE_PERIOD = 60;
    private final int WAVE_RADIUS = 50;
    private final int WAVE_PERIOD = 1;
    private final int MIN_RADIUS = 3000;

    private final int MAX_RADIUS = TIDE_RADIUS + WAVE_RADIUS + MIN_RADIUS;
    private final double TIDE_ARG = 1 / (TIDE_PERIOD * 1000d);
    private final double WAVE_ARG = 1 / (WAVE_PERIOD * 1000d);

    // The wet sand extends further than the max radius by this amount.
    private final int EXTRA_VISUAL_RADIUS = 50;
    private final int VISUAL_MAX_RADIUS = MAX_RADIUS + EXTRA_VISUAL_RADIUS;

    private long startTime;
    private int waterRadius;

    private final ShapeRenderer shapeRenderer;
    private final Texture ocean;
    private final Texture wetBeach;
    private final Texture dryBeach;

    public WaterSim() {
        shapeRenderer = new ShapeRenderer();

        ocean = new Texture(Gdx.files.internal("ocean.jpg"));
        ocean.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        dryBeach = new Texture(Gdx.files.internal("dryBeach.jpg"));
        dryBeach.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        wetBeach = new Texture(Gdx.files.internal("wetBeach.jpg"));
        wetBeach.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    public int getWaterRadius() {
        return waterRadius;
    }

    @Override
    public void create(GameScreen game) {
        startTime = TimeUtils.millis();
    }

    @Override
    public void update(GameScreen game, float delta) {
        // Simulate water movement.
        final long t = TimeUtils.timeSinceMillis(startTime);

        final double tide = TIDE_RADIUS * Math.sin(TIDE_ARG * t);
        final double wave = WAVE_RADIUS * Math.sin(WAVE_ARG * t);
        final double r = tide + wave + MIN_RADIUS;

        waterRadius = Math.round((float) r);
    }

    @SuppressWarnings("DuplicatedCode") // OpenGL code is more readable when unfolded IMO.
    @Override
    public void render(GameScreen game) {
        GameScreen.Batches batches = game.batches;

        // Fill the screen with the ocean image.
        batches.screen.begin();
        batches.screen.draw(ocean,
                0, 0,
                (int) game.camera.position.x, (int) -game.camera.position.y,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batches.screen.end();

        // Fill everywhere except the water with the wet sand image.
        shapeRenderer.setProjectionMatrix(batches.world.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Gdx.gl20.glClear(GL20.GL_STENCIL_BUFFER_BIT);

        Gdx.gl20.glEnable(GL20.GL_STENCIL_TEST);
        Gdx.gl20.glStencilFunc(GL20.GL_ALWAYS, 0x1, 0xffffffff);
        Gdx.gl20.glStencilOp(GL20.GL_REPLACE, GL20.GL_REPLACE, GL20.GL_REPLACE);
        Gdx.gl20.glColorMask(false, false, false, false);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(0, 0, getWaterRadius());
        shapeRenderer.end();

        batches.screen.begin();

        Gdx.gl20.glColorMask(true, true, true, true);
        Gdx.gl20.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_KEEP);
        Gdx.gl20.glStencilFunc(GL20.GL_NOTEQUAL, 0x1, 0xff);

        batches.screen.draw(wetBeach,
                0, 0,
                (int) game.camera.position.x, (int) -game.camera.position.y,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batches.screen.end();

        // Fill everywhere except the maximum water area with the dry sand image.
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Gdx.gl20.glStencilFunc(GL20.GL_ALWAYS, 0x1, 0xffffffff);
        Gdx.gl20.glStencilOp(GL20.GL_REPLACE, GL20.GL_REPLACE, GL20.GL_REPLACE);
        Gdx.gl20.glColorMask(false, false, false, false);

        shapeRenderer.circle(0, 0, VISUAL_MAX_RADIUS);
        shapeRenderer.end();

        batches.screen.begin();

        Gdx.gl20.glColorMask(true, true, true, true);
        Gdx.gl20.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_KEEP);
        Gdx.gl20.glStencilFunc(GL20.GL_NOTEQUAL, 0x1, 0xff);

        batches.screen.draw(dryBeach,
                0, 0,
                (int) game.camera.position.x, (int) -game.camera.position.y,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batches.screen.end();

        Gdx.gl20.glDisable(GL20.GL_STENCIL_TEST);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        ocean.dispose();
        wetBeach.dispose();
        dryBeach.dispose();
    }

    @Override
    public int getDepth() {
        return 500;
    }
}
