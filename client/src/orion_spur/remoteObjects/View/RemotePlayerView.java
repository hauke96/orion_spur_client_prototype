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
	private ICoordinateConverter _coordinateConverter;
	
	public RemotePlayerView(LevelElement levelElement, ICoordinateConverter coordinateConverter)
	{
		super(levelElement, 600, 600);
		
		Contract.NotNull(coordinateConverter);
		
		_coordinateConverter = coordinateConverter;
		
		IRemoteObjectService.RemoteObjectChanged.add(this::OnRemoteObjectChanged);
	}
	
	private void OnRemoteObjectChanged(RemoteObject object)
	{
		if (object.getId().equals(getLevelElement().getId()))
		{
			Vector2 position = _coordinateConverter.universeToWorld(object.getPosition());
			
			setX(position.x - getWidth() / 2);
			setY(position.y - getHeight() / 2);
			
			setRotation(object.getRotation());
			
			getLevelElement().setMovementVector(object.getMovementVector());
			
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
