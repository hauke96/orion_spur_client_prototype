package orion_spur.screen.game;

import java.io.IOException;
import java.net.MalformedURLException;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import juard.contract.Contract;
import juard.event.Event;
import juard.injection.Locator;
import juard.log.Logger;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.common.exception.HttpException;
import orion_spur.common.factory.IActorFactory;
import orion_spur.level.domainvalue.LevelElementType;
import orion_spur.level.material.LevelElement;
import orion_spur.level.service.ILevelService;
import orion_spur.level.view.LevelView;
import orion_spur.level.view.LevelView.LayerZIndex;
import orion_spur.particles.service.IParticleService;
import orion_spur.particles.view.ParticleView;
import orion_spur.player.service.ILoginService;
import orion_spur.player.service.IPlayerService;
import orion_spur.player.view.PlayerView;
import orion_spur.remoteObjects.Service.IRemoteObjectService;
import orion_spur.remoteObjects.material.RemoteObject;

public class MainGameScreen implements Screen
{
	public Event MainScreenInitialized = new Event();
	
	private Stage	_currentStage;
	private Camera	_camera;
	
	private int	_width;
	private int	_height;
	
	private long _lastPrintTime;
	
	private LevelElement	_playerLevelElement;
	private LevelView		_level;
	private PlayerView		_player;
	private ScreenViewport	_viewport;
	
	private ICoordinateConverter	_coordinateConverter;
	private IPlayerService			_playerService;
	private ILevelService			_levelService;
	private IRemoteObjectService	_remoteObjectService;
	private ILoginService			_loginService;
	private IParticleService		_particleService;
	
	private final float WORLD_UNITS_PER_PIXEL;
	
	public MainGameScreen(IUnitConverter unitConverter, ICoordinateConverter coordinateConverter, ILevelService levelService, ILoginService loginService, IParticleService particleService, IPlayerService playerService, IRemoteObjectService remoteObjectService, int width, int height, float worldUnitsPerPixel) throws RuntimeException, Exception
	{
		unitConverter.initialize(worldUnitsPerPixel);
		
		_coordinateConverter = coordinateConverter;
		_levelService = levelService;
		_loginService = loginService;
		_playerService = playerService;
		_remoteObjectService = remoteObjectService;
		_particleService = particleService;
		
		WORLD_UNITS_PER_PIXEL = worldUnitsPerPixel;
		
		_camera = new OrthographicCamera(_width, _height);
		
		_viewport = new ScreenViewport(_camera);
		_viewport.setUnitsPerPixel(worldUnitsPerPixel);
		
		_level = new LevelView(levelService, Locator.get(IActorFactory.class));
		_level.addToLayer(new ParticleView(_particleService),
		    LayerZIndex.LAYER_ANIMATION,
		    "particle view");
		
		_currentStage = new Stage(_viewport);
		_currentStage.addActor(_level);
		
		_coordinateConverter.initialize(_viewport, _level.getPosition());
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				try
				{
					loginService.Logout(playerService.getPlayer().getId());
				}
				catch (IOException | HttpException e)
				{
					Logger.error("Could not logout player", e);
				}
			}
			
		}));
	}
	
	/**
	 * Adds the player to the game.
	 * 
	 * @param newPlayer
	 *            The player to add.
	 * @throws HttpException
	 * @throws IOException
	 */
	public void initialize(LevelElement newPlayer) throws IOException, HttpException
	{
		Contract.NotNull(newPlayer);
		
		if (newPlayer.getId().equals(_playerService.getPlayer().getId()))
		{
			try
			{
				_loginService.Login(newPlayer.getId());
				
				initializeLevel(_levelService, WORLD_UNITS_PER_PIXEL, newPlayer, _remoteObjectService);
				
				onPlayerPositionChanged(new Vector2(0, 0));
				
				MainScreenInitialized.fireEvent();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				Logger.fatal(e.getMessage());
			}
		}
		else
		{
			_level.addToLayer(newPlayer);
		}
	}
	
	private void initializeLevel(ILevelService levelService, float worldUnitsPerPixel, LevelElement player, IRemoteObjectService remoteObjectService) throws Exception, MalformedURLException, IOException, HttpException
	{
		_playerLevelElement = player;
		
		_player = (PlayerView) _level.addToLayer(_playerLevelElement);
		
		_level.loadLevelElements();
		
		for (RemoteObject remoteObject : remoteObjectService.getAllObjectsForLevel(""))
		{
			if (!remoteObject.getId().equals(_playerLevelElement.getId()))
			{
				createRemoteObjectView(remoteObject);
			}
		}
		
		_particleService.syncFromRemote();
		
		// _player.PositionChanged.add(position -> onPlayerPositionChanged(position));
		IPlayerService.PositionChanged.add(offset -> onPlayerPositionChanged(offset));
	}
	
	private void createRemoteObjectView(RemoteObject remoteObject)
	{
		Vector2 worldPosition = _coordinateConverter.universeToWorld(remoteObject.getPosition());
		
		LevelElement levelElement =
		        new LevelElement(remoteObject.getId(),
		            worldPosition,
		            remoteObject.getMovementVector(),
		            remoteObject.getRotation(),
		            LayerZIndex.LAYER_REMOTE_OBJECTS,
		            LevelElementType.REMOTE_OBJECT,
		            remoteObject.getAssetFile());
		_level.addToLayer(levelElement);
	}
	
	private void onPlayerPositionChanged(Vector2 offset)
	{
		Vector2 playerPosition = _player.getCenterWorldPosition();
		float playerSpeed = _playerLevelElement.getSpeed();
		_camera.position.set(playerPosition, 0);
		
		// Set rotation
		_camera.up.set(0, 1, 0);
		_camera.direction.set(0, 0, -1);
		_camera.rotate(_player.getRotation(), 0, 0, 1);
		
		_camera.update();
		
		printPlayerData(playerPosition, playerSpeed);
		
		_level.onPlayerPositionChanged(offset);
	}
	
	@Override
	public void show()
	{
	}
	
	@Override
	public void render(float delta)
	{
		_currentStage.act(delta);
		
		_currentStage.draw();
	}
	
	@Override
	public void resize(int width, int height)
	{
	}
	
	@Override
	public void pause()
	{
	}
	
	@Override
	public void resume()
	{
	}
	
	@Override
	public void hide()
	{
	}
	
	@Override
	public void dispose()
	{
		_currentStage.dispose();
	}
	
	private void printPlayerData(Vector2 playerPosition, float playerSpeed)
	{
		if (_lastPrintTime + 1000 < System.currentTimeMillis())
		{
			_lastPrintTime = System.currentTimeMillis();
			
			String speedString = String.format("%08.2f", playerSpeed / 100);
			System.out.printf("%s m/s     at world pos: %-25s    at universe pos: %s\n",
			    speedString,
			    playerPosition,
			    _coordinateConverter.worldToUniverse(playerPosition));
		}
	}
}
