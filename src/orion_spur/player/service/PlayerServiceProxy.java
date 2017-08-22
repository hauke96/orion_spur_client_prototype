package orion_spur.player.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.xml.ws.http.HTTPException;

import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.net.HttpStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hauke_stieler.goms.service.ConnectionService;
import de.hauke_stieler.goms.service.GoMessagingService;
import juard.contract.Contract;
import juard.log.Logger;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.exception.HttpException;
import orion_spur.player.view.Player;

public class PlayerServiceProxy implements IPlayerService
{
	private static final String PLAYER_NAME = ""+System.nanoTime();
	private static final String PLAYER_CREATED = "player.created";
	private String _serviceUrlString = "http://localhost:8080/player/"+PLAYER_NAME;
	private Gson _gson;
	
	public PlayerServiceProxy(GoMessagingService messagingService) {
		Contract.NotNull(messagingService);
		
		try
		{
			messagingService.register(data -> gomsOnPlayerCreated(data), PLAYER_CREATED);
		}
		catch (IOException e)
		{
			Logger.fatal("Could not register to " + PLAYER_CREATED + " event.", e);
		}
		
		_gson = new Gson();
	}
	
	private void gomsOnPlayerCreated(String data) {
		System.out.println(data);
	}

	@Override
	public void createPlayer() throws Exception
	{
		URL url = new URL(_serviceUrlString);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethods.POST);
		
		if(connection.getResponseCode() != HttpStatus.SC_OK)
		{
			Reader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			//TODO check if this works or if the number from the .read() method is wrong
			char[] cbuf = new char[reader.read()];
			reader.read(cbuf);
			throw new HttpException(connection.getResponseCode(), new String(cbuf));
		}
	}

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
		connection.setRequestMethod(HttpMethods.PUT);
		connection.setDoOutput(true);
		connection.getResponseCode();
		
		if(connection.getResponseCode() == HttpStatus.SC_OK)
		{
			PositionChanged.fireEvent(newPosition);
		}
		else
		{
			Reader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			throw new HTTPException(connection.getResponseCode());
		}
	}
	
	@Override
	public Position getPosition() throws Exception
	{
		HttpURLConnection connection = (HttpURLConnection) new URL(_serviceUrlString).openConnection();
		connection.setRequestMethod(HttpMethods.GET);
		
		if (connection.getResponseCode() == HttpStatus.SC_OK)
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
