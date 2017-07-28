package orion_spur.level.view;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.factory.IActorFactory;
import orion_spur.common.service.LayerActor;
import orion_spur.level.service.ILevelService;

public class LevelActor extends LayerActor
{
	private Position		_position;
	private ILevelService	_levelService;
	private Vector2			_size;
	
	public LevelActor(ILevelService levelService, IActorFactory actorFactory, ICoordinateConverter coordinateConverter)
	{
		super(actorFactory, coordinateConverter);
		Contract.NotNull(levelService);
		
		_levelService = levelService;
		
		// TODO add real level name when implemented
		_position = _levelService.getPosition("");
		_size = _levelService.getSizeInMeters("");
		
		Contract.NotNull(_position);
	}
	
	public void loadLevelElements()
	{
		_levelService.getLevel("").forEach((levelElement) -> addToLayer(levelElement));
	}
	
	public Position getPosition()
	{
		Contract.NotNull(_position);
		return _position;
	}
	
	public Vector2 getSize()
	{
		Contract.NotNull(_size);
		return _size;
	}
}
