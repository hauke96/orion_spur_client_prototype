package orion_spur.level.view;

import juard.contract.Contract;
import orion_spur.common.service.LayerActor;
import orion_spur.level.service.ILevelService;

public class LevelActor extends LayerActor
{
	public LevelActor(ILevelService levelService)
	{
		Contract.NotNull(levelService);
	}
}
