package orion_spur.player.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import juard.event.DataEvent;
import juard.log.Logger;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.common.view.ImageActor;
import orion_spur.player.service.IPlayerService;
import orion_spur.ships.material.SpaceShip;

public class PlayerView extends ImageActor
{
	public DataEvent<Vector2> PositionChanged = new DataEvent<Vector2>(); // sending the offset at [0]
	
	private IPlayerService _playerService;
	
	private ICoordinateConverter _coordinateConverter;

	private SpaceShip _ship;
	
	public PlayerView(IPlayerService playerService, IUnitConverter unitConverter, ICoordinateConverter coordinateConverter, Vector2 positionInLevel, SpaceShip ship)
	{
		super(ship.getAssetFile());
		
		Contract.NotNull(playerService);
		Contract.NotNull(unitConverter);
		Contract.NotNull(coordinateConverter);
		Contract.NotNull(positionInLevel);
		Contract.NotNull(ship);
		
		_playerService = playerService;
		_coordinateConverter = coordinateConverter;
		_ship = ship;
		
		setWidth(20);
		setHeight(20);
		setX(positionInLevel.x - getWidth() / 2);
		setY(positionInLevel.y - getHeight() / 2);
		
		_sprite.setBounds(getX(), getY(), getWidth(), getHeight());
		_sprite.setOrigin(getWidth() / 2, getHeight() / 2);
		_sprite.rotate(ship.getRotationDegree());
		
		super.setPosition(getX(), getY());
		
		Contract.Satisfy(_sprite != null);
		Contract.Satisfy(_sprite.getTexture() != null);
	}
	
	@Override
	public void act(float delta)
	{
		Vector2 positionOfView = new Vector2(getX(), getY());
		Vector2 movementAdjustion = new Vector2();
		boolean rotationChanged = false;
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
		{
			movementAdjustion = movementAdjustion.add(_ship.getAcceleration() * delta, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
		{
			movementAdjustion = movementAdjustion.add(-_ship.getAcceleration()* delta, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
		{
			movementAdjustion = movementAdjustion.add(0, _ship.getAcceleration() * delta);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
		{
			movementAdjustion = movementAdjustion.add(0, -_ship.getAcceleration() * delta);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.Q))
		{
			_ship.rotateBy(_ship.getRotationSpeed() * delta);
			rotationChanged = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E))
		{
			_ship.rotateBy(-_ship.getRotationSpeed() * delta);
			rotationChanged = true;
		}
		
		movementAdjustion.rotate(_ship.getRotationDegree());
		
		_ship.accelerateShipBy(movementAdjustion);
		
		positionOfView.add(_ship.getMovementVector().x * delta / getScaleX(), _ship.getMovementVector().y * delta / getScaleY());
		
		if (positionOfView.x != getX() || positionOfView.y != getY() || rotationChanged)
		{
			setPosition(positionOfView.x, positionOfView.y);
		}
		
		_sprite.setRotation(_ship.getRotationDegree());
	}
	
	@Override
	public void setPosition(float x, float y)
	{
		Vector2 offset = new Vector2(x - getX(), y - getY());
		
		super.setPosition(x, y);
		
		try
		{
			_playerService.setPosition(_coordinateConverter.worldToUniverse(getCenterPosition()));
		}
		catch (Exception e)
		{
			Logger.error("Could not send updated position: ", e);
		}
		
		// TODO set position on player service
		PositionChanged.fireEvent(offset);
	}
	
	@Override
	public float getRotation()
	{
		return _ship.getRotationDegree();
	}
	
	public Vector2 getCenterPosition()
	{
		return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}
	
	public float getSpeed()
	{
		return _ship.getMovementVector().len();
	}
}
