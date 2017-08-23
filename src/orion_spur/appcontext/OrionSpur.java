package orion_spur.appcontext;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;

import juard.injection.Locator;
import orion_spur.level.service.ILevelService;
import orion_spur.player.service.IPlayerService;
import orion_spur.screen.game.MainGameScreen;
import orion_spur.screen.mainmenu.MainMenuScreen;

public class OrionSpur extends Game implements ApplicationListener
{
	private static final float WORLD_UNITS_PER_PIXEL = 0.2f;
	
	private int	_width;
	private int	_height;
	
	public OrionSpur(int width, int height)
	{
		_width = width;
		_height = height;
	}
	
	@Override
	public void create()
	{
		MainMenuScreen screen = new MainMenuScreen();
		screen.PlayButtonClicked.add(() ->
		{
			Screen newScreen = new MainGameScreen(Locator.get(IPlayerService.class), Locator.get(ILevelService.class), _width, _height, WORLD_UNITS_PER_PIXEL);
			setScreen(newScreen);
		});
		
		setScreen(screen);
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
		
		super.render();
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
	}
}
