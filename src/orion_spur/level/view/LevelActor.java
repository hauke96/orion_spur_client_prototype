package orion_spur.level.view;

import juard.contract.Contract;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.factory.IActorFactory;
import orion_spur.common.service.LayerActor;
import orion_spur.level.service.ILevelService;

public class LevelActor extends LayerActor
{
	private Position _position;
	
	public LevelActor(ILevelService levelService, IActorFactory actorFactory)
	{
		super(actorFactory);
		Contract.NotNull(levelService);
		
		// TODO add real level name when implemented
		_position = levelService.getPosition("");
		levelService.getLevel("").forEach((levelElement) -> addToLayer(levelElement));
		
		Contract.NotNull(_position);
	}
	
	public Position getPosition()
	{
		Contract.NotNull(_position);
		return _position;
	}
}
