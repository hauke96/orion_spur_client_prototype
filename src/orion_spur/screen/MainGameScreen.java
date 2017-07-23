package orion_spur.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import juard.contract.Contract;
import orion_spur.common.service.LayerActor.LayerType;
import orion_spur.level.view.LevelActor;
import orion_spur.player.service.IPlayerService;
import orion_spur.player.view.Player;

public class MainGameScreen implements Screen
{
	private Stage	_currentStage;
	private Camera	_camera;
	
	private int	_width;
	private int	_height;
	
	private Player		_player;
	private LevelActor	_level;
	
	public MainGameScreen(IPlayerService playerService, int width, int height, float worldUnitsPerPixel)
	{
		Contract.NotNull(playerService);
		
		_camera = new OrthographicCamera(_width, _height);
		
		ScreenViewport viewport = new ScreenViewport(_camera);
		viewport.setUnitsPerPixel(worldUnitsPerPixel);
		
		_level = new LevelActor();
		
		_player = new Player(playerService, "assets/textures/spaceship.png");
		_level.addToLayer(_player, LayerType.LAYER_PLAYER);
		
		_currentStage = new Stage(viewport);
		_currentStage.addActor(_level);
		
		_player.PositionChanged.add((Object... data) -> onPlayerPositionChanged((Vector2) data[0]));
		onPlayerPositionChanged(new Vector2());
	}
	
	private void onPlayerPositionChanged(Vector2 offset)
	{
		Vector2 playerPosition = _player.getCenterPosition();
		
		_camera.position.set(playerPosition, 0);
		_camera.update();
		
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
}
