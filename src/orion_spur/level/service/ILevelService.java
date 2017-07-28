package orion_spur.level.service;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import orion_spur.common.domainvalue.Position;
import orion_spur.level.material.LevelElement;

public interface ILevelService
{
	List<LevelElement> getLevel(String name);
	
	Position getPosition(String name);
	
	Position getCenterPosition(String name);
	
	Vector2 getSizeInMeters(String name);
}
