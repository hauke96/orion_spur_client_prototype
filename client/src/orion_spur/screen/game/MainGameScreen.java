package orion_spur.screen.game;

import java.io.IOException;
import java.net.MalformedURLException;

import com.badlogic.gdx.Gdx;
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
import orion_spur.common.domainvalue.Position;
import orion_spur.common.exception.HttpException;
import orion_spur.common.factory.IActorFactory;
import orion_spur.common.service.LayerActor.LayerType;
import orion_spur.level.domainvalue.LevelType;
import orion_spur.level.material.LevelElement;
import orion_spur.level.service.ILevelService;
import orion_spur.level.view.LevelActor;
import orion_spur.player.service.ILoginService;
import orion_spur.player.service.IPlayerService;
import orion_spur.player.view.PlayerView;
import orion_spur.remoteObjects.Service.IRemoteObjectService;
import orion_spur.remoteObjects.material.RemoteObject;
import orion_spur.ships.material.SpaceShip;

// TODO Extract Coordinate and unit converter
public class MainGameScreen implements Screen, ICoordinateConverter, IUnitConverter
{
	public Event MainScreenInitialized = new Event();
	
	private Stage	_currentStage;
	private Camera	_camera;
	
	private int	_width;
	private int	_height;
	
	private LevelElement	_playerLevelElement;
	private LevelActor		_level;
	private PlayerView		_player;
	private ScreenViewport	_viewport;
	
	public MainGameScreen(ILevelService levelService, ILoginService loginService, int width, int height, float worldUnitsPerPixel) throws RuntimeException, Exception
	{
		Locator.register(IUnitConverter.class, () -> this);
		Locator.register(ICoordinateConverter.class, () -> this);
		
		IPlayerService playerService = Locator.get(IPlayerService.class);
		IRemoteObjectService remoteObjectService = Locator.get(IRemoteObjectService.class);
		
		_camera = new OrthographicCamera(_width, _height);
		
		_viewport = new ScreenViewport(_camera);
		_viewport.setUnitsPerPixel(worldUnitsPerPixel);
		
		_level = new LevelActor(levelService, Locator.get(IActorFactory.class));
		
		IPlayerService.PlayerCreated.add(remoteObject ->
		{
			Gdx.app.postRunnable(new Runnable()
			{
				@Override
				public void run()
				{
					RemoteObject player = playerService.getPlayer();
					
					if (remoteObject.getId().equals(player.getId()))
					{
						try
						{
							loginService.Login(player.getId());
							
							initializeLevel(levelService, worldUnitsPerPixel, playerService, remoteObjectService);
							
							_currentStage = new Stage(_viewport);
							_currentStage.addActor(_level);
							
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
						createRemoteObjectView(remoteObject);
					}
				}
			});
		});
		
		playerService.createPlayer();
		
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
	
	private void initializeLevel(ILevelService levelService, float worldUnitsPerPixel, IPlayerService playerService, IRemoteObjectService remoteObjectService) throws Exception, MalformedURLException, IOException, HttpException
	{
		// TODO refactor this to first get the player and then create the main game
		// screen
		_playerLevelElement =
		        new SpaceShip(playerService.getPlayer().getId(), universeToWorld(levelService.getPosition("")), new Vector2(), 0, LayerType.LAYER_PLAYER, LevelType.PLAYER, "assets/textures/spaceship.png", 1000, 10000, 250);
		_player = (PlayerView) _level.addToLayer(_playerLevelElement);
		
		_level.loadLevelElements();
		
		for (RemoteObject remoteObject : remoteObjectService.getAllObjectsForLevel(""))
		{
			if (!remoteObject.getId().equals(_playerLevelElement.getId()))
			{
				createRemoteObjectView(remoteObject);
			}
		}
		
		// _player.PositionChanged.add(position -> onPlayerPositionChanged(position));
		IPlayerService.PositionChanged.add(offset -> onPlayerPositionChanged(offset));
	}
	
	private void createRemoteObjectView(RemoteObject remoteObject)
	{
		LevelElement levelElement =
		        new LevelElement(remoteObject.getId(), universeToWorld(remoteObject.getPosition()), remoteObject.getMovementVector(), remoteObject.getRotation(), LayerType.LAYER_REMOTE_OBJECTS, LevelType.REMOTE_OBJECT, remoteObject.getAssetFile());
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
	
	@Override
	public Vector2 screenToWorld(Vector2 position)
	{
		Contract.NotNull(position);
		Contract.NotNull(_viewport);
		
		return _viewport.unproject(position);
	}
	
	@Override
	public Position worldToUniverse(Vector2 position)
	{
		Contract.NotNull(position);
		Contract.NotNull(_level);
		
		Position positionInWorld = Position.create(0, 0, (long) position.x, (long) position.y);
		
		return _level.getPosition().add(positionInWorld);
	}
	
	@Override
	public Vector2 worldToScreen(Vector2 position)
	{
		Contract.NotNull(position);
		Contract.NotNull(_viewport);
		
		return _viewport.project(position);
	}
	
	@Override
	public Vector2 universeToWorld(Position position) throws RuntimeException
	{
		Contract.NotNull(position);
		Contract.NotNull(_level);
		
		Position positionInLevel = position.subtract(_level.getPosition());
		
		if (positionInLevel.getX().getLightYear() != 0 || positionInLevel.getY().getLightYear() != 0)
		{
			throw new RuntimeException(
			        "Position too far away from level (max 1 Ly). Distance is: " + positionInLevel.toString());
		}
		
		return new Vector2(positionInLevel.getX().getCentimeter(), positionInLevel.getY().getCentimeter());
	}
	
	@Override
	public float convertToWorld(float value)
	{
		Contract.NotNull(_viewport);
		
		return value * _viewport.getUnitsPerPixel();
	}
	
	@Override
	public float convertFromWorld(float value)
	{
		Contract.NotNull(_viewport);
		
		return value / _viewport.getUnitsPerPixel();
	}
	
	private void printPlayerData(Vector2 playerPosition, float playerSpeed)
	{
		String speedString = String.format("%08.2f", playerSpeed / 100);
		System.out.printf("%s m/s     at world pos: %-25s    at universe pos: %s\n", speedString, playerPosition, worldToUniverse(playerPosition));
	}
}
