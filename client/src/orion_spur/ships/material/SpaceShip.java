package orion_spur.ships.material;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.service.LayerActor.LayerType;
import orion_spur.level.domainvalue.LevelType;
import orion_spur.level.material.LevelElement;

public class SpaceShip extends LevelElement
{
	private float	_acceleration;	// m/s²
	private float	_maxSpeed;		// m/s
	private float	_rotationSpeed;	// degree per second
	
	public SpaceShip(String id, Vector2 position, Vector2 movementVector, float rotation, LayerType layer, LevelType type, String assetPath, float accleration, float maxSpeed, float rotationSpeed)
	{
		super(id, position, movementVector, rotation, layer, type, assetPath);
		
		Contract.Satisfy(accleration >= 0);
		Contract.Satisfy(maxSpeed >= 0);
		Contract.Satisfy(rotationSpeed >= 0);
		
		_acceleration = accleration;
		_maxSpeed = maxSpeed;
		_rotationSpeed = rotationSpeed;
	}
	
	public float getAcceleration()
	{
		return _acceleration;
	}
	
	public float getMaxSpeed()
	{
		return _maxSpeed;
	}
	
	public float getRotationSpeed()
	{
		return _rotationSpeed;
	}
	
	public void accelerateShipBy(Vector2 movementAdjustion)
	{
		setMovementVector(getMovementVector().add(movementAdjustion));
		
		if (getMovementVector().len() > _maxSpeed)
		{
			// getMovementVector().setLength(_maxSpeed);
			setMovementVectorLength(_maxSpeed);
		}
	}
	
	@Override
	public int hashCode()
	{
		return (int) (super.hashCode()
		        * getAcceleration()
		        * getMaxSpeed()
		        * getRotationSpeed());
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this.getClass().equals(obj.getClass()))
		{
			return false;
		}
		
		SpaceShip other = (SpaceShip) obj;
		
		return super.equals(other)
		        && this.getAcceleration() == other.getAcceleration()
		        && this.getMaxSpeed() == other.getMaxSpeed()
		        && this.getRotationSpeed() == other.getRotationSpeed();
	}
}
