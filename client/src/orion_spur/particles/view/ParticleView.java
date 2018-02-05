package orion_spur.particles.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;
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
		for (Particle particle : _particleService.getAll())
		{
			int width = particle.getTexture().getRegionWidth();
			int height = particle.getTexture().getRegionHeight();
			
			batch.draw(particle.getTexture(),
			    particle.getPosition().x,
			    particle.getPosition().y,
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
	
	@Override
	public void act(float delta)
	{
		_particleService.act(delta);
	}
}
