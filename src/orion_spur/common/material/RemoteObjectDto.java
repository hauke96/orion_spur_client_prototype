package orion_spur.common.material;

import java.util.List;

public class RemoteObjectDto 
{
	private String AssetFile;
	private VectorDto MovementVector;
	private CoordinateDto X;
	private CoordinateDto Y;

	public RemoteObjectDto(){}

	public RemoteObjectDto(String AssetFile, VectorDto MovementVector, CoordinateDto X, CoordinateDto Y)
	{
		this.AssetFile = AssetFile;
		this.MovementVector = MovementVector;
		this.X = X;
		this.Y = Y;
	}

	public String getAssetFile()
	{
		return AssetFile;
	}

	public VectorDto getMovementVector()
	{
		return MovementVector;
	}

	public CoordinateDto getX()
	{
		return X;
	}

	public CoordinateDto getY()
	{
		return Y;
	}
}


