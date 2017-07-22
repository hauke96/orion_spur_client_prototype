package orion_spur;

import java.io.InvalidObjectException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
		
		Contract.NotNull(_layers);
	}
	
	public void addToLayer(Actor newActor, LayerType layerType)
	{
		Contract.Satisfy(layerType==LayerType.LAYER_PLAYER && !hasPlayer());
		
		Set<Actor> layer = _layers.get(layerType);
		
		layer.add(newActor);
	}
	
	public boolean hasPlayer()
	{
		Set<Actor> layer = _layers.get(LayerType.LAYER_PLAYER);
		
		return layer != null && layer.size() != 0;
	}
}
