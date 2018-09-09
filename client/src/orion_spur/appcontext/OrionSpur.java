package orion_spur.appcontext;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;

import juard.injection.Locator;
import juard.log.Logger;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.level.service.ILevelService;
import orion_spur.particles.service.IParticleService;
import orion_spur.player.material.SpaceShip;
import orion_spur.player.service.ILoginService;
import orion_spur.player.service.IPlayerService;
import orion_spur.remoteObjects.Service.IRemoteObjectService;
import orion_spur.screen.game.MainGameScreen;
import orion_spur.screen.mainmenu.MainMenuScreen;

public class OrionSpur extends Game implements ApplicationListener
{
	private static final float WORLD_UNITS_PER_PIXEL = 6f;
	
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
			Gdx.app.postRunnable(() ->
			{
				try
				{
					IPlayerService playerService = Locator.get(IPlayerService.class);
					
					MainGameScreen newScreen = new MainGameScreen(Locator.get(IUnitConverter.class),
					    Locator.get(ICoordinateConverter.class),
					    Locator.get(ILevelService.class),
					    Locator.get(ILoginService.class),
					    Locator.get(IParticleService.class),
					    playerService,
					    Locator.get(IRemoteObjectService.class),
					    _width,
					    _height,
					    WORLD_UNITS_PER_PIXEL);
					
					setScreen(newScreen);
					screen.dispose();
					
					IPlayerService.PlayerCreated.add(newPlayer ->
					{
						Gdx.app.postRunnable(() ->
						{
							try
							{
								newScreen.initialize(newPlayer);
							}
							catch (Exception e)
							{
								Logger.error("Could not initialize the main game screen", e);
							}
						});
					});
					
					// TODO Temporary solution to avoid crash on existing players. When there's a login-dialog things
					// will change.
					try
					{
						SpaceShip player = playerService.getPlayer();
						newScreen.initialize(player);
					}
					catch (Exception e)
					{
						Logger.error("Could not get player", e);
						playerService.createPlayer();
					}
				}
				catch (Exception e)
				{
					Logger.error("Could not create main game screen", e);
				}
			});
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
