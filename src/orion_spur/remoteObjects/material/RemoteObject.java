package orion_spur.remoteObjects.material;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;

public class RemoteObject
{
	private Vector2 _movementVector;
	private String _assetFile;
	
	public RemoteObject(Vector2 movementVector, String assetFile)
	{
		Contract.NotNull(movementVector);
		Contract.NotNullOrEmpty(assetFile);
		
		_movementVector = movementVector;
		_assetFile = assetFile;
	}

	public Vector2 getMovementVector()
	{
		return _movementVector;
	}

	public String getAssetFile()
	{
		return _assetFile;
	}
	
	
}
