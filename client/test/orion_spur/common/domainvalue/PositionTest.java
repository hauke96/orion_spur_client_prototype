package orion_spur.common.domainvalue;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PositionTest
{
	private static final long LIGHTYEAR_IN_CENTIMETERS = 946073047258080000L;
	
	private Position _position;
	
	@Before
	public void init()
	{
		_position = Position.create(1, 1, 1, 1);
	}
	
	@Test
	public void addNegativeMeter()
	{
		Position result = _position.add(Position.create(0, 0, -2, -1));
		
		assertEquals(0, result.getX().getLightYear());
		assertEquals(LIGHTYEAR_IN_CENTIMETERS - 1, result.getX().getCentimeter());
		
		assertEquals(1, result.getY().getLightYear());
		assertEquals(0, result.getY().getCentimeter());
	}
	
	@Test
	public void addLargeMeter()
	{
		Position result = _position.add(Position.create(0, 0, LIGHTYEAR_IN_CENTIMETERS, 0));
		
		assertEquals(2, result.getX().getLightYear());
		assertEquals(1, result.getX().getCentimeter());
		
		assertEquals(1, result.getY().getLightYear());
		assertEquals(1, result.getY().getCentimeter());
	}
}
