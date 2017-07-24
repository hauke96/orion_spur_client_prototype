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
				long amountLightYear = -meter / LIGHTYEAR_IN_METERS + 1;
				lightYear -= amountLightYear;
			}
			else
			{
				lightYear += meter / LIGHTYEAR_IN_METERS;
			}
			meter %= LIGHTYEAR_IN_METERS;
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
	 * Adds the given position and creates a new position object. This is like vector addition.
	 * 
	 * @param position
	 *            The position to add.
	 * @return The new position.
	 */
	public Position add(Position position)
	{
		return create(_xLightYear + position.getX().getLightYear(), _yLightYear + position.getY().getLightYear(), _xMeter + position.getX().getMeter(), _yMeter
		        + position.getY().getMeter());
	}
	
	/**
	 * Subtracts the given position and creates a new position object. This is like vector subtraction.
	 * 
	 * @param position
	 *            The position to subtract.
	 * @return The new position.
	 */
	public Position subtract(Position position)
	{
		return create(_xLightYear - position.getX().getLightYear(), _yLightYear - position.getY().getLightYear(), _xMeter - position.getX().getMeter(), _yMeter
		        - position.getY().getMeter());
	}
}
