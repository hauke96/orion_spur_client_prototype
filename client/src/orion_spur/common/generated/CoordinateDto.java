package orion_spur.common.generated;

public class CoordinateDto
{
	private long	LightYears;
	private long	Meters;
	
	public CoordinateDto()
	{
	}
	
	public CoordinateDto(long LightYears, long Meters)
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
