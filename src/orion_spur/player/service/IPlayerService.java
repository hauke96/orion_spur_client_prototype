package orion_spur.player.service;

import orion_spur.common.material.Position;

public interface IPlayerService
{
	void setPosition(Position newPosition);
	
	Position getPosition();
}
