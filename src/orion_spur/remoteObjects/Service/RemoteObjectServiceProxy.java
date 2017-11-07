package orion_spur.remoteObjects.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.HttpStatus;
import com.google.gson.Gson;

import de.hauke_stieler.goms.service.GoMessagingService;
import juard.contract.Contract;
import juard.log.Logger;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.exception.HttpException;
import orion_spur.common.material.RemoteObjectDto;
import orion_spur.common.material.RemoteObjectListDto;
import orion_spur.remoteObjects.material.RemoteObject;

public class RemoteObjectServiceProxy implements IRemoteObjectService
{
	private static final String	OBJECT_MOVED		= "object.moved";
	private String				_serviceUrlString	= "http://localhost:8080/objects";
	private Gson				_gson;
	
	public RemoteObjectServiceProxy(GoMessagingService messagingService)
	{
		Contract.NotNull(messagingService);
		
		try
		{
			messagingService.register(data -> gomsOnObjectMoved(data), OBJECT_MOVED);
		}
		catch (IOException e)
		{
			Logger.fatal("Could not register to " + OBJECT_MOVED + " event.", e);
		}
		
		_gson = new Gson();
	}
	
	private void gomsOnObjectMoved(String data)
	{
		// TODO react to moved objects and fire event
	}
	
	@Override
	public List<RemoteObject> getAllObjectsForLevel(String levelName) throws MalformedURLException, IOException, HttpException
	{
		HttpURLConnection connection = (HttpURLConnection) new URL(_serviceUrlString).openConnection();
		connection.setRequestMethod(HttpMethods.GET);
		
		if (connection.getResponseCode() != HttpStatus.SC_OK)
		{
			throwHttpException(connection);
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		// read response lines into single string
		String response = reader.lines().collect(Collectors.joining());
		
		RemoteObjectListDto remoteObjects = _gson.fromJson(response, RemoteObjectListDto.class);
		
		if (remoteObjects == null)
		{
			return new ArrayList<>();
		}
		
		return convertToList(remoteObjects);
	}
	
	private List<RemoteObject> convertToList(RemoteObjectListDto remoteObjects)
	{
		Contract.NotNull(remoteObjects);
		
		List<RemoteObject> result = new ArrayList<>();
		
		for (RemoteObjectDto dto : remoteObjects.getRemoteObjectList())
		{
			Vector2 movementVector = new Vector2(dto.getMovementVector().getX(), dto.getMovementVector().getY());
			
			String assetFile = dto.getAssetFile();
			
			Position position = Position.create(dto.getX().getLightYears(), dto.getY().getLightYears(), dto.getX().getMeters(), dto.getY().getMeters());
			
			float rotation = dto.getRotation();
			
			result.add(new RemoteObject(dto.getName(), movementVector, assetFile, position, rotation));
		}
		
		return result;
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
