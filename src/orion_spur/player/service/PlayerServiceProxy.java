package orion_spur.player.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import javax.xml.ws.http.HTTPException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import juard.log.Logger;
import orion_spur.common.domainvalue.Position;

public class PlayerServiceProxy implements IPlayerService
{
	@Override
	public void setPosition(Position newPosition)
	{
		Logger.error("not implemented");
	}
	
	@Override
	public Position getPosition() throws Exception
	{
		URL url = new URL("http://localhost:8080/player");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		if (connection.getResponseCode() == 200)
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			// read response lines into single string
			String response = reader.lines().collect(Collectors.joining());
			
			Gson gson = new GsonBuilder().create();
			Position position = gson.fromJson(response, Position.class);
			
			return position;
		}
		else
		{
			Reader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			throw new HTTPException(connection.getResponseCode());
		}
	}
}
