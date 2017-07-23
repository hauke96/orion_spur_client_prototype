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
		
		result.add(new LevelElement(Position.create(0, -23142, 0, -8401128659651750400L), LayerType.LAYER_BACKGROUND, LevelType.IMAGE, "assets/textures/milkyway.jpg"));
		result.add(new LevelElement(Position.create(0, -23142, 0, -8401128659651750400L), LayerType.LAYER_1_BEHIND, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		result.add(new LevelElement(Position.create(0, -23142, 0, -8401128659651750400L), LayerType.LAYER_0_BEHIND, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		result.add(new LevelElement(Position.create(0, -23142, 0, -8401128659651750400L), LayerType.LAYER_0_BEFORE, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		result.add(new LevelElement(Position.create(0, -23142, 0, -8401128659651750400L), LayerType.LAYER_1_BEFORE, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		
		return result;
	}
}
