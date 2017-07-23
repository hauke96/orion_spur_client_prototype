package orion_spur.player.service;

import juard.contract.Contract;
import orion_spur.common.material.Position;

public class PlayerServiceDummy implements IPlayerService
{
	private Position _playerPosition;
	
	@Override
	public void setPosition(Position newPosition)
	{
		Contract.NotNull(newPosition);
		
		_playerPosition = newPosition;
	}
	
	@Override
	public Position getPosition()
	{
		return _playerPosition;
	}
	
}
