package orion_spur.common.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;

public class ImageActor extends Actor
{
	protected Sprite _sprite;
	
	public ImageActor(String file)
	{
		Contract.NotNull(file);
		
		Texture texture = new Texture(Gdx.files.internal(file));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		setBounds(0, 0, texture.getWidth() / 5, texture.getHeight() / 5);
		
		_sprite = new Sprite(texture);
		_sprite.setBounds(getX(), getY(), getWidth(), getHeight());
		_sprite.setOrigin(getWidth() / 2, getHeight() / 2);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		_sprite.draw(batch);
	}
	
	@Override
	public void setScale(float scaleXY)
	{
		_sprite.setScale(scaleXY);
		super.setScale(scaleXY);
	}
	
	@Override
	public void moveBy(float x, float y)
	{
		_sprite.translate(x, y);
		super.moveBy(x, y);
	}
	
	@Override
	public void setPosition(float x, float y)
	{
		_sprite.setPosition(x, y);
		
		super.setPosition(x, y);
	}
}
