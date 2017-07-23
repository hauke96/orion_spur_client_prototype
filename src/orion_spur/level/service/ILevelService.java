package orion_spur.level.service;

import java.util.List;

import orion_spur.level.material.LevelElement;

public interface ILevelService
{
	List<LevelElement> getLevel(String name);
	
	// TODO create own method to ask for positions
}
