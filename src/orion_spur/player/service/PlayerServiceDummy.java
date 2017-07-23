package orion_spur.player.service;

import juard.contract.Contract;
import juard.event.Event;
import orion_spur.common.material.Position;

public class PlayerServiceDummy implements IPlayerService
{
	public Event PositionChanged = new Event();
	
	private Position _playerPosition;
	
	@Override
	public void setPosition(Position newPosition)
	{
		Contract.NotNull(newPosition);
		
		_playerPosition = newPosition;
		
		PositionChanged.fireEvent();
	}
	
	@Override
	public Position getPosition()
	{
		return _playerPosition;
	}
	
}
