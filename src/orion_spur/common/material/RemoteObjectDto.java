package orion_spur.common.material;

import java.util.List;

public class RemoteObjectDto 
{
	private String AssetFile;
	private VectorDto MovementVector;

	public RemoteObjectDto(){}

	public RemoteObjectDto(String AssetFile, VectorDto MovementVector)
	{
		this.AssetFile = AssetFile;
		this.MovementVector = MovementVector;
	}

	public String getAssetFile()
	{
		return AssetFile;
	}

	public VectorDto getMovementVector()
	{
		return MovementVector;
	}
}


