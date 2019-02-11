package orion_spur.appcontext;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.hauke_stieler.goms.service.GoMessagingService;
import de.hauke_stieler.goms.service.TcpConnectionService;
import juard.injection.Locator;
import juard.injection.ResolutionFailedException;
import juard.log.Logger;
import orion_spur.common.converter.CoordinateConverterImpl;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.common.converter.UnitConverterImpl;
import orion_spur.common.factory.ActorFactoryImpl;
import orion_spur.common.factory.IActorFactory;
import orion_spur.common.service.CurrentWorldService;
import orion_spur.common.service.ICurrentWorldService;
import orion_spur.level.service.ILevelService;
import orion_spur.level.service.LevelDummyService;
import orion_spur.particles.service.IParticleService;
import orion_spur.particles.service.ParticleServiceDummy;
import orion_spur.particles.service.ParticleServiceProxy;
import orion_spur.player.service.ILoginService;
import orion_spur.player.service.IPlayerService;
import orion_spur.player.service.PlayerLoginServiceProxy;
import orion_spur.player.service.PlayerServiceDummy;
import orion_spur.player.service.PlayerServiceProxy;
import orion_spur.remoteObjects.Service.IRemoteObjectService;
import orion_spur.remoteObjects.Service.RemoteObjectServiceProxy;

public class Main
{
	private static final int	WIDTH					= 600;
	private static final int	HEIGHT					= 700;
	private static final float	WORLD_UNITS_PER_PIXEL	= 6f;
	
	private static final boolean USE_DUMMY_SERVICES = false;
	
	public static void main(String[] args)
	{
		initServices();
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Orion Spur";
		cfg.useGL30 = true;
		cfg.width = WIDTH;
		cfg.height = HEIGHT;
		cfg.samples = 32;
		cfg.useHDPI = true;
		
		OrionSpur orionSpur = new OrionSpur(WIDTH, HEIGHT, WORLD_UNITS_PER_PIXEL);
		new LwjglApplication(orionSpur, cfg);
	}
	
	private static void initServices()
	{
		if (USE_DUMMY_SERVICES)
		{
			Locator.register(IPlayerService.class,
			    () -> new PlayerServiceDummy(Locator.get(ICoordinateConverter.class)));
			Locator.register(IParticleService.class, () -> new ParticleServiceDummy());
			// TODO create dummy service for remote objects
		}
		else
		{
			Locator.register(GoMessagingService.class, () ->
			{
				try
				{
					return new GoMessagingService(new TcpConnectionService("localhost", 55545));
				}
				catch (Throwable t)
				{
					Logger.error("Could not create GoMessagingService.", t);
					throw new ResolutionFailedException(GoMessagingService.class);
				}
			});
			Locator.register(IPlayerService.class,
			    () -> new PlayerServiceProxy(
			        Locator.get(GoMessagingService.class),
			        Locator.get(ICoordinateConverter.class)));
			Locator.register(IRemoteObjectService.class,
			    () -> new RemoteObjectServiceProxy(
			        Locator.get(GoMessagingService.class)));
			Locator.register(ILoginService.class, () -> new PlayerLoginServiceProxy());
		}
		
		Locator.register(IUnitConverter.class, () -> new UnitConverterImpl());
		Locator.register(ICoordinateConverter.class, () -> new CoordinateConverterImpl());
		Locator.register(ICurrentWorldService.class, () -> new CurrentWorldService(WORLD_UNITS_PER_PIXEL));
		Locator.register(ILevelService.class, () -> new LevelDummyService());
		Locator.register(IParticleService.class,
		    () -> new ParticleServiceProxy(Locator.get(ICoordinateConverter.class)));
		Locator.register(IActorFactory.class,
		    () -> new ActorFactoryImpl(
		        Locator.get(IPlayerService.class),
		        Locator.get(IUnitConverter.class),
		        Locator.get(ICoordinateConverter.class),
		        Locator.get(IParticleService.class),
		        Locator.get(ICurrentWorldService.class)));
		Locator.register(IPlayerService.class,
		    () -> new PlayerServiceProxy(Locator.get(GoMessagingService.class),
		        Locator.get(ICoordinateConverter.class)));
		Locator.register(IRemoteObjectService.class,
		    () -> new RemoteObjectServiceProxy(Locator.get(GoMessagingService.class)));
	}
}
