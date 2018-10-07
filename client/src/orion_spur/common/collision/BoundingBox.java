package orion_spur.common.collision;

public class BoundingBox
{
	private final int	left;
	private final int	top;
	private final int	right;
	private final int	bottom;
	
	public BoundingBox(int x, int y, int width, int height)
	{
		left = x;
		top = y;
		right = x + width;
		bottom = y + height;
	}
	
	public boolean intersectsWith(BoundingBox box)
	{
		return !(right <= box.left
		        || left >= box.right
		        || bottom <= box.top
		        || top >= box.bottom);
	}
}
