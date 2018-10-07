package orion_spur.common.collision;

import java.util.List;

public class CollisionRelation
{
	private BoundingBoxProvider	_firstProvider;
	private BoundingBoxProvider	_secondProvider;
	
	private CollisionCallback	_firstCallback;
	private CollisionCallback	_secondCallback;
	
	public void setFirst(BoundingBoxProvider provider, CollisionCallback callback)
	{
		_firstProvider = provider;
		_firstCallback = callback;
	}
	
	public void setSecond(BoundingBoxProvider provider, CollisionCallback callback)
	{
		_secondProvider = provider;
		_secondCallback = callback;
	}
	
	public void handleCollision()
	{
		List<BoundingBox> firstProvidersBoxes = _firstProvider.getBoundingBoxes();
		List<BoundingBox> secondProvidersBoxes = _secondProvider.getBoundingBoxes();
		
		for (BoundingBox first : firstProvidersBoxes)
		{
			for (BoundingBox second : secondProvidersBoxes)
			{
				if (first.intersectsWith(second))
				{
					_firstCallback.handleCollision(first);
					_secondCallback.handleCollision(second);
					return;
				}
			}
		}
		
		// TODO Maybe create a rectangle where the two object overlap and send the center of it with the callback. The
		// receiver of this callback can then show an animation at this point or so.
	}
}
