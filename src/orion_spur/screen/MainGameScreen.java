package orion_spur.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import juard.contract.Contract;
import juard.injection.Locator;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.factory.IActorFactory;
import orion_spur.common.service.ICoordinateConverter;
import orion_spur.common.service.LayerActor.LayerType;
import orion_spur.level.domainvalue.LevelType;
import orion_spur.level.material.LevelElement;
import orion_spur.level.service.ILevelService;
import orion_spur.level.view.LevelActor;
import orion_spur.player.service.IPlayerService;
import orion_spur.player.view.Player;

public class MainGameScreen implements Screen, ICoordinateConverter
{
	private Stage	_currentStage;
	private Camera	_camera;
	
	private int	_width;
	private int	_height;
	
	private LevelElement	_playerLevelElement;
	private LevelActor		_level;
	private Player			_player;
	private ScreenViewport	_viewport;
	
	public MainGameScreen(IPlayerService playerService, int width, int height, float worldUnitsPerPixel)
	{
		Contract.NotNull(playerService);
		
		_camera = new OrthographicCamera(_width, _height);
		
		_viewport = new ScreenViewport(_camera);
		_viewport.setUnitsPerPixel(worldUnitsPerPixel);
		
		_level = new LevelActor(Locator.get(ILevelService.class), Locator.get(IActorFactory.class));
		
		_playerLevelElement = new LevelElement(Position.create(0, 0, 0, 0), LayerType.LAYER_PLAYER, LevelType.PLAYER, "assets/textures/spaceship.png");
		_player = (Player) _level.addToLayer(_playerLevelElement);
		
		_currentStage = new Stage(_viewport);
		_currentStage.addActor(_level);
		
		_player.PositionChanged.add((Object... data) -> onPlayerPositionChanged((Vector2) data[0]));
		onPlayerPositionChanged(new Vector2());
	}
	
	private void onPlayerPositionChanged(Vector2 offset)
	{
		Vector2 playerPosition = _player.getCenterPosition();
		
		_camera.position.set(playerPosition, 0);
		_camera.update();
		
		System.out.println(playerPosition);
		screenToWorld(new Vector2(600, 349));
		System.out.println(_viewport.project(playerPosition).toString());
		
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
		return _viewport.unproject(position);
	}
	
	@Override
	public Position worldToUniverse(Vector2 position)
	{
		return null;
	}
	
	@Override
	public Vector2 worldToScreen(Vector2 position)
	{
		return _viewport.project(position);
	}
	
	@Override
	public Vector2 universeToWorld(Position position)
	{
		return null;
	}
}
