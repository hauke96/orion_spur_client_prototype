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
import juard.event.EventArgs;

public class Player extends Actor
{
	public EventArgs PositionChanged = new EventArgs();
	
	private Sprite _sprite;
	
	private float _speed;
	private Vector2 _directionOfMovement;
	
	public Player()
	{
		Texture texture = new Texture(Gdx.files.internal("assets/textures/spaceship.png"));
		
		setBounds(400, 300, 50, 50);
		
		_speed = 10;
		_directionOfMovement = new Vector2();
		
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
			_directionOfMovement = _directionOfMovement.add(_speed * delta, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			_directionOfMovement = _directionOfMovement.add(-_speed * delta, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			_directionOfMovement = _directionOfMovement.add(0, _speed * delta);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			_directionOfMovement = _directionOfMovement.add(0, -_speed * delta);
		}
		
		position.add(_directionOfMovement);
		
		if (position.x != getX() || position.y != getY())
		{
			setPosition(position.x, position.y);
		}
	}
	
	@Override
	public void setPosition(float x, float y)
	{
		Vector2 offset = new Vector2(x-getX(), y-getY());
		
		_sprite.setPosition(x, y);
		
		PositionChanged.fireEvent(offset);
		
		super.setPosition(x, y);
	}
	
	public Vector2 getCenterPosition()
	{
		return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}
}
