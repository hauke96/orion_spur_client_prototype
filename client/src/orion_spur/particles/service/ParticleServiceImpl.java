package orion_spur.particles.service;

import java.util.Collection;
import java.util.HashSet;

import com.badlogic.gdx.math.Vector2;

import orion_spur.particles.material.Particle;

public class ParticleServiceImpl implements IParticleService
{
	private Collection<Particle> _particles;
	
	public ParticleServiceImpl()
	{
		_particles = new HashSet<>();
	}
	
	@Override
	public void add(Particle particle)
	{
		_particles.add(particle);
	}
	
	@Override
	public Collection<Particle> getAll()
	{
		// TODO do not pass original reference
		return _particles;
	}
	
	@Override
	public void act(float delta)
	{
		for (Particle particle : _particles)
		{
			Vector2 movementAdjustion = particle.getMovementVector().scl(delta);
			
			Vector2 newPosition = particle.getPosition().add(movementAdjustion);
			
			particle.setPosition(newPosition);
		}
	}
}
