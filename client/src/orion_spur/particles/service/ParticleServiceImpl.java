package orion_spur.particles.service;

import java.util.Collection;
import java.util.HashSet;

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
}
