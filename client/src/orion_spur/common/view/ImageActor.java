package orion_spur.common.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;
import orion_spur.level.material.LevelElement;

public class ImageActor extends Actor
{
	protected Sprite _sprite;
	
	private LevelElement _levelElement;
	
	public ImageActor(LevelElement levelElement)
	{
		Contract.NotNull(levelElement);
		
		_levelElement = levelElement;
		
		Texture texture = new Texture(Gdx.files.internal(levelElement.getAssetPath()));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		initialize(levelElement, texture.getWidth() * 6, texture.getHeight() * 6, texture);
	}
	
	public ImageActor(LevelElement levelElement, int width, int height)
	{
		Contract.NotNull(levelElement);
		// TODO contracts
		
		_levelElement = levelElement;
		
		Texture texture = new Texture(Gdx.files.internal(levelElement.getAssetPath()));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		initialize(levelElement, width, height, texture);
	}
	
	private void initialize(LevelElement levelElement, int width, int height, Texture texture)
	{
		setWidth(width);
		setHeight(height);
		
		setBounds(0, 0, getWidth(), getHeight());
		
		_sprite = new Sprite(texture);
		_sprite.setBounds(getX(), getY(), getWidth(), getHeight());
		_sprite.setOrigin(getWidth() / 2, getHeight() / 2);
		_sprite.rotate(levelElement.getRotation());
		
		setX(levelElement.getPosition().x - getWidth() / 2);
		setY(levelElement.getPosition().y - getHeight() / 2);
		
		_sprite.setPosition(getX(), getY());
		
		System.out.println("added: " + levelElement.getAssetPath()
		        + " - at sprite-pos ("
		        + _sprite.getX()
		        + ", "
		        + _sprite.getY()
		        + ") - at element pos "
		        + levelElement.getPosition());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if (_levelElement.getAssetPath().contains("space") || _levelElement.getAssetPath().contains("asteroid"))
		{
			System.out.println(_levelElement.getAssetPath() + " -> "
			        + _levelElement.getPosition()
			        + " -- "
			        + _sprite.getX()
			        + ", "
			        + _sprite.getY());
		}
		
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
	
	protected LevelElement getLevelElement()
	{
		Contract.NotNull(_levelElement);
		return _levelElement;
	}
}
