package orion_spur.player.service;

import java.io.IOException;

import com.badlogic.gdx.math.Vector2;

import juard.event.DataEvent;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.exception.HttpException;
import orion_spur.level.material.LevelElement;
import orion_spur.player.material.SpaceShip;

public interface IPlayerService
{
	// TODO separate events for own player creation and joined other players (#16)
	public DataEvent<LevelElement>	PlayerCreated	= new DataEvent<LevelElement>();
	public DataEvent<Vector2>		PositionChanged	= new DataEvent<Vector2>();
	
	void createPlayer() throws Exception;
	
	void setPosition(LevelElement player, Vector2 oldPosition) throws IOException, HttpException;
	
	Position getPosition() throws Exception;
	
	SpaceShip getPlayer();
}
