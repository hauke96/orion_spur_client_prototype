package orion_spur;

import com.badlogic.gdx.graphics.GL30;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import juard.contract.Contract;
import orion_spur.LayerActor.LayerType;

public class OrionSpur implements ApplicationListener
{
	private Stage	_currentStage;
	private Camera	_camera;
	
	private int		_width;
	private int		_height;
	
	private Player	_player;
	private LayerActor _layerActor;
	
	public OrionSpur(int width, int height)
	{
		_width = width;
		_height = height;
	}
	
	@Override
	public void create()
	{
		_camera = new OrthographicCamera(_width, _height);
		
		Viewport viewport = new ScreenViewport(_camera);
		_player = new Player();
		_layerActor = new LayerActor();
		
		_layerActor.addToLayer(_player, LayerType.LAYER_PLAYER);
		_layerActor.addToLayer(new Background("assets/textures/milkyway.jpg"), LayerType.LAYER_2_BEHIND);
		_layerActor.addToLayer(new Background("assets/textures/asteroid-0.png"), LayerType.LAYER_1_BEHIND);
		_layerActor.addToLayer(new Background("assets/textures/asteroid-0.png"), LayerType.LAYER_0_BEHIND);
		
		_currentStage = new Stage(viewport);
		_currentStage.addActor(_layerActor);

		_player.PositionChanged.add((Object... data) -> onPlayerPositionChanged((Vector2)data[0]));
		onPlayerPositionChanged(new Vector2());
	}
	
	private void onPlayerPositionChanged(Vector2 offset)
	{
		Vector2 playerPosition = _player.getCenterPosition();
		
		_camera.position.set(playerPosition, 0);
		_camera.update();

		_layerActor.onPlayerPositionChanged(offset);
	}
	
	@Override
	public void resize(int width, int height)
	{
	}
	
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		_currentStage.act(Gdx.graphics.getDeltaTime());
		
		_currentStage.draw();
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
	public void dispose()
	{
		_currentStage.dispose();
	}
}
