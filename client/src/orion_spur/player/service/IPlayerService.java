package orion_spur.player.service;

import java.io.IOException;

import com.badlogic.gdx.math.Vector2;

import juard.event.DataEvent;
import juard.event.Event;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.exception.HttpException;
import orion_spur.level.material.LevelElement;
import orion_spur.remoteObjects.material.RemoteObject;

public interface IPlayerService
{
	public Event				PlayerCreated	= new Event();
	public DataEvent<Vector2>	PositionChanged	= new DataEvent<Vector2>();
	
	void createPlayer() throws Exception;
	
	void setPosition(Vector2 newPosition, float rotation) throws Exception;
	
	void setPosition(LevelElement player) throws IOException, HttpException;
	
	Position getPosition() throws Exception;
	
	RemoteObject getPlayer();
	
	// TODO getCenteredPosition
}
