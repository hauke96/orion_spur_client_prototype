package orion_spur.player.service;

import juard.event.EventArgs;
import orion_spur.common.domainvalue.Position;

public interface IPlayerService
{
	public EventArgs PositionChanged = new EventArgs();
	
	void setPosition(Position newPosition);
	
	Position getPosition() throws Exception;
	
	// TODO getCenteredPosition
}
