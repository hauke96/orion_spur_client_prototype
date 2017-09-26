package orion_spur.player.service;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.domainvalue.Position;

public class PlayerServiceDummy implements IPlayerService
{
	// TODO replace by a vector (world position)
	private Position _playerPosition = Position.create(0, -23013, 600, -6467355351055975L);
	
	private ICoordinateConverter _coordinateConverter;
	
	public PlayerServiceDummy(ICoordinateConverter coordinateConverter)
	{
		Contract.NotNull(coordinateConverter);
		
		_coordinateConverter = coordinateConverter;
	}
	
	@Override
	public void setPosition(Vector2 newPosition, float rotation)
	{
		Contract.NotNull(newPosition);
		
		Vector2 newPositionWorldVector = newPosition;
		Vector2 playerPositionWorldVector = _coordinateConverter.universeToWorld(_playerPosition);
		
		_playerPosition = _coordinateConverter.worldToUniverse(newPosition);
		
		PositionChanged.fireEvent(new Vector2(newPositionWorldVector.x - playerPositionWorldVector.x, newPositionWorldVector.y - playerPositionWorldVector.y));
	}
	
	@Override
	public Position getPosition()
	{
		return _playerPosition;
	}
	
	@Override
	public void createPlayer() throws Exception
	{
	}
}
