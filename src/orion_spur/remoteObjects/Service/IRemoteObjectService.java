package orion_spur.remoteObjects.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import juard.event.DataEvent;
import orion_spur.common.exception.HttpException;
import orion_spur.remoteObjects.material.RemoteObject;

public interface IRemoteObjectService
{
	DataEvent<RemoteObject> RemoteObjectChanged = new DataEvent<RemoteObject>();
	
	List<RemoteObject> getAllObjectsForLevel(String levelName) throws MalformedURLException, IOException, HttpException;
}
