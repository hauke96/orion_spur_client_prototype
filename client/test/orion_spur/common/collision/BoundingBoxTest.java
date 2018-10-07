package orion_spur.common.collision;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BoundingBoxTest
{
	BoundingBox	b1;
	BoundingBox	b2;
	BoundingBox	b3;
	BoundingBox	b4;
	BoundingBox	b5;
	
	@Before
	public void init()
	{
		b1 = new BoundingBox(0, 0, 10, 10);
		b2 = new BoundingBox(5, 5, 10, 10);
		
		b3 = new BoundingBox(0, 5, 10, 10);
		b4 = new BoundingBox(5, 0, 10, 10);
		
		b5 = new BoundingBox(10, 0, 10, 10);
	}
	
	@Test
	public void normalIntersecting()
	{
		assertTrue(b1.intersectsWith(b2));
		assertTrue(b2.intersectsWith(b1));
	}
	
	@Test
	public void onlyYIntersecting()
	{
		assertTrue(b1.intersectsWith(b3));
		assertTrue(b3.intersectsWith(b1));
	}
	
	@Test
	public void onlyXIntersecting()
	{
		assertTrue(b1.intersectsWith(b4));
		assertTrue(b4.intersectsWith(b1));
	}
	
	@Test
	public void touchingButNotIntersecting()
	{
		assertFalse(b1.intersectsWith(b5));
		assertFalse(b5.intersectsWith(b1));
	}
}
