package orion_spur.common.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
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
	
	// Set this to "true" to enable the drawing of the center point of each image actor. This is useful for debugging
	// purposes.
	private boolean	debugDrawing	= true;
	private Sprite	debugSprite;
	
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
		super.setPosition(levelElement.getPosition().x, levelElement.getPosition().y);
		
		_sprite = new Sprite(texture);
		_sprite.setBounds(getX(), getY(), getWidth(), getHeight());
		_sprite.setOrigin(getWidth() / 2, getHeight() / 2);
		_sprite.setPosition(-getWidth() / 2, -getHeight() / 2);
		
		setRotation(levelElement.getRotation());
		
		createDebugSprite();
		
		Contract.Satisfy(_sprite != null);
		Contract.Satisfy(_sprite.getTexture() != null);
	}
	
	private void createDebugSprite()
	{
		int width = (int) _sprite.getWidth();
		int height = (int) _sprite.getHeight();
		
		if (_sprite.getWidth() >= 10000 || _sprite.getHeight() >= 10000)
		{
			debugDrawing = false;
			return;
		}
		
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(Color.RED);
		pixmap.fillCircle(width / 2, width / 2, 10);
		pixmap.drawRectangle(0, 0, width, height);
		pixmap.drawRectangle(1, 1, width - 2, height - 2);
		pixmap.drawRectangle(2, 2, width - 4, height - 4);
		Texture texture = new Texture(pixmap);
		debugSprite = new Sprite(texture);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		_sprite.draw(batch);
		
		if (debugDrawing)
		{
			debugSprite.setBounds(getX(), getY(), getWidth(), getHeight());
			debugSprite.setOrigin(getWidth() / 2, getHeight() / 2);
			debugSprite.setScale(_sprite.getScaleX());
			debugSprite.setRotation(_sprite.getRotation());
			debugSprite.setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
			
			debugSprite.draw(batch);
		}
	}
	
	@Override
	public void setScale(float scaleXY)
	{
		_sprite.setScale(scaleXY);
		super.setScale(scaleXY);
		
		createDebugSprite();
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
		_sprite.setPosition(x - getWidth() / 2, y - getHeight() / 2);
		getLevelElement().setPosition(new Vector2(x, y));
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
