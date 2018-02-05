package orion_spur.particles.service;

import java.util.Collection;

import orion_spur.particles.material.Particle;

public interface IParticleService
{
	public void add(Particle particle);
	
	public Collection<Particle> getAll();
}
