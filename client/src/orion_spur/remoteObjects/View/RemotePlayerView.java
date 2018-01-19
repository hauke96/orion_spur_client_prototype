package orion_spur.remoteObjects.View;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.view.ImageActor;
import orion_spur.level.material.LevelElement;
import orion_spur.remoteObjects.Service.IRemoteObjectService;
import orion_spur.remoteObjects.material.RemoteObject;

public class RemotePlayerView extends ImageActor
{
	private IRemoteObjectService	_remoteObjectService;
	private ICoordinateConverter	_coordinateConverter;
	
	public RemotePlayerView(LevelElement levelElement, ICoordinateConverter coordinateConverter, IRemoteObjectService service)
	{
		super(levelElement);
		
		Contract.NotNull(coordinateConverter);
		Contract.NotNull(service);
		
		_coordinateConverter = coordinateConverter;
		_remoteObjectService = service;
		
		setWidth(20);
		setHeight(20);
		setX(levelElement.getPosition().x - getWidth() / 2);
		setY(levelElement.getPosition().y - getHeight() / 2);
		
		_sprite.setBounds(getX(), getY(), getWidth(), getHeight());
		_sprite.setOrigin(getWidth() / 2, getHeight() / 2);
		_sprite.rotate(levelElement.getRotation());
		
		setPosition(getX(), getY());
		
		service.RemoteObjectChanged.add(this::OnRemoteObjectChanged);
		
		Contract.Satisfy(_sprite != null);
		Contract.Satisfy(_sprite.getTexture() != null);
	}
	
	private void OnRemoteObjectChanged(RemoteObject object)
	{
		if (object.getId().equals(getLevelElement().getId()))
		{
			Vector2 position = _coordinateConverter.universeToWorld(object.getPosition());
			
			setPosition(position.x, position.y);
			getLevelElement().setPosition(position);
		}
	}
	
	@Override
	public void act(float delta)
	{
		Vector2 movementAdjustion = getLevelElement().getMovementVector().scl(delta);
		
		setPosition(getX() + movementAdjustion.x, getY() + movementAdjustion.y);
		
		super.act(delta);
	}
}
