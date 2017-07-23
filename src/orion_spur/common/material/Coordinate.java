package orion_spur.common.material;

import juard.contract.Contract;

public class Coordinate
{
	private static final long LIGHTYEAR_IN_METERS = 9460730472580800L;
	
	private final long	_lightYear;
	private final long	_meter;
	
	public Coordinate(long lightYear, long meter)
	{
		Contract.Satisfy(meter < LIGHTYEAR_IN_METERS);
		
		_lightYear = lightYear;
		_meter = meter;
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
