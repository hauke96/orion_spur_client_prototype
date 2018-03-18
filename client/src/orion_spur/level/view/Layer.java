package orion_spur.level.view;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;

public class Layer extends Actor implements Comparable<Layer>
{
	private final Set<Actor>	_elements;
	private float				_offsetScale;	// When moves, this scale will be taken
	private float				_scale;
	
	public Layer(float scale)
	{
		Contract.Satisfy(scale >= 0);
		
		// Adjust scale to not do this every time something has to be drawn. This is needed for the perspective effect
		// that things behind _and_ in before the player should move in the same direction. Normal behavior (an in this
		// case also wrong behavior) is that they move in opposite directions.
		_offsetScale = (float) (scale * -1 + 1);
		_scale = scale;
		
		_elements = new HashSet<>();
		
		Contract.NotNull(_elements);
	}
	
	public void add(Actor element)
	{
		Contract.NotNull(element);
		Contract.Satisfy(!has(element));
		
		_elements.add(element);
		
		Contract.Satisfy(has(element));
	}
	
	public boolean has(Actor element)
	{
		Contract.NotNull(element);
		
		return _elements.contains(element);
	}
	
	public float getScale()
	{
		return _scale;
	}
	
	public void move(Vector2 offset)
	{
		for (Actor actor : _elements)
		{
			actor.moveBy(offset.x * _offsetScale, offset.y * _offsetScale);
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		Contract.NotNull(batch);
		Contract.Satisfy(parentAlpha >= 0);
		
		for (Actor actor : _elements)
		{
			actor.draw(batch, parentAlpha);
		}
	}
	
	@Override
	public void act(float delta)
	{
		Contract.Satisfy(delta > 0);
		
		for (Actor actor : _elements)
		{
			actor.act(delta);
		}
	}
	
	@Override
	public int compareTo(Layer otherLayer)
	{
		if (_scale > otherLayer._scale)
		{
			return 1;
		}
		if (_scale < otherLayer._scale)
		{
			return -1;
		}
		return 0;
	}
}
