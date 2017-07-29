package orion_spur.player.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.ws.http.HTTPException;

import juard.log.Logger;
import orion_spur.common.domainvalue.Position;

public class PlayerServiceProxy implements IPlayerService
{
	@Override
	public void setPosition(Position newPosition)
	{
		Logger.__error("not implemented");
	}
	
	@Override
	public Position getPosition() throws Exception
	{
		URL url = new URL("http://localhost:8080/player");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		if (connection.getResponseCode() == 200)
		{
			Reader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			return Position.create(0, 0, 0, 0);
		}
		else
		{
			Reader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			throw new HTTPException(connection.getResponseCode());
		}
	}
}
