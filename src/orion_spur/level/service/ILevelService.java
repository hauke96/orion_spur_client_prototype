package orion_spur.level.service;

import java.util.List;

import orion_spur.common.domainvalue.Position;
import orion_spur.level.material.LevelElement;

public interface ILevelService
{
	List<LevelElement> getLevel(String name);
	
	Position getPosition(String name);
}
