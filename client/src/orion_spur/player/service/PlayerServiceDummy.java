package orion_spur.player.service;

import java.io.IOException;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.exception.HttpException;
import orion_spur.level.domainvalue.LevelElementType;
import orion_spur.level.material.LevelElement;
import orion_spur.level.view.LevelView.LayerType;
import orion_spur.player.material.SpaceShip;

public class PlayerServiceDummy implements IPlayerService
{
	// TODO replace by a vector (world position)
	private Vector2 _playerPosition = Vector2.Zero;// Position.create(0, -23013, 600, -646735535105597500L);
	
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
		Vector2 playerPositionWorldVector = _playerPosition;
		
		_playerPosition = newPosition;
		
		PositionChanged.fireEvent(new Vector2(newPositionWorldVector.x - playerPositionWorldVector.x,
		    newPositionWorldVector.y - playerPositionWorldVector.y));
	}
	
	@Override
	public Position getPosition()
	{
		return _coordinateConverter.worldToUniverse(_playerPosition);
	}
	
	@Override
	public SpaceShip getPlayer()
	{
		// TODO reele Daten einfügen
		return new SpaceShip("player",
		    new Vector2(),
		    _playerPosition,
		    0f,
		    LayerType.LAYER_PLAYER,
		    LevelElementType.PLAYER,
		    "assets/textures/spaceship.png",
		    1000,
		    10000,
		    250);
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
