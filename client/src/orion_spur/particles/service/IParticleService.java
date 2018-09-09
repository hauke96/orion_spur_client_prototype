package orion_spur.particles.service;

import java.io.IOException;
import java.util.Collection;

import orion_spur.common.exception.HttpException;
import orion_spur.particles.material.Particle;

public interface IParticleService
{
	public void add(Particle particle) throws IOException, HttpException;
	
	/**
	 * Gets all remote particles and add the new ones to the local cache.
	 * 
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	void syncFromRemote() throws IOException, HttpException;
	
	public Collection<Particle> getAll();
	
	public void act(float delta);
}
