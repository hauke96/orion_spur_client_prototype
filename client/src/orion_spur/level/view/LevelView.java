package orion_spur.level.view;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
	public enum LayerType
	{
		LAYER_BACKGROUND, LAYER_1_BEHIND, LAYER_0_BEHIND, LAYER_REMOTE_OBJECTS, LAYER_PLAYER, LAYER_ANIMATION, LAYER_0_BEFORE, LAYER_1_BEFORE
	}
	
	private Map<LayerType, HashMap<String, Actor>>	_layers;
	private Map<LayerType, Float>					_layerToScale;
	private IActorFactory							_actorFactory;
	private ILevelService							_levelService;
	private Position								_position;
	private Vector2									_size;
	
	public LevelView(ILevelService levelService, IActorFactory actorFactory)
	{
		Contract.NotNull(levelService);
		Contract.NotNull(actorFactory);
		
		_levelService = levelService;
		_actorFactory = actorFactory;
		
		// TODO add real level name when implemented
		_position = _levelService.getPosition("");
		_size = _levelService.getSizeInMeters("");
		
		_layers = new HashMap<LevelView.LayerType, HashMap<String, Actor>>();
		
		_layers.put(LayerType.LAYER_BACKGROUND, new HashMap<>());
		
		_layers.put(LayerType.LAYER_1_BEHIND, new HashMap<>());
		_layers.put(LayerType.LAYER_0_BEHIND, new HashMap<>());
		
		_layers.put(LayerType.LAYER_REMOTE_OBJECTS, new HashMap<>());
		_layers.put(LayerType.LAYER_PLAYER, new HashMap<>());
		_layers.put(LayerType.LAYER_ANIMATION, new HashMap<>());
		
		_layers.put(LayerType.LAYER_0_BEFORE, new HashMap<>());
		_layers.put(LayerType.LAYER_1_BEFORE, new HashMap<>());
		
		_layerToScale = new HashMap<LevelView.LayerType, Float>();
		
		_layerToScale.put(LayerType.LAYER_BACKGROUND, 0.995f);
		
		_layerToScale.put(LayerType.LAYER_1_BEHIND, 0.4f);
		_layerToScale.put(LayerType.LAYER_0_BEHIND, 0.75f);
		
		_layerToScale.put(LayerType.LAYER_REMOTE_OBJECTS, 1f);
		_layerToScale.put(LayerType.LAYER_PLAYER, 1f);
		_layerToScale.put(LayerType.LAYER_ANIMATION, 1f);
		
		_layerToScale.put(LayerType.LAYER_0_BEFORE, 1.25f);
		_layerToScale.put(LayerType.LAYER_1_BEFORE, 2f);
		
		Contract.NotNull(_layers);
		Contract.Satisfy(_layers.values().size() == LayerType.values().length);
		Contract.NotNull(_layerToScale);
		Contract.Satisfy(_layerToScale.values().size() == LayerType.values().length);
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
		Contract.Satisfy(levelElement.getLayer() != LayerType.LAYER_PLAYER
		        || levelElement.getLayer() == LayerType.LAYER_PLAYER && !hasPlayer());
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
		if (levelElement.getLayer() != LayerType.LAYER_BACKGROUND)
		{
			actor.setScale(_layerToScale.get(levelElement.getLayer()));
		}
		
		addToLayer(actor, levelElement.getLayer(), levelElement.getId());
		
		Contract.NotNull(actor);
		return actor;
	}
	
	public void addToLayer(Actor actor, LayerType layerType, String layerId)
	{
		Contract.NotNull(actor);
		Contract.NotNull(layerType);
		Contract.NotNullOrEmpty(layerId);
		
		Map<String, Actor> layer = _layers.get(layerType);
		
		layer.put(layerId, actor);
	}
	
	public boolean hasPlayer()
	{
		Map<String, Actor> layer = _layers.get(LayerType.LAYER_PLAYER);
		
		return layer != null && layer.size() != 0;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		for (LayerType type : LayerType.values())
		{
			for (Actor actor : _layers.get(type).values())
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
			for (Actor actor : _layers.get(type).values())
			{
				actor.act(delta);
			}
		}
	}
	
	public void onPlayerPositionChanged(Vector2 offset)
	{
		for (LayerType type : LayerType.values())
		{
			// The player already got its offset
			if (type != LayerType.LAYER_PLAYER && type != LayerType.LAYER_ANIMATION
			        && type != LayerType.LAYER_REMOTE_OBJECTS)
			{
				moveLayer(offset, type);
			}
		}
	}
	
	private void moveLayer(Vector2 offset, LayerType type)
	{
		float scale = _layerToScale.get(type);
		
		if (type == LayerType.LAYER_0_BEFORE || type == LayerType.LAYER_1_BEFORE)
		{
			// Negate, because otherwise the image will move in the opposite direction.
			scale *= -1;
		}
		else if (type == LayerType.LAYER_0_BEHIND || type == LayerType.LAYER_1_BEHIND)
		{
			// Negate, because otherwise the image will move in the opposite direction.
			scale = 1 - scale;
		}
		
		move(_layers.get(type).values(), new Vector2(offset.x * scale, offset.y * scale));
	}
	
	private void move(Collection<Actor> actors, Vector2 offset)
	{
		for (Actor actor : actors)
		{
			actor.moveBy(offset.x, offset.y);
		}
	}
}
