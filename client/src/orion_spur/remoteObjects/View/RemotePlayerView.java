package orion_spur.remoteObjects.View;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.view.ImageActor;
import orion_spur.level.material.LevelElement;

public class RemotePlayerView extends ImageActor
{
	public RemotePlayerView(LevelElement levelElement)
	{
		super(levelElement);
		
		setWidth(20);
		setHeight(20);
		setX(levelElement.getPosition().x - getWidth() / 2);
		setY(levelElement.getPosition().y - getHeight() / 2);
		
		_sprite.setBounds(getX(), getY(), getWidth(), getHeight());
		_sprite.setOrigin(getWidth() / 2, getHeight() / 2);
		_sprite.rotate(levelElement.getRotation());
		
		setPosition(getX(), getY());
		
		Contract.Satisfy(_sprite != null);
		Contract.Satisfy(_sprite.getTexture() != null);
	}
	
	@Override
	public void act(float delta)
	{
		Vector2 movementAdjustion = getLevelElement().getMovementVector().scl(delta);
		
		setPosition(getX() + movementAdjustion.x, getY() + movementAdjustion.y);
		
		super.act(delta);
	}
}
