package orion_spur.level.view;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;
import juard.log.Logger;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.factory.IActorFactory;
import orion_spur.level.material.LevelElement;
import orion_spur.level.service.ILevelService;

public class LevelView extends Actor
{
	public enum LayerZIndex
	{
		LAYER_BACKGROUND, LAYER_1_BEHIND, LAYER_0_BEHIND, LAYER_REMOTE_OBJECTS, LAYER_PLAYER, LAYER_ANIMATION, LAYER_0_BEFORE, LAYER_1_BEFORE
	}
	
	private Map<LayerZIndex, Layer>	_layerToScale;
	private IActorFactory			_actorFactory;
	private ILevelService			_levelService;
	private Position				_position;
	private Vector2					_size;
	
	public LevelView(ILevelService levelService, IActorFactory actorFactory)
	{
		Contract.NotNull(levelService);
		Contract.NotNull(actorFactory);
		
		_levelService = levelService;
		_actorFactory = actorFactory;
		
		// TODO add real level name when implemented
		_position = _levelService.getPosition("");
		_size = _levelService.getSizeInMeters("");
		
		_layerToScale = new TreeMap<LayerZIndex, Layer>();
		
		// TODO pass these from outside. Create e.g. a RemoteObjectLayer which adds all remote objects to it.
		_layerToScale.put(LayerZIndex.LAYER_BACKGROUND, new Layer(0.005f));
		
		_layerToScale.put(LayerZIndex.LAYER_1_BEHIND, new Layer(0.4f));
		_layerToScale.put(LayerZIndex.LAYER_0_BEHIND, new Layer(0.75f));
		
		_layerToScale.put(LayerZIndex.LAYER_REMOTE_OBJECTS, new Layer(1f));
		_layerToScale.put(LayerZIndex.LAYER_PLAYER, new Layer(1f));
		_layerToScale.put(LayerZIndex.LAYER_ANIMATION, new Layer(1f));
		
		_layerToScale.put(LayerZIndex.LAYER_0_BEFORE, new Layer(1.25f));
		_layerToScale.put(LayerZIndex.LAYER_1_BEFORE, new Layer(2f));
		
		Contract.NotNull(_layerToScale);
		Contract.Satisfy(_layerToScale.values().size() == LayerZIndex.values().length);
	}
	
	public void loadLevelElements() throws RuntimeException, Exception
	{
		_levelService.getLevel("").forEach((levelElement) -> addToLayer(levelElement));
	}
	
	public Position getPosition()
	{
		Contract.NotNull(_position);
		return _position;
	}
	
	public Vector2 getSize()
	{
		Contract.NotNull(_size);
		return _size;
	}
	
	// TODO refactor this to also add level elements and an actor
	public Actor addToLayer(LevelElement levelElement) throws RuntimeException
	{
		Contract.NotNull(levelElement);
		Contract.Satisfy(levelElement.getLayer() != LayerZIndex.LAYER_PLAYER
		        || levelElement.getLayer() == LayerZIndex.LAYER_PLAYER && !hasPlayer());
		// TODO contract: !hasLevelElement()
		
		Actor actor = new Actor();
		try
		{
			actor = _actorFactory.convert(levelElement);
		}
		catch (Exception e)
		{
			Logger.error("Error converting level element!", e);
		}
		
		// Even if the background is the most far away layer, it'll not be scales, but
		// moves slower
		if (levelElement.getLayer() != LayerZIndex.LAYER_BACKGROUND)
		{
			actor.setScale(_layerToScale.get(levelElement.getLayer()).getScale());
		}
		
		addToLayer(actor, levelElement.getLayer(), levelElement.getId());
		
		Contract.NotNull(actor);
		return actor;
	}
	
	public void addToLayer(Actor actor, LayerZIndex layerType, String layerId)
	{
		Contract.NotNull(actor);
		Contract.NotNull(layerType);
		Contract.NotNullOrEmpty(layerId);
		
		_layerToScale.get(layerType).add(actor);
	}
	
	public boolean hasPlayer()
	{
		// TODO remove or implement this
		return false;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		for (Layer layer : _layerToScale.values())
		{
			layer.draw(batch, parentAlpha);
		}
	}
	
	@Override
	public void act(float delta)
	{
		for (Layer layer : _layerToScale.values())
		{
			layer.act(delta);
		}
	}
	
	public void onPlayerPositionChanged(Vector2 offset)
	{
		for (Layer layer : _layerToScale.values())
		{
			layer.move(offset);
		}
	}
}
