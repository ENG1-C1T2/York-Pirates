package yorkpirates.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import yorkpirates.YorkPirates;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		// Configure the desktop window.
		config.setTitle("York Pirates!");
		Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
		config.setFullscreenMode(displayMode);
		config.setResizable(false);
		config.useVsync(true);
		//Not compatible with macOS currently, special run configuration needed

		// Set stencil buffer size (other arguments are just their defaults).
		config.setBackBufferConfig(8, 8, 8, 8, 16, 8, 0);

		new Lwjgl3Application(new YorkPirates(), config);
	}
}
