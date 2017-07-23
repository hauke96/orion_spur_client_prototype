package orion_spur.player.service;

import juard.event.Event;
import orion_spur.common.material.Position;

public interface IPlayerService
{
	public Event PositionChanged = new Event();
	
	void setPosition(Position newPosition);
	
	Position getPosition();
}
