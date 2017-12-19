package orion_spur.level.material;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.service.LayerActor.LayerType;
import orion_spur.level.domainvalue.LevelType;

public class LevelElement
{
	private String		_id;
	private Vector2		_position;
	private float		_rotation;
	private LayerType	_layer;
	private LevelType	_type;
	private String		_assetPath;
	private Vector2		_movementVector;
	
	public LevelElement(String id, Vector2 position, Vector2 movementVector, float rotation, LayerType layer, LevelType type, String assetPath)
	{
		Contract.NotNull(id);
		Contract.NotNull(position);
		Contract.NotNull(movementVector);
		Contract.NotNull(assetPath);
		
		_id = id;
		_position = position;
		_movementVector = movementVector;
		_rotation = rotation;
		_layer = layer;
		_type = type;
		_assetPath = assetPath;
	}
	
	public String getId()
	{
		return _id;
	}
	
	public Vector2 getPosition()
	{
		return _position;
	}
	
	public LayerType getLayer()
	{
		return _layer;
	}
	
	public LevelType getType()
	{
		return _type;
	}
	
	public String getAssetPath()
	{
		return _assetPath;
	}
	
	public float getRotation()
	{
		return _rotation;
	}
	
	public Vector2 getMovementVector()
	{
		return new Vector2(_movementVector.x, _movementVector.y);
	}
	
	public void setRotation(float newRotation)
	{
		_rotation = newRotation;
	}
	
	public void setPosition(Vector2 newPosition)
	{
		Contract.NotNull(newPosition);
		
		_position = newPosition;
	}
	
	public void setMovementVector(Vector2 newMovementVector)
	{
		Contract.NotNull(newMovementVector);
		
		_movementVector = newMovementVector;
	}
	
	// TODO equals, hashcode
}
