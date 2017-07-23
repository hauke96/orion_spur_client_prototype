package orion_spur.common.domainvalue;

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
	
	private final long	_lightYear;
	private final long	_meter;
	
	private Coordinate(long lightYear, long meter)
	{
		_lightYear = lightYear;
		_meter = meter;
	}
	
	public static Coordinate create(long lightYear, long meter)
	{
		Contract.Satisfy(meter < LIGHTYEAR_IN_METERS);
		
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
}
