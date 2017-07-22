package orion_spur;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main
{
	public static void main(String[] args)
	{
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Orion Spur";
        cfg.useGL30=true;
        cfg.width = 800;
        cfg.height = 600;
        
        new LwjglApplication(new OrionSpur(), cfg);
	}
}
