package orion_spur.common.service;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import juard.contract.Contract;

public class CurrentWorldService implements ICurrentWorldService
{
	private World	_world;
	private int		_meterPerPixel;
	
	public CurrentWorldService(World world, int meterPerPixel)
	{
		Contract.NotNull(world);
		Contract.Satisfy(meterPerPixel > 0);
		
		_world = world;
		_meterPerPixel = meterPerPixel;
	}
	
	@Override
	public boolean hasWorld()
	{
		return _world != null;
	}
	
	@Override
	public int meterPerPixel()
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
