package orion_spur.player.service;

import juard.contract.Contract;
import orion_spur.common.domainvalue.Position;

public class PlayerServiceDummy implements IPlayerService
{
	private Position _playerPosition = Position.create(0, -23013, 600, -6467355351055975L);
	
	@Override
	public void setPosition(Position newPosition)
	{
		Contract.NotNull(newPosition);
		
		_playerPosition = newPosition;
		
		PositionChanged.fireEvent(_playerPosition);
	}
	
	@Override
	public Position getPosition()
	{
		return _playerPosition;
	}

	@Override
	public void createPlayer() throws Exception {
	}
}
