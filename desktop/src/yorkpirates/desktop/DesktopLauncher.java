package yorkpirates.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import yorkpirates.YorkPirates;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Configure the desktop window.
		config.title = "York Pirates!";
		config.width = 1920;
		config.height = 1080;
		config.resizable = false;
		config.fullscreen = true;

		new LwjglApplication(new YorkPirates(), config);
	}
}
