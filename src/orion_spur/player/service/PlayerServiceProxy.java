package orion_spur.player.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.HttpStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hauke_stieler.goms.service.GoMessagingService;
import juard.contract.Contract;
import juard.log.Logger;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.domainvalue.Coordinate;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.exception.HttpException;
import orion_spur.common.material.CoordinateDto;
import orion_spur.common.material.RemoteObjectDto;
import orion_spur.common.material.VectorDto;
import orion_spur.level.material.LevelElement;

public class PlayerServiceProxy implements IPlayerService
{
	private static final String		PLAYER_NAME			= "" + System.nanoTime();
	private static final String		PLAYER_CREATED		= "player.created";
	private static final String		PLAYER_MOVED		= "player.moved";
	private String					_serviceUrlString	= "http://localhost:8080/player/" + PLAYER_NAME;
	private Gson					_gson;
	private Vector2					_oldPosition;
	private ICoordinateConverter	_coordinateConverter;
	
	public PlayerServiceProxy(GoMessagingService messagingService, ICoordinateConverter coordinateConverter)
	{
		Contract.NotNull(messagingService);
		Contract.NotNull(coordinateConverter);
		
		_coordinateConverter = coordinateConverter;
		
		try
		{
			messagingService.register(data -> gomsOnPlayerCreated(data), PLAYER_CREATED);
			messagingService.register(data -> gomsOnPlayerMoved(data), PLAYER_MOVED);
		}
		catch (IOException e)
		{
			Logger.fatal("Could not register to " + PLAYER_CREATED + " event.", e);
		}
		
		_gson = new Gson();
	}
	
	private void gomsOnPlayerCreated(String data)
	{
		RemoteObjectDto player = _gson.fromJson(data, RemoteObjectDto.class);
		
		if (!player.getName().equals(PLAYER_NAME))
		{
			System.out.println(data);
		}
		// Getting message of own player
		else
		{
			_oldPosition = _coordinateConverter.universeToWorld(Position.create(player.getX().getLightYears(), player.getY().getLightYears(), player.getX().getMeters(), player.getY().getMeters()));
		}
	}
	
	private void gomsOnPlayerMoved(String data)
	{
		RemoteObjectDto player = _gson.fromJson(data, RemoteObjectDto.class);
		
		if (!player.getName().equals(PLAYER_NAME))
		{
			System.out.println(data);
		}
		// Getting message of own player
		else
		{
			// Vector2 newPosition = _coordinateConverter.universeToWorld(Position.create(player.getX().getLightYears(), player.getY().getLightYears(), player.getX().getMeters(), player.getY().getMeters()));
			// try
			// {
			// setPosition(newPosition);
			// }
			// catch (Exception e)
			// {
			// Logger.error("Could not set new player position", e);
			// }
		}
	}
	
	@Override
	public void createPlayer() throws Exception
	{
		URL url = new URL(_serviceUrlString);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethods.POST);
		
		if (connection.getResponseCode() != HttpStatus.SC_OK)
		{
			throwHttpException(connection);
		}
	}
	
	@Override
	public void setPosition(LevelElement player) throws IOException, HttpException
	{
		Contract.NotNull(player);
		
		StringBuilder params = new StringBuilder(_serviceUrlString);
		
		URL url = new URL(params.toString());
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethods.PUT);
		connection.setDoOutput(true);
		
		RemoteObjectDto dto = new RemoteObjectDto(PLAYER_NAME, player.getAssetPath(), toVectorDto(player.getMovementVector()), toCoordinateDto(_coordinateConverter.worldToUniverse(player.getPosition()).getX()), toCoordinateDto(_coordinateConverter.worldToUniverse(player.getPosition()).getY()), player.getRotation());
		
		String data = _gson.toJson(dto);
		
		connection.getOutputStream().write(data.getBytes());
		
		if (connection.getResponseCode() == HttpStatus.SC_OK)
		{
			// TODO add real level name when implemented
			Vector2 offset = new Vector2(player.getPosition().x - _oldPosition.x, player.getPosition().y - _oldPosition.y);
			
			_oldPosition = player.getPosition();
			
			PositionChanged.fireEvent(offset);
		}
		else
		{
			throwHttpException(connection);
		}
	}
	
	private CoordinateDto toCoordinateDto(Coordinate coordinate)
	{
		return new CoordinateDto(coordinate.getLightYear(), coordinate.getMeter());
	}
	
	private VectorDto toVectorDto(Vector2 vector)
	{
		return new VectorDto(vector.x, vector.y);
	}
	
	@Override
	@Deprecated
	public void setPosition(Vector2 newPosition, float rotation) throws Exception
	{
		StringBuilder params = new StringBuilder(_serviceUrlString + "?");
		
		Position newUniversePosition = _coordinateConverter.worldToUniverse(newPosition);
		
		params.append("xLightYear=");
		params.append(newUniversePosition.getX().getLightYear());
		
		params.append("&xMeter=");
		params.append(newUniversePosition.getX().getMeter());
		
		params.append("&yLightYear=");
		params.append(newUniversePosition.getY().getLightYear());
		
		params.append("&yMeter=");
		params.append(newUniversePosition.getY().getMeter());
		
		params.append("&rotation=");
		params.append(rotation);
		
		URL url = new URL(params.toString());
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethods.PUT);
		connection.setDoOutput(true);
		connection.getResponseCode();
		
		if (connection.getResponseCode() == HttpStatus.SC_OK)
		{
			// TODO add real level name when implemented
			Vector2 offset = new Vector2(newPosition.x - _oldPosition.x, newPosition.y - _oldPosition.y);
			
			_oldPosition = newPosition;
			
			PositionChanged.fireEvent(offset);
		}
		else
		{
			throwHttpException(connection);
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
			throwHttpException(connection);
		}
		
		// Java doen't seem to be able to detect, that throwHttpException always throws an exception.
		return null;
	}
	
	private void throwHttpException(HttpURLConnection connection) throws IOException, HttpException
	{
		Reader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
		// TODO first charavter is thrown away
		char[] cbuf = new char[reader.read()];
		reader.read(cbuf);
		throw new HttpException(connection.getResponseCode(), new String(cbuf));
	}
}
