package orion_spur.player.service;

import com.badlogic.gdx.math.Vector2;

import juard.event.DataEvent;
import orion_spur.common.domainvalue.Position;

public interface IPlayerService
{
	public DataEvent<Vector2> PositionChanged = new DataEvent<Vector2>();
	
	void createPlayer() throws Exception;
	
	void setPosition(Vector2 newPosition, float rotation) throws Exception;
	
	Position getPosition() throws Exception;
	
	// TODO getCenteredPosition
}
