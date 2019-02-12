package orion_spur.level.view;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;
import juard.log.Logger;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.factory.IActorFactory;
import orion_spur.common.service.ICurrentWorldService;
import orion_spur.level.material.LevelElement;
import orion_spur.level.service.ILevelService;

public class LevelView extends Actor
{
	public enum LayerZIndex
	{
		LAYER_BACKGROUND(1 << 0), //
		LAYER_1_BEHIND(1 << 1), //
		LAYER_0_BEHIND(1 << 2), //
		LAYER_REMOTE_OBJECTS(1 << 3), //
		LAYER_PLAYER(1 << 4), //
		LAYER_ANIMATION(1 << 5), //
		LAYER_0_BEFORE(1 << 6), //
		LAYER_1_BEFORE(1 << 7); //
		
		public final int Z;
		
		LayerZIndex(int z)
		{
			Z = z;
		}
	}
	
	private ILevelService			_levelService;
	private IActorFactory			_actorFactory;
	private ICurrentWorldService	_currentWorldService;
	
	private Map<LayerZIndex, Layer> _layerToScale;
	
	private Position	_position;
	private Vector2		_size;
	
	private Box2DDebugRenderer _debugBodyRenderer;
	
	public LevelView(ILevelService levelService, IActorFactory actorFactory, ICurrentWorldService currentWorldService)
	{
		Contract.NotNull(levelService);
		Contract.NotNull(actorFactory);
		Contract.NotNull(currentWorldService);
		
		_levelService = levelService;
		_actorFactory = actorFactory;
		_currentWorldService = currentWorldService;
		
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
		
		_debugBodyRenderer = new Box2DDebugRenderer(true, false, false, false, true, true);
		
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
		Matrix4 debugMatrix = batch.getProjectionMatrix()
		    .cpy()
		    .scale(_currentWorldService.meterPerPixel(),
		        _currentWorldService.meterPerPixel(),
		        0);
		
		_currentWorldService.simulate(1 / 60f);
		
		for (Layer layer : _layerToScale.values())
		{
			layer.draw(batch, parentAlpha);
		}
		
		_debugBodyRenderer.render(_currentWorldService.getWorld(), debugMatrix);
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
