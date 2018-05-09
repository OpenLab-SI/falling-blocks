package si.openlab.falling_blocks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import si.openlab.falling_blocks.FallingBlocks;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Falling blocks";
        config.fullscreen = false;
        config.resizable = true;
        config.width = 720;
        config.height = 480;
        config.useHDPI = false;

        new LwjglApplication(new FallingBlocks(), config);
    }
}
