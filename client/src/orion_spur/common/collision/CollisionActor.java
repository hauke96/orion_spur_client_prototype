package orion_spur.common.collision;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;

import orion_spur.player.view.PlayerView;

public class CollisionActor extends Actor
{
	private final List<CollisionRelation> _relations;
	
	public CollisionActor()
	{
		_relations = new ArrayList<>();
	}
	
	@Override
	public void act(float delta)
	{
		_relations.forEach(CollisionRelation::handleCollision);
	}
	
	public void addPlayer(PlayerView view)
	{
		CollisionRelation relation = new CollisionRelation();
		
		relation.setFirst(view, (BoundingBox box) -> System.out.println("first"));
	}
}
