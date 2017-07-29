package orion_spur.common.domainvalue;

import com.google.gson.annotations.SerializedName;

import juard.contract.Contract;

/*
 * The coordinate system begins in the center of the milky way with (0:0,0:0). The x- and y-axis are reaching from -60.000Ly to +60.000Ly:
 * @formatter:off
 * 
 * 
 * (-60.000:0, +60.000:0)       (+60.000:0, +60.000:0)
 *          \_____________________________/
 *          |              |              |
 *          |              |              |
 *          |              |              |
 *          |              |              |
 *          |              |              |
 *          |              |              |
 *          |__________(0:0,0:0)__________|
 *          |              |              |
 *          |              |              |
 *          |              |              |
 *          |             sun             |
 *          |              |              |
 *          |              |              |
 *          |______________|______________|
 *          /                             \
 * (-60.000:0, -60.000:0)        (+60.000:0, -60.000:0)
 * 
 * 
 * @formatter:on
 * 
 * The coordinate format is (Ly:m,Ly:m) so e.g. the position of the sun may be set to (0:0,-23142:-8401128659651750400).
 */
public class Coordinate
{
	private static final long LIGHTYEAR_IN_METERS = 9460730472580800L;
	
	@SerializedName ("LightYears")
	private final long	_lightYear;
	@SerializedName ("Meters")
	private final long	_meter;
	
	private Coordinate(long lightYear, long meter)
	{
		_lightYear = lightYear;
		_meter = meter;
	}
	
	public static Coordinate create(long lightYear, long meter)
	{
		Contract.Satisfy(meter / LIGHTYEAR_IN_METERS == 0);
		
		return new Coordinate(lightYear, meter);
	}
	
	public long getLightYear()
	{
		return _lightYear;
	}
	
	public long getMeter()
	{
		return _meter;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof Coordinate))
		{
			return false;
		}
		
		Coordinate other = (Coordinate) obj;
		
		return _lightYear == other._lightYear &&
		        _meter == other._meter;
	}
	
	@Override
	public int hashCode()
	{
		return (int) (_lightYear + _meter);
	}
	
	public Coordinate copy()
	{
		return create(_lightYear, _meter);
	}
}
