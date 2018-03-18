package orion_spur.common.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
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
		Contract.Satisfy(width >= 0);
		Contract.Satisfy(height >= 0);
		
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
		
		Contract.Satisfy(_sprite != null);
		Contract.Satisfy(_sprite.getTexture() != null);
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
		getLevelElement().setPosition(new Vector2(x + getWidth() / 2, y + getHeight() / 2));
		super.setPosition(x, y);
	}
	
	@Override
	public void setRotation(float degrees)
	{
		_sprite.rotate(degrees - _sprite.getRotation());
		getLevelElement().rotateTo(degrees);
		super.setRotation(degrees);
	}
	
	@Override
	public void rotateBy(float amountInDegrees)
	{
		setRotation(amountInDegrees + getRotation());
	}
	
	@Override
	public float getRotation()
	{
		float rotation = super.getRotation();
		
		Contract.Satisfy(rotation == _sprite.getRotation());
		Contract.Satisfy(rotation == getLevelElement().getRotation());
		
		return rotation;
	}
	
	protected LevelElement getLevelElement()
	{
		Contract.NotNull(_levelElement);
		return _levelElement;
	}
}
