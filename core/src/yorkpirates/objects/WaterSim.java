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

    public WaterSim() {
        shapeRenderer = new ShapeRenderer();
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

        final Color water = new Color(0.122f, 0.406f, 0.725f, 1f);
        final Color wetSand = new Color(0.789f, 0.702f, 0.461f, 1f);
        final Color drySand = new Color(0.848f, 0.755f, 0.495f, 1f);

        Gdx.gl20.glClear(GL20.GL_STENCIL_BUFFER_BIT);

        // Fill the screen with the ocean image.
        shapeRenderer.setProjectionMatrix(batches.screen.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(water);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        // Fill everywhere except the water with the wet sand image.
        shapeRenderer.setProjectionMatrix(batches.world.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Gdx.gl20.glEnable(GL20.GL_STENCIL_TEST);
        Gdx.gl20.glStencilFunc(GL20.GL_ALWAYS, 0x1, 0xffffffff);
        Gdx.gl20.glStencilOp(GL20.GL_REPLACE, GL20.GL_REPLACE, GL20.GL_REPLACE);
        Gdx.gl20.glColorMask(false, false, false, false);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(0, 0, getWaterRadius());
        shapeRenderer.end();

        shapeRenderer.setProjectionMatrix(batches.screen.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Gdx.gl20.glColorMask(true, true, true, true);
        Gdx.gl20.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_KEEP);
        Gdx.gl20.glStencilFunc(GL20.GL_NOTEQUAL, 0x1, 0xff);

        shapeRenderer.setColor(wetSand);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        // Fill everywhere except the maximum water area with the dry sand image.
        shapeRenderer.setProjectionMatrix(batches.world.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Gdx.gl20.glStencilFunc(GL20.GL_ALWAYS, 0x1, 0xffffffff);
        Gdx.gl20.glStencilOp(GL20.GL_REPLACE, GL20.GL_REPLACE, GL20.GL_REPLACE);
        Gdx.gl20.glColorMask(false, false, false, false);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(0, 0, VISUAL_MAX_RADIUS);
        shapeRenderer.end();

        shapeRenderer.setProjectionMatrix(batches.screen.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Gdx.gl20.glColorMask(true, true, true, true);
        Gdx.gl20.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_KEEP);
        Gdx.gl20.glStencilFunc(GL20.GL_NOTEQUAL, 0x1, 0xff);

        shapeRenderer.setColor(drySand);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        Gdx.gl20.glDisable(GL20.GL_STENCIL_TEST);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    @Override
    public int getDepth() {
        return 500;
    }
}
