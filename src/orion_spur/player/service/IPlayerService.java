package orion_spur.player.service;

import juard.event.DataEvent;
import orion_spur.common.domainvalue.Position;

public interface IPlayerService
{
	public DataEvent<Position> PositionChanged = new DataEvent<Position>();
	
	void createPlayer() throws Exception;
	
	void setPosition(Position newPosition) throws Exception;
	
	Position getPosition() throws Exception;
	
	// TODO getCenteredPosition
}
