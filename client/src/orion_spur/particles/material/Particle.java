package orion_spur.particles.material;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import orion_spur.common.service.LayerActor.LayerType;
import orion_spur.level.domainvalue.LevelType;
import orion_spur.level.material.LevelElement;

public abstract class Particle extends LevelElement
{
	protected Particle(String id, Vector2 position, Vector2 movementVector, float rotation, LayerType layer, LevelType type, String assetPath)
	{
		super(id, position, movementVector, rotation, layer, type, assetPath);
	}
	
	public abstract Texture getTexture();
}
