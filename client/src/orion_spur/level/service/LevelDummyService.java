package orion_spur.level.service;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

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
		
		// center: -6467355351055975L
		
		result.add(new LevelElement("1", new Vector2(0, 0), new Vector2(), 0, LayerType.LAYER_BACKGROUND, LevelType.IMAGE, "assets/textures/milkyway.jpg"));
		result.add(new LevelElement("2", new Vector2(590, 260), new Vector2(), 20, LayerType.LAYER_1_BEHIND, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		result.add(new LevelElement("3", new Vector2(600, 240), new Vector2(), 60, LayerType.LAYER_0_BEHIND, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		result.add(new LevelElement("4", new Vector2(610, 285), new Vector2(), 110, LayerType.LAYER_0_BEFORE, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		result.add(new LevelElement("5", new Vector2(560, 235), new Vector2(), 230, LayerType.LAYER_1_BEFORE, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		
		return result;
	}
	
	@Override
	public Position getPosition(String name)
	{
		return Position.create(0, -23014, 0, 2993375121524565L);
	}
	
	@Override
	public Vector2 getSizeInMeters(String name)
	{
		return new Vector2(1200, 520);
	}
	
	@Override
	public Position getCenterPosition(String name)
	{
		Position position = getPosition(name);
		Vector2 size = getSizeInMeters(name);
		
		return position.add(Position.create(0, 0, (int) size.x / 2, (int) size.y / 2));
	}
}
