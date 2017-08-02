package orion_spur.player.service;

import juard.event.DataEvent;
import orion_spur.common.domainvalue.Position;

public interface IPlayerService
{
	public DataEvent<Position> PositionChanged = new DataEvent<Position>();
	
	void setPosition(Position newPosition);
	
	Position getPosition() throws Exception;
	
	// TODO getCenteredPosition
}
