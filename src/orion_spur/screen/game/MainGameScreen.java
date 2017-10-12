package orion_spur.screen.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import juard.contract.Contract;
import juard.injection.Locator;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.factory.IActorFactory;
import orion_spur.common.service.LayerActor.LayerType;
import orion_spur.level.domainvalue.LevelType;
import orion_spur.level.material.LevelElement;
import orion_spur.level.service.ILevelService;
import orion_spur.level.view.LevelActor;
import orion_spur.player.service.IPlayerService;
import orion_spur.player.view.PlayerView;
import orion_spur.remoteObjects.Service.IRemoteObjectService;
import orion_spur.remoteObjects.material.RemoteObject;

// TODO Extract Coordinate and unit converter
public class MainGameScreen implements Screen, ICoordinateConverter, IUnitConverter
{
	private Stage	_currentStage;
	private Camera	_camera;
	
	private int	_width;
	private int	_height;
	
	private LevelElement	_playerLevelElement;
	private LevelActor		_level;
	private PlayerView		_player;
	private ScreenViewport	_viewport;
	
	public MainGameScreen(ILevelService levelService, int width, int height, float worldUnitsPerPixel) throws RuntimeException, Exception
	{
		Locator.register(IUnitConverter.class, () -> this);
		Locator.register(ICoordinateConverter.class, () -> this);
		
		IPlayerService playerService = Locator.get(IPlayerService.class);
		IRemoteObjectService remoteObjectService = Locator.get(IRemoteObjectService.class);
		
		_camera = new OrthographicCamera(_width, _height);
		
		_viewport = new ScreenViewport(_camera);
		_viewport.setUnitsPerPixel(worldUnitsPerPixel);
		
		_level = new LevelActor(levelService, Locator.get(IActorFactory.class), Locator.get(ICoordinateConverter.class), playerService);
		
		_playerLevelElement = new LevelElement(levelService.getPosition(""), new Vector2(), 0, LayerType.LAYER_PLAYER, LevelType.PLAYER, "assets/textures/spaceship.png");
		_player = (PlayerView) _level.addToLayer(_playerLevelElement);
		
		_level.loadLevelElements();
		
		for (RemoteObject remoteObject : remoteObjectService.getAllObjectsForLevel(""))
		{
			LevelElement levelElement = new LevelElement(remoteObject.getPosition(), remoteObject.getMovementVector(), remoteObject.getRotation(), LayerType.LAYER_ANIMATION, LevelType.REMOTE_OBJECT, remoteObject.getAssetFile());
			_level.addToLayer(levelElement);
		}
		
		_currentStage = new Stage(_viewport);
		_currentStage.addActor(_level);
		
		// _player.PositionChanged.add(position -> onPlayerPositionChanged(position));
		playerService.PositionChanged.add(position -> onPlayerPositionChanged(position));
		onPlayerPositionChanged(new Vector2(0, 0));
	}
	
	private void onPlayerPositionChanged(Vector2 offset)
	{
		Vector2 playerPosition = _player.getCenterPosition();
		float playerSpeed = _player.getSpeed();
		
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
			throw new RuntimeException("Position too far away from level (max 1 Ly). Distance is: " + positionInLevel.toString());
		}
		
		return new Vector2(positionInLevel.getX().getMeter(), positionInLevel.getY().getMeter());
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
		String speedString = String.format("%08.2f", playerSpeed);
		System.out.printf("%s km/h     at world pos: %-25s    at universe pos: %s\n", speedString, playerPosition, worldToUniverse(playerPosition));
	}
}
