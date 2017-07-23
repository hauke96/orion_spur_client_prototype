package orion_spur.common.material;

import java.text.MessageFormat;

public class Position
{
	private static final long LIGHTYEAR_IN_METERS = 9460730472580800L;
	
	private final long	_xLightYear;
	private final long	_yLightYear;
	private final long	_xMeter;
	private final long	_yMeter;
	
	public Position(long xLightYear, long yLightYear, long xMeter, long yMeter)
	{
		if (xMeter >= LIGHTYEAR_IN_METERS)
		{
			xLightYear += xMeter % LIGHTYEAR_IN_METERS;
			xMeter /= LIGHTYEAR_IN_METERS;
		}
		
		if (yMeter >= LIGHTYEAR_IN_METERS)
		{
			yLightYear += yMeter % LIGHTYEAR_IN_METERS;
			yMeter /= LIGHTYEAR_IN_METERS;
		}
		
		_xLightYear = xLightYear;
		_yLightYear = yLightYear;
		_xMeter = xMeter;
		_yMeter = yMeter;
	}
	
	public Coordinate getX()
	{
		return new Coordinate(_xLightYear, _xMeter);
	}
	
	public Coordinate getY()
	{
		return new Coordinate(_yLightYear, _yMeter);
	}
	
	@Override
	public String toString()
	{
		return MessageFormat.format("({0}:{1}, {2}:{3})", _xLightYear, _xMeter, _yLightYear, _yMeter);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof Position))
		{
			return false;
		}
		
		Position other = (Position) obj;
		
		return _xLightYear == other._xLightYear &&
		        _yLightYear == other._yLightYear &&
		        _xMeter == other._xMeter &&
		        _yMeter == other._yMeter;
	}
	
	@Override
	public int hashCode()
	{
		return (int) (_xLightYear + _yLightYear + _xMeter + _yMeter);
	}
}
