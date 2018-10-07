package orion_spur.common.collision;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class CollisionActor extends Actor
{
	List<CollisionRelation> _relations;
	
	public CollisionActor(BoundingBoxProvider shipProvider)
	{
		_relations = new ArrayList<>();
	}
	
	@Override
	public void act(float delta)
	{
		_relations.forEach(CollisionRelation::handleCollision);
	}
}
