package orion_spur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;
import juard.event.Event;

public class Player extends Actor
{
	public Event PositionChanged = new Event();
	
	private Sprite _sprite;
	
	private float _speed;
	
	public Player()
	{
		Texture texture = new Texture(Gdx.files.internal("assets/textures/spaceship.png"));
		
		setBounds(400, 300, 50, 50);
		
		_speed = 50f;
		
		_sprite = new Sprite(texture);
		_sprite.setBounds(getX(), getY(), getWidth(), getHeight());
		
		Contract.Satisfy(_sprite != null);
		Contract.Satisfy(_sprite.getTexture() != null);
		Contract.Satisfy(_speed > 0);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		_sprite.draw(batch);
	}
	
	@Override
	public void act(float delta)
	{
		Vector2 position = new Vector2(getX(), getY());
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			position = position.add(_speed * delta,0);
		}
		
		if (position.x != getX() || position.y != getY())
		{
			setPosition(position);
		}
	}

	private void setPosition(Vector2 position)
	{
		setPosition(position.x, position.y);
		_sprite.setPosition(position.x, position.y);
		
		PositionChanged.fireEvent();
	}
	
	public Vector2 getCenterPosition()
	{
		return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}
}
