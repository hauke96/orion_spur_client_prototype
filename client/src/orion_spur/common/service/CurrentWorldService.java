package orion_spur.common.service;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import juard.contract.Contract;

public class CurrentWorldService implements ICurrentWorldService
{
	private World _world;
	
	private float _meterPerPixel;
	
	public CurrentWorldService(float meterPerPixel)
	{
		Contract.Satisfy(meterPerPixel > 0);
		
		_world = new World(new Vector2(0, 0f), true);
		_meterPerPixel = meterPerPixel;
	}
	
	@Override
	public boolean hasWorld()
	{
		return _world != null;
	}
	
	@Override
	public World getWorld()
	{
		Contract.Satisfy(hasWorld());
		
		return _world;
	}
	
	@Override
	public float meterPerPixel()
	{
		return _meterPerPixel;
	}
	
	@Override
	public Body createBody(BodyDef def)
	{
		Contract.NotNull(def);
		Contract.Satisfy(hasWorld());
		
		return _world.createBody(def);
	}
	
	@Override
	public void simulate(float delta)
	{
		Contract.Satisfy(delta >= 0);
		Contract.Satisfy(hasWorld());
		
		_world.step(delta, 6, 2);
	}
	
	@Override
	public void dispose()
	{
		Contract.Satisfy(hasWorld());
		
		_world.dispose();
	}
}
