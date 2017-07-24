package orion_spur.level.service;

import java.util.ArrayList;
import java.util.List;

import orion_spur.common.domainvalue.Position;
import orion_spur.common.service.LayerActor.LayerType;
import orion_spur.level.domainvalue.LevelType;
import orion_spur.level.material.LevelElement;

public class LevelDummyService implements ILevelService
{
	@Override
	public List<LevelElement> getLevel(String name)
	{
		List<LevelElement> result = new ArrayList<LevelElement>();
		
		result.add(new LevelElement(Position.create(0, -23014, 0, 2993375121524565L), LayerType.LAYER_BACKGROUND, LevelType.IMAGE, "assets/textures/milkyway.jpg"));
		result.add(new LevelElement(Position.create(0, -23014, 0, 2993375121524575L), LayerType.LAYER_1_BEHIND, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		result.add(new LevelElement(Position.create(0, -23014, 60, 2993375121524585L), LayerType.LAYER_0_BEHIND, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		result.add(new LevelElement(Position.create(0, -23014, 60, 2993375121524635L), LayerType.LAYER_0_BEFORE, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		result.add(new LevelElement(Position.create(0, -23014, 50, 2993375121524605L), LayerType.LAYER_1_BEFORE, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		
		return result;
	}
	
	@Override
	public Position getPosition(String name)
	{
		return Position.create(0, -23014, 0, 2993375121524565L);
	}
}
