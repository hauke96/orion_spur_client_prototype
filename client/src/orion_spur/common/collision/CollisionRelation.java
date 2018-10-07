package orion_spur.common.collision;

import java.util.HashMap;
import java.util.Map;

public class CollisionRelation
{
	private final Map<BoundingBoxProvider, CollisionCallback> _providerToCallback;
	
	public CollisionRelation()
	{
		_providerToCallback = new HashMap<>();
	}
	
	public void set(BoundingBoxProvider provider, CollisionCallback callback)
	{
		_providerToCallback.put(provider, callback);
	}
	
	public void handleCollision()
	{
		// TODO check if there's a collision and call the callback
		
		// TODO Maybe create a rectangle where the two object overlap and send the center of it with the callback. The
		// receiver of this callback can then show an animation at this point or so.
	}
}
