package orion_spur.remoteObjects.material;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.domainvalue.Position;

public class RemoteObject
{
	private Vector2		_movementVector;
	private String		_assetFile;
	private Position	_position;
	
	public RemoteObject(Vector2 movementVector, String assetFile, Position position)
	{
		Contract.NotNull(movementVector);
		Contract.NotNullOrEmpty(assetFile);
		Contract.NotNull(position);
		
		_movementVector = movementVector;
		_assetFile = assetFile;
		_position = position;
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
}
