package orion_spur.remoteObjects.View;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.service.ICurrentWorldService;
import orion_spur.common.view.ImageActor;
import orion_spur.level.material.LevelElement;
import orion_spur.remoteObjects.Service.IRemoteObjectService;
import orion_spur.remoteObjects.material.RemoteObject;

public class RemoteObjectView extends ImageActor
{
	private ICoordinateConverter _coordinateConverter;
	
	public RemoteObjectView(LevelElement levelElement, ICoordinateConverter coordinateConverter, ICurrentWorldService currentWorldService)
	{
		super(levelElement, currentWorldService, 600, 600);
		
		Contract.NotNull(coordinateConverter);
		
		_coordinateConverter = coordinateConverter;
		
		IRemoteObjectService.RemoteObjectChanged.add(this::OnRemoteObjectChanged);
	}
	
	private void OnRemoteObjectChanged(RemoteObject object)
	{
		if (object.getId().equals(getLevelElement().getId()))
		{
			Vector2 position = _coordinateConverter.universeToWorld(object.getPosition());
			
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
