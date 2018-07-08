package orion_spur.particles.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;
import juard.log.Logger;
import orion_spur.particles.material.Particle;
import orion_spur.particles.service.IParticleService;

public class ParticleView extends Actor
{
	private IParticleService _particleService;
	
	public ParticleView(IParticleService particleService)
	{
		Contract.NotNull(particleService);
		
		_particleService = particleService;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		try
		{
			// TODO Create a cache in the particle service and create goms-events
			for (Particle particle : _particleService.getAll())
			{
				int width = particle.getTexture().getRegionWidth() * 6;
				int height = particle.getTexture().getRegionHeight() * 6;
				
				batch.draw(particle.getTexture(),
				    particle.getPosition().x - width / 2,
				    particle.getPosition().y - height / 2,
				    width / 2,
				    height / 2,
				    width,
				    height,
				    1,
				    1,
				    particle.getRotation(),
				    true);
			}
		}
		catch (Exception e)
		{
			Logger.error("Could not get particles", e);
		}
	}
	
	@Override
	public void act(float delta)
	{
		_particleService.act(delta);
	}
}
