package orion_spur;

import java.io.InvalidObjectException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;

public class LayerActor extends Actor
{
	public enum LayerType
	{
		LAYER_0, LAYER_1, LAYER_2, LAYER_PLAYER, LAYER_ANIMATION,
	}
	
	private Map<LayerType, Set<Actor>> _layers;
	
	public LayerActor()
	{
		_layers = new HashMap<LayerActor.LayerType, Set<Actor>>();
		
		_layers.put(LayerType.LAYER_0, new HashSet<>());
		_layers.put(LayerType.LAYER_1, new HashSet<>());
		_layers.put(LayerType.LAYER_2, new HashSet<>());
		
		_layers.put(LayerType.LAYER_PLAYER, new HashSet<>());
		
		_layers.put(LayerType.LAYER_ANIMATION, new HashSet<>());
		
		Contract.NotNull(_layers);
	}
	
	public void addToLayer(Actor newActor, LayerType layerType)
	{
		Contract.Satisfy(layerType != LayerType.LAYER_PLAYER || layerType == LayerType.LAYER_PLAYER && !hasPlayer());
		
		Set<Actor> layer = _layers.get(layerType);
		
		layer = new HashSet<>();
		_layers.put(layerType, layer);
		
		layer.add(newActor);
	}
	
	public boolean hasPlayer()
	{
		Set<Actor> layer = _layers.get(LayerType.LAYER_PLAYER);
		
		return layer != null && layer.size() != 0;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		draw(batch, parentAlpha, _layers.get(LayerType.LAYER_0));
		draw(batch, parentAlpha, _layers.get(LayerType.LAYER_1));
		draw(batch, parentAlpha, _layers.get(LayerType.LAYER_2));
		
		draw(batch, parentAlpha, _layers.get(LayerType.LAYER_PLAYER));
		draw(batch, parentAlpha, _layers.get(LayerType.LAYER_ANIMATION));
	}
	
	private void draw(Batch batch, float parentAlpha, Set<Actor> actors)
	{
		for (Actor actor : actors)
		{
			actor.draw(batch, parentAlpha);
		}
	}
	
	@Override
	public void act(float delta)
	{
		act(_layers.get(LayerType.LAYER_0), delta);
		act(_layers.get(LayerType.LAYER_1), delta);
		act(_layers.get(LayerType.LAYER_2), delta);
		
		act(_layers.get(LayerType.LAYER_PLAYER), delta);
		act(_layers.get(LayerType.LAYER_ANIMATION), delta);
	}
	
	private void act(Set<Actor> actors, float delta)
	{
		for (Actor actor : actors)
		{
			actor.act(delta);
		}
	}

	public void onPlayerPositionChanged(Vector2 offset)
	{
		move(_layers.get(LayerType.LAYER_0), new Vector2(offset.x*0.9f, offset.y*0.9f));
		move(_layers.get(LayerType.LAYER_1), new Vector2(offset.x*0.45f, offset.y*0.45f));
		move(_layers.get(LayerType.LAYER_2), new Vector2(offset.x*0.15f, offset.y*0.15f));
	}

	private void move(Set<Actor> actors, Vector2 offset)
	{
		for (Actor actor : actors)
		{
			actor.moveBy(offset.x, offset.y);
		}
	}
}
