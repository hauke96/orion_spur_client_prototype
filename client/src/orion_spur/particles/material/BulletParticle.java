package orion_spur.particles.material;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.level.domainvalue.LevelElementType;
import orion_spur.level.view.LevelView.LayerZIndex;

public class BulletParticle extends Particle
{
	private static TextureRegion _texture;
	
	public BulletParticle(Vector2 position, Vector2 movementVector, float rotation)
	{
		super("BulletParticle" + System.nanoTime(),
		    position,
		    movementVector,
		    rotation,
		    LayerZIndex.LAYER_ANIMATION,
		    LevelElementType.IMAGE,
		    "assets/textures/bullet.png");
		
		if (_texture == null)
		{
			_texture = new TextureRegion(new Texture(Gdx.files.internal(getAssetPath())));
		}
		
		Contract.NotNull(_texture);
	}
	
	@Override
	public TextureRegion getTexture()
	{
		Contract.NotNull(_texture);
		return _texture;
	}
	
}
