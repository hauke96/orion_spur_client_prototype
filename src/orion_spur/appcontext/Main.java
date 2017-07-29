package orion_spur.appcontext;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import juard.injection.Locator;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.common.factory.ActorFactoryImpl;
import orion_spur.common.factory.IActorFactory;
import orion_spur.level.service.ILevelService;
import orion_spur.level.service.LevelDummyService;
import orion_spur.player.service.IPlayerService;
import orion_spur.player.service.PlayerServiceDummy;
import orion_spur.player.service.PlayerServiceProxy;

public class Main
{
	private static final int	WIDTH	= 1200;
	private static final int	HEIGHT	= 700;
	
	private static final boolean USE_DUMMY_SERVICES = false;
	
	public static void main(String[] args)
	{
		initDummyServices();
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Orion Spur";
		cfg.useGL30 = true;
		cfg.width = WIDTH;
		cfg.height = HEIGHT;
		
		OrionSpur orionSpur = new OrionSpur(WIDTH, HEIGHT);
		new LwjglApplication(orionSpur, cfg);
	}
	
	private static void initDummyServices()
	{
		if (USE_DUMMY_SERVICES)
		{
			Locator.register(IPlayerService.class, () -> new PlayerServiceDummy());
		}
		else
		{
			Locator.register(IPlayerService.class, () -> new PlayerServiceProxy());
			
		}
		
		Locator.register(ILevelService.class, () -> new LevelDummyService());
		Locator.register(IActorFactory.class, () -> new ActorFactoryImpl(Locator.get(IPlayerService.class), Locator.get(ILevelService.class), Locator.get(IUnitConverter.class), Locator.get(ICoordinateConverter.class)));
	}
}
