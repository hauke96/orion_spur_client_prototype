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
		
		result.add(new LevelElement(Position.create(0, -23013, 0, -6467355351056235L), 0, LayerType.LAYER_BACKGROUND, LevelType.IMAGE, "assets/textures/milkyway.jpg"));
		result.add(new LevelElement(Position.create(0, -23013, 590, -6467355351055975L), 20, LayerType.LAYER_1_BEHIND, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		result.add(new LevelElement(Position.create(0, -23013, 600, -6467355351055955L), 60, LayerType.LAYER_0_BEHIND, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		result.add(new LevelElement(Position.create(0, -23013, 610, -6467355351056000L), 110, LayerType.LAYER_0_BEFORE, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		result.add(new LevelElement(Position.create(0, -23013, 560, -6467355351055955L), 230, LayerType.LAYER_1_BEFORE, LevelType.IMAGE, "assets/textures/asteroid-0.png"));
		
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
