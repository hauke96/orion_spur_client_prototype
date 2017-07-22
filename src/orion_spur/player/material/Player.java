package orion_spur.player.material;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import juard.event.EventArgs;
import orion_spur.common.material.ImageActor;

public class Player extends ImageActor
{
	public EventArgs PositionChanged = new EventArgs();
	
	private float	_movementSpeed;
	private float	_maxSpeed;
	private float	_rotationSpeed;	// degree per second
	private float	_rotationDegree;// degree the player is rotated
	
	private Vector2 _movementVector;
	
	public Player(String file)
	{
		super(file);
		
		setBounds(80, 60, 20, 20);
		
		_movementSpeed = 0.5f;
		_maxSpeed = 4;
		_rotationSpeed = 250;
		_rotationDegree = 0;
		
		_movementVector = new Vector2();
		
		_sprite.setBounds(getX(), getY(), getWidth(), getHeight());
		_sprite.setOrigin(getWidth() / 2, getHeight() / 2);
		_sprite.rotate(_rotationDegree);
		
		Contract.Satisfy(_sprite != null);
		Contract.Satisfy(_sprite.getTexture() != null);
		Contract.Satisfy(_movementSpeed > 0);
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
		Vector2 movementAdjustion = new Vector2();
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
		{
			movementAdjustion = movementAdjustion.add(_movementSpeed * delta, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
		{
			movementAdjustion = movementAdjustion.add(-_movementSpeed * delta, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
		{
			movementAdjustion = movementAdjustion.add(0, _movementSpeed * delta);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
		{
			movementAdjustion = movementAdjustion.add(0, -_movementSpeed * delta);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.Q))
		{
			_rotationDegree += _rotationSpeed * delta;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E))
		{
			_rotationDegree -= _rotationSpeed * delta;
		}
		
		movementAdjustion.rotate(_rotationDegree);
		
		_movementVector = _movementVector.add(movementAdjustion);
		
		if (_movementVector.len() > _maxSpeed)
		{
			_movementVector.setLength(_maxSpeed);
		}
		
		position.add(_movementVector);
		
		if (position.x != getX() || position.y != getY())
		{
			setPosition(position.x, position.y);
		}
		
		_sprite.setRotation(_rotationDegree);
	}
	
	@Override
	public void setPosition(float x, float y)
	{
		Vector2 offset = new Vector2(x - getX(), y - getY());
		
		_sprite.setPosition(x, y);
		
		PositionChanged.fireEvent(offset);
		
		super.setPosition(x, y);
	}
	
	public Vector2 getCenterPosition()
	{
		return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}
}
