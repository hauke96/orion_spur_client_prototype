package orion_spur.level.material;

import juard.contract.Contract;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.service.LayerActor.LayerType;
import orion_spur.level.domainvalue.LevelType;

public class LevelElement
{
	private Position	_position;
	private float		_rotation;
	private LayerType	_layer;
	private LevelType	_type;
	private String		_assetPath;
	
	public LevelElement(Position position, float rotation, LayerType layer, LevelType type, String assetPath)
	{
		Contract.NotNull(position);
		Contract.NotNull(assetPath);
		
		_position = position;
		_rotation = rotation;
		_layer = layer;
		_type = type;
		_assetPath = assetPath;
	}
	
	public Position getPosition()
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
	
	// TODO equals, hashcode
}
