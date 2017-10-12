package orion_spur.remoteObjects.View;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.view.ImageActor;
import orion_spur.level.material.LevelElement;
import orion_spur.remoteObjects.Service.IRemoteObjectService;
import orion_spur.ships.material.SpaceShip;

public class RemotePlayerView extends ImageActor
{
	private IRemoteObjectService	_remoteObjectService;
	private ICoordinateConverter	_coordinateConverter;
	private SpaceShip				_ship;
	private LevelElement			_levelElement;
	
	public RemotePlayerView(IRemoteObjectService remoteObjectService, ICoordinateConverter coordinateConverter, LevelElement levelElement, SpaceShip ship)
	{
		super(ship.getAssetFile());
		Contract.NotNull(remoteObjectService);
		Contract.NotNull(coordinateConverter);
		Contract.NotNull(levelElement);
		
		_remoteObjectService = remoteObjectService;
		_coordinateConverter = coordinateConverter;
		_levelElement = levelElement;
		_ship = ship;
		
		setWidth(20);
		setHeight(20);
		setX(levelElement.getPosition().x - getWidth() / 2);
		setY(levelElement.getPosition().y - getHeight() / 2);
		
		_sprite.setBounds(getX(), getY(), getWidth(), getHeight());
		_sprite.setOrigin(getWidth() / 2, getHeight() / 2);
		_sprite.rotate(ship.getRotationDegree());
		
		super.setPosition(getX(), getY());
		
		Contract.Satisfy(_sprite != null);
		Contract.Satisfy(_sprite.getTexture() != null);
	}
	
	@Override
	public void act(float delta)
	{
		Vector2 movementAdjustion = _levelElement.getMovementVector().scl(delta);
		
		setPosition(getX() + movementAdjustion.x, getY() + movementAdjustion.y);
		
		super.act(delta);
	}
}
