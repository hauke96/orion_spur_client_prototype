package orion_spur.particles.service;

import java.io.IOException;
import java.util.Collection;

import orion_spur.common.exception.HttpException;
import orion_spur.particles.material.Particle;

public interface IParticleService
{
	public void add(Particle particle);
	
	public Collection<Particle> getAll() throws IOException, HttpException;
	
	public void act(float delta);
}
