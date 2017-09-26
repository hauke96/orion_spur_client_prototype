package orion_spur.common.material;

import java.util.List;

public class PlayerDto 
{
	private String Name;
	private CoordinateDto X;
	private CoordinateDto Y;
	private float Rotation;

	public PlayerDto(){}

	public PlayerDto(String Name, CoordinateDto X, CoordinateDto Y, float Rotation)
	{
		this.Name = Name;
		this.X = X;
		this.Y = Y;
		this.Rotation = Rotation;
	}

	public String getName()
	{
		return Name;
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


