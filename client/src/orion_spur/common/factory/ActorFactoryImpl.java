package orion_spur.common.factory;

import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.common.view.ImageActor;
import orion_spur.level.material.LevelElement;
import orion_spur.level.service.ILevelService;
import orion_spur.player.service.IPlayerService;
import orion_spur.player.view.PlayerView;
import orion_spur.remoteObjects.Service.IRemoteObjectService;
import orion_spur.remoteObjects.View.RemotePlayerView;
import orion_spur.ships.material.SpaceShip;

public class ActorFactoryImpl implements IActorFactory
{
	private IPlayerService			_playerService;
	private IRemoteObjectService	_remoteObjectService;
	private ILevelService			_levelService;
	private IUnitConverter			_unitConverter;
	private ICoordinateConverter	_coordinateConverter;
	
	public ActorFactoryImpl(IPlayerService playerService, IRemoteObjectService remoteObjectService, ILevelService levelService, IUnitConverter unitConverter, ICoordinateConverter coordinateConverter)
	{
		Contract.NotNull(playerService);
		Contract.NotNull(remoteObjectService);
		Contract.NotNull(levelService);
		Contract.NotNull(unitConverter);
		Contract.NotNull(coordinateConverter);
		
		_playerService = playerService;
		_remoteObjectService = remoteObjectService;
		_levelService = levelService;
		_unitConverter = unitConverter;
		_coordinateConverter = coordinateConverter;
	}
	
	@Override
	public Actor convert(LevelElement levelElement) throws Exception
	{
		Actor result = null;
		
		switch (levelElement.getType())
		{
			case IMAGE:
				result = new ImageActor(levelElement);
				setPosition(levelElement, result);
				break;
			case PLAYER:
				result = new PlayerView(_playerService, _unitConverter, (SpaceShip) levelElement, _coordinateConverter.universeToWorld(_playerService.getPosition()));
				break;
			case REMOTE_OBJECT:
				result = new RemotePlayerView(levelElement, _coordinateConverter, _remoteObjectService);
				break;
			default:
				result = new Actor();
				// setPosition(levelElement, result);
				break;
		}
		
		Contract.NotNull(result);
		return result;
	}
	
	private void setPosition(LevelElement levelElement, Actor actor)
	{
		actor.setPosition(levelElement.getPosition().x, levelElement.getPosition().y);
	}
}
