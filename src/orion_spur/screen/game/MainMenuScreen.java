package orion_spur.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import juard.event.Event;

public class MainMenuScreen implements Screen
{
	public Event PlayButtonClicked = new Event();
	
	private Stage	_stage;
	private Table	_outer;
	
	public MainMenuScreen()
	{
		_stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(_stage);
		
		Skin skin = new Skin(Gdx.files.internal("assets/skin/orion_spur.json"));
		
		Button playButton = new TextButton("Play", skin);
		TextButton exitButton = new TextButton("Exit", skin);
		
		registerPlayListener(playButton);
		registerExitButton(exitButton);
		
		Pixmap greenPixel = new Pixmap(1, 1, Format.RGBA8888);
		greenPixel.setColor(0, 1, 0, 1);
		greenPixel.drawLine(0, 0, 1, 0);
		Texture greenPixelTexture = new Texture(greenPixel);
		greenPixel.dispose();
		
		Pixmap transparentGrayPixel = new Pixmap(1, 1, Format.RGBA8888);
		transparentGrayPixel.setColor(0.2f, 0.2f, 0.2f, 0.4f);
		transparentGrayPixel.fill();
		Texture transparentGrayTexture = new Texture(transparentGrayPixel);
		transparentGrayPixel.dispose();
		
		Table titleTable = new Table();
		titleTable.add(new Image(greenPixelTexture)).width(500).padBottom(5);
		titleTable.row();
		titleTable.add(new Image(greenPixelTexture)).width(750).padBottom(30);
		titleTable.row();
		titleTable.add(new Label("Orion Spur", skin)).padBottom(30);
		titleTable.row();
		titleTable.add(new Image(greenPixelTexture)).width(750).padBottom(5);
		titleTable.row();
		titleTable.add(new Image(greenPixelTexture)).width(500).padBottom(100);
		
		_outer = new Table();
		_outer.setFillParent(true);
		_outer.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("assets/textures/milkyway.jpg")))));
		
		Table inner = new Table();
		inner.pad(20);
		inner.background(new TextureRegionDrawable(new TextureRegion(transparentGrayTexture)));
		inner.add(titleTable);
		inner.row();
		inner.add(playButton).width(250).height(40).padBottom(20);
		inner.row();
		inner.add(exitButton).width(250).height(40).padBottom(100);
		
		_outer.add(inner);
		
		// titleTable.debug();
		// inner.debug();
		// _outer.debug();
		
		_stage.addActor(_outer);
		
	}
	
	private void registerPlayListener(Button button)
	{
		button.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				PlayButtonClicked.fireEvent();
			}
		});
	}
	
	private void registerExitButton(TextButton button)
	{
		button.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				Gdx.app.exit();
			}
		});
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
