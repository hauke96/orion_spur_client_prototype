package orion_spur.common.factory;

import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.common.view.ImageActor;
import orion_spur.level.material.LevelElement;
import orion_spur.player.service.IPlayerService;
import orion_spur.player.view.Player;

public class ActorFactoryImpl implements IActorFactory
{
	private IPlayerService	_playerService;
	private IUnitConverter	_unitConverter;
	
	public ActorFactoryImpl(IPlayerService playerService, IUnitConverter unitConverter)
	{
		Contract.NotNull(playerService);
		Contract.NotNull(unitConverter);
		
		_playerService = playerService;
		_unitConverter = unitConverter;
	}
	
	@Override
	public Actor convert(LevelElement levelElement)
	{
		Actor result = null;
		
		switch (levelElement.getType())
		{
			case IMAGE:
				result = new ImageActor(levelElement.getAssetPath());
				break;
			case PLAYER:
				result = new Player(_playerService, _unitConverter, levelElement.getAssetPath());
				break;
			default:
				result = new Actor();
				break;
		}
		
		Contract.NotNull(result);
		return result;
	}
}
