package orion_spur.common.factory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.common.view.ImageActor;
import orion_spur.level.material.LevelElement;
import orion_spur.level.service.ILevelService;
import orion_spur.player.service.IPlayerService;
import orion_spur.player.view.PlayerView;
import orion_spur.ships.material.SpaceShip;

public class ActorFactoryImpl implements IActorFactory
{
	private IPlayerService			_playerService;
	private ILevelService			_levelService;
	private IUnitConverter			_unitConverter;
	private ICoordinateConverter	_coordinateConverter;
	
	public ActorFactoryImpl(IPlayerService playerService, ILevelService levelService, IUnitConverter unitConverter, ICoordinateConverter coordinateConverter)
	{
		Contract.NotNull(playerService);
		Contract.NotNull(levelService);
		Contract.NotNull(unitConverter);
		Contract.NotNull(coordinateConverter);
		
		_playerService = playerService;
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
				result = new ImageActor(levelElement.getAssetPath());
				setPosition(levelElement, result);
				break;
			case PLAYER:
				_playerService.createPlayer();
				SpaceShip ship = new SpaceShip(_unitConverter.convertFromWorld(3), _unitConverter.convertFromWorld(100), 250, 0, levelElement.getAssetPath());
				result = new PlayerView(_playerService, _unitConverter, _coordinateConverter, _coordinateConverter.universeToWorld(_playerService.getPosition()), ship);
				break;
			default:
				result = new Actor();
				setPosition(levelElement, result);
				break;
		}
		
		Contract.NotNull(result);
		return result;
	}
	
	private void setPosition(LevelElement levelElement, Actor actor)
	{
		Vector2 position = _coordinateConverter.universeToWorld(levelElement.getPosition());
		System.out.println(position);
		actor.setPosition(position.x, position.y);
	}
}
