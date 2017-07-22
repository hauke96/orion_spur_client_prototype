package orion_spur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor
{
	private Sprite _sprite;
	
	public Background(String file)
	{
		Texture texture = new Texture(Gdx.files.internal(file));
		
		setBounds(0, 0, texture.getWidth(), texture.getHeight());
		
		_sprite = new Sprite(texture);
		_sprite.setBounds(getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		_sprite.draw(batch);
	}
}
