package orion_spur.appcontext;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main
{
	private static final int	WIDTH	= 1600;
	private static final int	HEIGHT	= 900;
	
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
