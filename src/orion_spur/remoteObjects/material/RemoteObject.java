package orion_spur.remoteObjects.material;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.domainvalue.Position;

public class RemoteObject
{
	private Vector2		_movementVector;
	private String		_assetFile;
	private Position	_position;
	private float		_rotation;
	
	public RemoteObject(Vector2 movementVector, String assetFile, Position position, float rotation)
	{
		Contract.NotNull(movementVector);
		Contract.NotNullOrEmpty(assetFile);
		Contract.NotNull(position);
		
		_movementVector = movementVector;
		_assetFile = assetFile;
		_position = position;
		_rotation = rotation;
	}
	
	public Vector2 getMovementVector()
	{
		return _movementVector;
	}
	
	public String getAssetFile()
	{
		return _assetFile;
	}
	
	public Position getPosition()
	{
		return _position;
	}
	
	public float getRotation()
	{
		return _rotation;
	}
}
