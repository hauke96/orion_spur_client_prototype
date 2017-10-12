package orion_spur.common.material;

import java.util.List;

public class RemoteObjectDto 
{
	private String Name;
	private String AssetFile;
	private VectorDto MovementVector;
	private CoordinateDto X;
	private CoordinateDto Y;
	private float Rotation;

	public RemoteObjectDto(){}

	public RemoteObjectDto(String Name, String AssetFile, VectorDto MovementVector, CoordinateDto X, CoordinateDto Y, float Rotation)
	{
		this.Name = Name;
		this.AssetFile = AssetFile;
		this.MovementVector = MovementVector;
		this.X = X;
		this.Y = Y;
		this.Rotation = Rotation;
	}

	public String getName()
	{
		return Name;
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

	public float getRotation()
	{
		return Rotation;
	}
}


