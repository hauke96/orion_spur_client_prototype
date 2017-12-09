package orion_spur.common.material;

import java.util.List;

public class RemoteObjectListDto 
{
	private List<RemoteObjectDto> RemoteObjectList;

	public RemoteObjectListDto(){}

	public RemoteObjectListDto(List<RemoteObjectDto> RemoteObjectList)
	{
		this.RemoteObjectList = RemoteObjectList;
	}

	public List<RemoteObjectDto> getRemoteObjectList()
	{
		return RemoteObjectList;
	}
}


