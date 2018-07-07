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

import de.hauke_stieler.goms.service.GoMessagingService;
import juard.contract.Contract;
import juard.log.Logger;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.domainvalue.Coordinate;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.exception.HttpException;
import orion_spur.common.generated.CoordinateDto;
import orion_spur.common.generated.RemoteObjectDto;
import orion_spur.common.generated.SpaceShipDto;
import orion_spur.common.generated.VectorDto;
import orion_spur.level.domainvalue.LevelElementType;
import orion_spur.level.material.LevelElement;
import orion_spur.level.view.LevelView.LayerZIndex;
import orion_spur.player.material.SpaceShip;

public class PlayerServiceProxy implements IPlayerService
{
	private static final String	PLAYER_NAME		= "" + System.nanoTime();
	private static final String	PLAYER_CREATED	= "player.created";
	private static final String	PLAYER_MOVED	= "player.moved";
	
	private String					_serviceUrlString	= "http://localhost:8080/player/" + PLAYER_NAME;
	private Gson					_gson;
	private ICoordinateConverter	_coordinateConverter;
	private SpaceShip				_player;
	
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
		SpaceShipDto playerDto = _gson.fromJson(data, SpaceShipDto.class);
		
		LevelElement player = convertToPlayer(playerDto);
		
		if (!player.getId().equals(PLAYER_NAME))
		{
			// TODO when a separate "OtherPlayerJoined" event exists, this should be the data of this event (#16)
			player = new LevelElement(player.getId(),
			    player.getPosition(),
			    player.getMovementVector(),
			    player.getRotation(),
			    LayerZIndex.LAYER_REMOTE_OBJECTS,
			    LevelElementType.REMOTE_OBJECT,
			    player.getAssetPath());
			
			System.out.println(data);
		}
		// Getting message of own player
		else
		{
			_player = (SpaceShip) player;
		}
		
		PlayerCreated.fireEvent(player);
	}
	
	// TODO Use converter for this
	@Deprecated
	private SpaceShip convertToPlayer(SpaceShipDto player)
	{
		RemoteObjectDto base = player.getBase();
		
		VectorDto vectorDto = base.getMovementVector();
		Vector2 movementVector = new Vector2(vectorDto.getX(), vectorDto.getY());
		
		Position position =
		        Position.create(base.getX().getLightYears(),
		            base.getY().getLightYears(),
		            base.getX().getMeters(),
		            base.getY().getMeters());
		
		return new SpaceShip(base.getName(),
		    _coordinateConverter.universeToWorld(position),
		    movementVector,
		    player.getBase().getRotation(),
		    LayerZIndex.LAYER_PLAYER,
		    LevelElementType.PLAYER,
		    base.getAssetFile(),
		    player.getAcceleration(),
		    player.getMaxSpeed(),
		    player.getRotationSpeed());
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
			// TODO what to do here? Fireing events is to slow due to relatively low network
			// speed.
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
	public void setPosition(LevelElement player, Vector2 oldPosition) throws IOException, HttpException
	{
		Contract.NotNull(player);
		
		StringBuilder params = new StringBuilder(_serviceUrlString);
		
		URL url = new URL(params.toString());
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethods.PUT);
		connection.setDoOutput(true);
		
		RemoteObjectDto dto =
		        new RemoteObjectDto(PLAYER_NAME,
		            player.getAssetPath(),
		            toVectorDto(player.getMovementVector()),
		            toCoordinateDto(_coordinateConverter.worldToUniverse(player.getPosition()).getX()),
		            toCoordinateDto(_coordinateConverter.worldToUniverse(player.getPosition()).getY()),
		            player.getRotation());
		
		String data = _gson.toJson(dto);
		
		connection.getOutputStream().write(data.getBytes());
		
		if (connection.getResponseCode() == HttpStatus.SC_OK)
		{
			// TODO add real level name when implemented
			Vector2 offset = new Vector2(player.getPosition().x - oldPosition.x,
			    player.getPosition().y - oldPosition.y);
			
			PositionChanged.fireEvent(offset);
		}
		else
		{
			throwHttpException(connection);
		}
	}
	
	// TODO create dto-converter for this
	private CoordinateDto toCoordinateDto(Coordinate coordinate)
	{
		return new CoordinateDto(coordinate.getLightYear(), coordinate.getCentimeter());
	}
	
	// TODO create dto-converter for this
	private VectorDto toVectorDto(Vector2 vector)
	{
		return new VectorDto(vector.x, vector.y);
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
			
			SpaceShipDto playerDto = _gson.fromJson(response, SpaceShipDto.class);
			
			Position position = _coordinateConverter.worldToUniverse(convertToPlayer(playerDto).getPosition());
			
			Contract.NotNull(position);
			return position;
		}
		else
		{
			throwHttpException(connection);
		}
		
		// Java doen't seem to be able to detect, that throwHttpException always throws
		// an exception.
		return null;
	}
	
	@Override
	public SpaceShip getPlayer()
	{
		Contract.NotNull(_player);
		return _player;
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
