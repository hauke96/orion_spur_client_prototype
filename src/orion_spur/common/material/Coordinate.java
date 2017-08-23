package orion_spur.common.material;

import java.util.List;

public class Coordinate 
{
	private long LightYears;
	private long Meters;

	public Coordinate(){}

	public Coordinate(long LightYears, long Meters)
	{
		this.LightYears = LightYears;
		this.Meters = Meters;
	}

	public long getLightYears()
	{
		return LightYears;
	}

	public long getMeters()
	{
		return Meters;
	}
}


