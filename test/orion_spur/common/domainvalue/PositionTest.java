package orion_spur.common.domainvalue;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public class PositionTest
{
	private static final long LIGHTYEAR_IN_METERS = 9460730472580800L;
	
	private Position _position;
	
	@Before
	public void init()
	{
		_position = Position.create(1, 1, 1, 1);
	}
	
	@Test
	public void addNegativeNumber()
	{
		Position result = _position.add(new Vector2(-2, -1));
		
		assertEquals(0, result.getX().getLightYear());
		assertEquals(LIGHTYEAR_IN_METERS - 1, result.getX().getMeter());
		
		assertEquals(1, result.getY().getLightYear());
		assertEquals(0, result.getY().getMeter());
	}
}
