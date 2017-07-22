package orion_spur;

import com.badlogic.gdx.graphics.GL30;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import juard.contract.Contract;
import orion_spur.LayerActor.LayerType;

public class OrionSpur implements ApplicationListener
{
	private Stage	_currentStage;
	private Camera	_camera;
	private int _width;
	private int _height;
	
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
		
		_currentStage = new Stage(viewport);
		
		LayerActor layerActor = new LayerActor();
		
		Player player = new Player();
		
		layerActor.addToLayer(player, LayerType.LAYER_PLAYER);
		
		_currentStage.addActor(layerActor);
	}
	
	@Override
	public void resize(int width, int height)
	{
	}
	
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
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
