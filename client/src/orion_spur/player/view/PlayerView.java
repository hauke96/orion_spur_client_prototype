package orion_spur.player.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import juard.log.Logger;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.common.view.ImageActor;
import orion_spur.player.service.IPlayerService;
import orion_spur.ships.material.SpaceShip;

public class PlayerView extends ImageActor
{
	private IPlayerService _playerService;
	
	public PlayerView(IPlayerService playerService, IUnitConverter unitConverter, SpaceShip levelElement, Vector2 positionInLevel)
	{
		super(levelElement, 600, 600);
		
		Contract.NotNull(playerService);
		Contract.NotNull(unitConverter);
		Contract.NotNull(positionInLevel);
		
		_playerService = playerService;
	}
	
	@Override
	public void act(float delta)
	{
		Vector2 positionOfView = new Vector2(getX(), getY());
		Vector2 movementAdjustion = new Vector2();
		boolean rotationChanged = false;
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
		{
			movementAdjustion = movementAdjustion.add(getLevelElement().getAcceleration() * delta, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
		{
			movementAdjustion = movementAdjustion.add(-getLevelElement().getAcceleration() * delta, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
		{
			movementAdjustion = movementAdjustion.add(0, getLevelElement().getAcceleration() * delta);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
		{
			movementAdjustion = movementAdjustion.add(0, -getLevelElement().getAcceleration() * delta);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.Q))
		{
			getLevelElement().rotateBy(getLevelElement().getRotationSpeed() * delta);
			rotationChanged = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E))
		{
			getLevelElement().rotateBy(-getLevelElement().getRotationSpeed() * delta);
			rotationChanged = true;
		}
		
		movementAdjustion.rotate(getLevelElement().getRotation());
		
		getLevelElement().accelerateShipBy(movementAdjustion);
		
		positionOfView.add(getLevelElement().getMovementVector().x * delta
		        / getScaleX(), getLevelElement().getMovementVector().y * delta / getScaleY());
		
		if (positionOfView.x != getX() || positionOfView.y != getY() || rotationChanged)
		{
			setPosition(positionOfView.x, positionOfView.y);
		}
		
		_sprite.setRotation(getLevelElement().getRotation());
	}
	
	@Override
	public void setPosition(float x, float y)
	{
		Vector2 offset = new Vector2(x - getX(), y - getY());
		
		getLevelElement().setPosition(new Vector2(getCenterPosition()));
		
		super.setPosition(x, y);
		
		try
		{
			_playerService.setPosition(getLevelElement());
		}
		catch (Exception e)
		{
			Logger.error("Could not send updated position: ", e);
		}
	}
	
	@Override
	public float getRotation()
	{
		return getLevelElement().getRotation();
	}
	
	public Vector2 getCenterPosition()
	{
		return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}
	
	public float getSpeed()
	{
		return getLevelElement().getMovementVector().len();
	}
	
	@Override
	protected SpaceShip getLevelElement()
	{
		return (SpaceShip) super.getLevelElement();
	}
}
