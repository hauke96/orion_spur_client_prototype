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
		
		_layerToScale.put(LayerType.LAYER_BACKGROUND, 1f);
		
		_layerToScale.put(LayerType.LAYER_1_BEHIND, 0.45f);
		_layerToScale.put(LayerType.LAYER_0_BEHIND, 0.85f);
		
		_layerToScale.put(LayerType.LAYER_PLAYER, 1f);
		_layerToScale.put(LayerType.LAYER_ANIMATION, 1f);
		
		_layerToScale.put(LayerType.LAYER_0_BEFORE, 1.15f);
		_layerToScale.put(LayerType.LAYER_1_BEFORE, 1.35f);
		
		Contract.NotNull(_layers);
		Contract.Satisfy(_layers.values().size() == LayerType.values().length);
		Contract.NotNull(_layerToScale);
		Contract.Satisfy(_layerToScale.values().size() == LayerType.values().length);
	}
	
	public void addToLayer(Actor newActor, LayerType layerType)
	{
		Contract.Satisfy(layerType != LayerType.LAYER_PLAYER || layerType == LayerType.LAYER_PLAYER && !hasPlayer());
		
		newActor.setScale(_layerToScale.get(layerType));
		
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
			draw(batch, parentAlpha, _layers.get(type));
		}
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
		for (LayerType type : LayerType.values())
		{
			act(_layers.get(type), delta);
		}
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
		move(_layers.get(LayerType.LAYER_BACKGROUND), new Vector2(offset.x * 0.9f, offset.y * 0.9f));
		
		move(_layers.get(LayerType.LAYER_1_BEHIND), new Vector2(offset.x * 0.55f, offset.y * 0.55f));
		move(_layers.get(LayerType.LAYER_0_BEHIND), new Vector2(offset.x * 0.15f, offset.y * 0.15f));
		
		// Negative numbers, because otherwise the image will move in the opposite direction (because the player is (1,1)).
		move(_layers.get(LayerType.LAYER_0_BEFORE), new Vector2(offset.x * -1.15f, offset.y * -1.15f));
		move(_layers.get(LayerType.LAYER_1_BEFORE), new Vector2(offset.x * -1.45f, offset.y * -1.45f));
	}
	
	private void move(Set<Actor> actors, Vector2 offset)
	{
		for (Actor actor : actors)
		{
			actor.moveBy(offset.x, offset.y);
		}
	}
}
