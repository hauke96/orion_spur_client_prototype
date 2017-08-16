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

import orion_spur.common.domainvalue.Position;

public class PlayerServiceProxy implements IPlayerService
{
	private String _serviceUrlString = "http://localhost:8080/player/1";
	
	@Override
	public void setPosition(Position newPosition) throws Exception
	{
		StringBuilder params = new StringBuilder(_serviceUrlString + "?");
		
		params.append("xLightYear=");
		params.append(newPosition.getX().getLightYear());
		
		params.append("&xMeter=");
		params.append(newPosition.getX().getMeter());
		
		params.append("&yLightYear=");
		params.append(newPosition.getY().getLightYear());
		
		params.append("&yMeter=");
		params.append(newPosition.getY().getMeter());
		
		URL url = new URL(params.toString());
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("PUT");
		connection.setDoOutput(true);
		connection.getResponseCode();
		
		PositionChanged.fireEvent(newPosition);
	}
	
	@Override
	public Position getPosition() throws Exception
	{
		HttpURLConnection connection = (HttpURLConnection) new URL(_serviceUrlString).openConnection();
		
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
