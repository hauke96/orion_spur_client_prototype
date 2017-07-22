package orion_spur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main
{
	private static final int	WIDTH	= 800;
	private static final int	HEIGHT	= 600;
	
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Orion Spur";
		cfg.useGL30 = true;
		cfg.width = WIDTH;
		cfg.height = HEIGHT;
		
		OrionSpur orionSpur = new OrionSpur(WIDTH, HEIGHT);
		new LwjglApplication(orionSpur, cfg);
	}
}
