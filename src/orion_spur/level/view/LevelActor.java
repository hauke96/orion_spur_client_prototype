package orion_spur.level.view;

import juard.contract.Contract;
import orion_spur.common.factory.IActorFactory;
import orion_spur.common.service.LayerActor;
import orion_spur.level.service.ILevelService;

public class LevelActor extends LayerActor
{
	public LevelActor(ILevelService levelService, IActorFactory actorFactory)
	{
		Contract.NotNull(levelService);
		
		// TODO add real level name when implemented
		levelService.getLevel("").forEach((levelElement) -> addToLayer(actorFactory.convert(levelElement), levelElement.getLayer()));
	}
}
