package orion_spur.common.service;

import com.badlogic.gdx.math.Vector2;

import orion_spur.common.domainvalue.Position;

public interface ICoordinateConverter
{
	Vector2 screenToWorld(Vector2 position);
	
	Position worldToUniverse(Vector2 position);
	
	Vector2 worldToScreen(Vector2 position);
	
	Vector2 universeToWorld(Position position);
}
