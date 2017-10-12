package orion_spur.player.service;

import java.io.IOException;
import java.net.MalformedURLException;

import com.badlogic.gdx.math.Vector2;

import juard.event.DataEvent;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.exception.HttpException;
import orion_spur.level.material.LevelElement;

public interface IPlayerService
{
	public DataEvent<Vector2> PositionChanged = new DataEvent<Vector2>();
	
	void createPlayer() throws Exception;
	
	void setPosition(Vector2 newPosition, float rotation) throws Exception;
	
	void setPosition(LevelElement player) throws IOException, HttpException;
	
	Position getPosition() throws Exception;
	
	// TODO getCenteredPosition
}
