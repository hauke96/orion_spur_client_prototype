package orion_spur.common.domainvalue;

import java.text.MessageFormat;

public class Position
{
	private static final long LIGHTYEAR_IN_METERS = 9460730472580800L;
	
	private final long	_xLightYear;
	private final long	_yLightYear;
	private final long	_xMeter;
	private final long	_yMeter;
	
	private Position(Coordinate x, Coordinate y)
	{
		_xLightYear = x.getLightYear();
		_yLightYear = y.getLightYear();
		_xMeter = x.getMeter();
		_yMeter = y.getMeter();
	}
	
	public static Position create(long xLightYear, long yLightYear, long xMeter, long yMeter)
	{
		Coordinate x = getCoordinate(xLightYear, xMeter);
		Coordinate y = getCoordinate(yLightYear, yMeter);
		
		return new Position(x, y);
	}
	
	/**
	 * Creates a coordinate object after converting negative or too large meter values into light years.
	 * This means, that the coordinate returning may not have exactly the same values as given here.
	 * 
	 * @param lightYear
	 *            Amount of light years.
	 * @param meter
	 *            Amount of meters.
	 * @return The coordinate.
	 */
	private static Coordinate getCoordinate(long lightYear, long meter)
	{
		if (meter < 0 || LIGHTYEAR_IN_METERS <= meter)
		{
			if (meter < 0)
			{
				long amountLightYear = meter / LIGHTYEAR_IN_METERS + 1;
				lightYear -= amountLightYear;
				meter = LIGHTYEAR_IN_METERS + (meter % LIGHTYEAR_IN_METERS); // just modulo wont work, for java is: -1 % 5 == -1
			}
			else
			{
				lightYear += meter / LIGHTYEAR_IN_METERS;
				meter %= LIGHTYEAR_IN_METERS;
			}
		}
		
		return Coordinate.create(lightYear, meter);
	}
	
	public Coordinate getX()
	{
		return Coordinate.create(_xLightYear, _xMeter);
	}
	
	public Coordinate getY()
	{
		return Coordinate.create(_yLightYear, _yMeter);
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
	
	/**
	 * Adds the given offset (given in meters) and creates a new position object.
	 * 
	 * @param offset
	 *            The offset in meters.
	 * @return The new position.
	 */
	public Position add(long xOffset, long yOffset)
	{
		return create(_xLightYear, _yLightYear, _xMeter + xOffset, _yMeter + yOffset);
	}
}
