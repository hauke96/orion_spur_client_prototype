package orion_spur.common.domainvalue;

import java.text.MessageFormat;

import com.google.gson.annotations.SerializedName;

import juard.contract.Contract;

public class Position
{
	private static final long LIGHTYEAR_IN_CENTIMETERS = 946073047258080000L;
	
	@SerializedName ("X")
	private final Coordinate	_xCoordinate;
	@SerializedName ("Y")
	private final Coordinate	_yCoordinate;
	
	private Position(Coordinate x, Coordinate y)
	{
		Contract.NotNull(x);
		Contract.NotNull(y);
		
		_xCoordinate = x;
		_yCoordinate = y;
	}
	
	public static Position create(long xLightYear, long yLightYear, long xCentimeter, long yCentimeter)
	{
		Coordinate x = getCoordinate(xLightYear, xCentimeter);
		Coordinate y = getCoordinate(yLightYear, yCentimeter);
		
		return create(x, y);
	}
	
	public static Position create(Coordinate x, Coordinate y)
	{
		return new Position(x, y);
	}
	
	/**
	 * Creates a coordinate object after converting negative or too large centimeter
	 * values into light years. This means, that the coordinate returning may not
	 * have exactly the same values as given here.
	 * 
	 * @param lightYear
	 *            Amount of light years.
	 * @param centimeter
	 *            Amount of centimeters.
	 * @return The coordinate.
	 */
	private static Coordinate getCoordinate(long lightYear, long centimeter)
	{
		long lightYearsInCentimeterPart = centimeter / LIGHTYEAR_IN_CENTIMETERS;
		long actualCentimeterPart = centimeter % LIGHTYEAR_IN_CENTIMETERS;
		
		// only (-1,-1) or (1,1) but not (1,-1) is allowed: Positive Ly -> positive
		// meter. Negative Ly -> negative meter
		if (lightYear > 0 && centimeter < 0)
		{
			lightYear -= lightYearsInCentimeterPart;
			
			if (lightYear >= 0)
			{
				if (actualCentimeterPart != 0)
				{
					// remove the left over negative part. This means subtracting one of the
					// lightYear and filling the rest with centimeters.
					lightYear--;
					centimeter = LIGHTYEAR_IN_CENTIMETERS - Math.abs(actualCentimeterPart);
				}
			}
			else
			{
				centimeter = actualCentimeterPart;
			}
		}
		else if (lightYear < 0 && centimeter > 0)
		{
			lightYear += lightYearsInCentimeterPart;
			
			if (lightYear <= 0)
			{
				if (actualCentimeterPart != 0)
				{
					// remove the left over negative part. This means subtracting one of the
					// lightYear and filling the rest with centimeters.
					lightYear++;
					centimeter = -LIGHTYEAR_IN_CENTIMETERS + Math.abs(actualCentimeterPart);
				}
			}
			else
			{
				centimeter = actualCentimeterPart;
			}
		}
		else // both have the same sign, just convert meter into light years.
		{
			lightYear += lightYearsInCentimeterPart;
			centimeter = actualCentimeterPart;
		}
		
		return Coordinate.create(lightYear, centimeter);
	}
	
	public Coordinate getX()
	{
		return _xCoordinate.copy();
	}
	
	public Coordinate getY()
	{
		return _yCoordinate.copy();
	}
	
	@Override
	public String toString()
	{
		return MessageFormat.format("({0}:{1}, {2}:{3})", getX().getLightYear(), getX().getCentimeter(), getY().getLightYear(), getY().getCentimeter());
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof Position))
		{
			return false;
		}
		
		Position other = (Position) obj;
		
		return getX().equals(other.getX()) && getY().equals(other.getY());
	}
	
	@Override
	public int hashCode()
	{
		return getX().hashCode() + getY().hashCode();
	}
	
	/**
	 * Adds the given position and creates a new position object. This is like
	 * vector addition.
	 * 
	 * @param position
	 *            The position to add.
	 * @return The new position.
	 */
	public Position add(Position position)
	{
		long newLightYearX = getX().getLightYear() + position.getX().getLightYear();
		long newLightYearY = getY().getLightYear() + position.getY().getLightYear();
		
		long newCentimetersX = getX().getCentimeter() + position.getX().getCentimeter();
		long newCentimetersY = getY().getCentimeter() + position.getY().getCentimeter();
		
		return create(newLightYearX, newLightYearY, newCentimetersX, newCentimetersY);
	}
	
	/**
	 * Subtracts the given position and creates a new position object. This is like
	 * vector subtraction.
	 * 
	 * @param position
	 *            The position to subtract.
	 * @return The new position.
	 */
	public Position subtract(Position position)
	{
		long newLightYearX = getX().getLightYear() - position.getX().getLightYear();
		long newLightYearY = getY().getLightYear() - position.getY().getLightYear();
		
		long newCentimetersX = getX().getCentimeter() - position.getX().getCentimeter();
		long newCentimetersY = getY().getCentimeter() - position.getY().getCentimeter();
		
		return create(newLightYearX, newLightYearY, newCentimetersX, newCentimetersY);
	}
}
