package orion_spur.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen
{
	private Stage	_stage;
	private Table	_outer;
	
	public MainMenuScreen()
	{
		_stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(_stage);
		
		Skin skin = new Skin(Gdx.files.internal("assets/skin/orion_spur.json"));
		// Skin skin = new Skin(Gdx.files.internal("assets/skin_old/quantum-horizon-ui.json"));
		
		Button playButton = new TextButton("Play", skin);
		playButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				
			}
		});
		
		_outer = new Table();
		_outer.setFillParent(true);
		_outer.add(playButton).width(250).height(40).padBottom(20);
		_outer.row();
		_outer.add(new TextButton("Exit", skin)).width(135).height(55);
		
		_stage.addActor(_outer);
		
	}
	
	@Override
	public void show()
	{
	}
	
	@Override
	public void render(float delta)
	{
		_stage.act(Gdx.graphics.getDeltaTime());
		_stage.draw();
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
	}
}
