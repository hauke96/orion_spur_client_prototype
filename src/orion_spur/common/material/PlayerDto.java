package orion_spur.common.material;

import java.util.List;

public class PlayerDto 
{
	private String Name;
	private CoordinateDto X;
	private CoordinateDto Y;

	public PlayerDto(){}

	public PlayerDto(String Name, CoordinateDto X, CoordinateDto Y)
	{
		this.Name = Name;
		this.X = X;
		this.Y = Y;
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
}


