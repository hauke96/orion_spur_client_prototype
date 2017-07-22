package orion_spur;

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
		LAYER_BACKGROUND, LAYER_1_BEHIND, LAYER_0_BEHIND, LAYER_PLAYER, LAYER_ANIMATION, LAYER_0_BEFORE, LAYER_1_BEFORE
	}
	
	private Map<LayerType, Set<Actor>>	_layers;
	private Map<LayerType, Float>		_layerToScale;
	
	public LayerActor()
	{
		_layers = new HashMap<LayerActor.LayerType, Set<Actor>>();
		
		_layers.put(LayerType.LAYER_BACKGROUND, new HashSet<>());
		
		_layers.put(LayerType.LAYER_1_BEHIND, new HashSet<>());
		_layers.put(LayerType.LAYER_0_BEHIND, new HashSet<>());
		
		_layers.put(LayerType.LAYER_PLAYER, new HashSet<>());
		_layers.put(LayerType.LAYER_ANIMATION, new HashSet<>());
		
		_layers.put(LayerType.LAYER_0_BEFORE, new HashSet<>());
		_layers.put(LayerType.LAYER_1_BEFORE, new HashSet<>());
		
		_layerToScale = new HashMap<LayerActor.LayerType, Float>();
		
		_layerToScale.put(LayerType.LAYER_BACKGROUND, 0.95f);
		
		_layerToScale.put(LayerType.LAYER_1_BEHIND, 0.4f);
		_layerToScale.put(LayerType.LAYER_0_BEHIND, 0.75f);
		
		_layerToScale.put(LayerType.LAYER_PLAYER, 1f);
		_layerToScale.put(LayerType.LAYER_ANIMATION, 1f);
		
		_layerToScale.put(LayerType.LAYER_0_BEFORE, 1.25f);
		_layerToScale.put(LayerType.LAYER_1_BEFORE, 2f);
		
		Contract.NotNull(_layers);
		Contract.Satisfy(_layers.values().size() == LayerType.values().length);
		Contract.NotNull(_layerToScale);
		Contract.Satisfy(_layerToScale.values().size() == LayerType.values().length);
	}
	
	public void addToLayer(Actor newActor, LayerType layerType)
	{
		Contract.Satisfy(layerType != LayerType.LAYER_PLAYER || layerType == LayerType.LAYER_PLAYER && !hasPlayer());
		
		// Even if the background is the most far away layer, it'll not be scales, but moves slower
		if (layerType != LayerType.LAYER_BACKGROUND)
		{
			newActor.setScale(_layerToScale.get(layerType));
		}
		
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
		for (LayerType type : LayerType.values())
		{
			for (Actor actor : _layers.get(type))
			{
				actor.draw(batch, parentAlpha);
			}
		}
	}
	
	@Override
	public void act(float delta)
	{
		for (LayerType type : LayerType.values())
		{
			for (Actor actor : _layers.get(type))
			{
				actor.act(delta);
			}
		}
	}
	
	public void onPlayerPositionChanged(Vector2 offset)
	{
		for (LayerType type : LayerType.values())
		{
			moveLayer(offset, type);
		}
	}
	
	private void moveLayer(Vector2 offset, LayerType type)
	{
		float scale = _layerToScale.get(type);
		
		if (type == LayerType.LAYER_0_BEFORE
		        || type == LayerType.LAYER_1_BEFORE
		        || type == LayerType.LAYER_0_BEHIND
		        || type == LayerType.LAYER_1_BEHIND)
		{
			// Negate, because otherwise the image will move in the opposite direction.
			scale *= -1;
		}
		
		move(_layers.get(type), new Vector2(offset.x * scale, offset.y * scale));
	}
	
	private void move(Set<Actor> actors, Vector2 offset)
	{
		for (Actor actor : actors)
		{
			actor.moveBy(offset.x, offset.y);
		}
	}
}
