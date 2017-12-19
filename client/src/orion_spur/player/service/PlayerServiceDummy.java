package orion_spur.player.service;

import java.io.IOException;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.exception.HttpException;
import orion_spur.level.material.LevelElement;
import orion_spur.remoteObjects.material.RemoteObject;

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
	public RemoteObject getPlayer()
	{
		// TODO reele Daten einfügen
		return new RemoteObject("player", new Vector2(), "", _playerPosition, 0f);
	}
	
	@Override
	public void createPlayer() throws Exception
	{
	}
	
	@Override
	public void setPosition(LevelElement player) throws IOException, HttpException
	{
		// TODO Implement method
		throw new RuntimeException("NOT IMPLEMENTED");
	}
}
