package orion_spur.ships.material;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;

public class SpaceShip
{
	private float	_acceleration;	// m/s²
	private float	_maxSpeed;		// m/s
	private float	_rotationSpeed;	// degree per second
	private float	_rotationDegree;// degree the player is rotated
	
	private Vector2	_movementVector;
	private String	_assetFile;
	
	public SpaceShip(float accleration, float maxSpeed, float rotationSpeed, float rotationDegree, String assetFile)
	{
		Contract.Satisfy(accleration >= 0);
		Contract.Satisfy(maxSpeed >= 0);
		Contract.Satisfy(rotationSpeed >= 0);
		Contract.NotNullOrEmpty(assetFile);
		
		_acceleration = accleration;
		_maxSpeed = maxSpeed;
		_rotationSpeed = rotationSpeed;
		_rotationDegree = rotationDegree;
		_assetFile = assetFile;
		
		_movementVector = new Vector2();
		
		Contract.NotNull(_movementVector);
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
	
	public float getRotationDegree()
	{
		return _rotationDegree;
	}
	
	public Vector2 getMovementVector()
	{
		return _movementVector;
	}
	
	public void accelerateShipBy(Vector2 movementAdjustion)
	{
		_movementVector = _movementVector.add(movementAdjustion);
		
		if (_movementVector.len() > _maxSpeed)
		{
			_movementVector.setLength(_maxSpeed);
		}
	}
	
	public String getAssetFile()
	{
		return _assetFile;
	}
	
	public void rotateBy(float amount)
	{
		_rotationDegree += amount;
	}
	
	public void rotateTo(float newDegree)
	{
		_rotationDegree = newDegree;
	}
}
