package orion_spur.common.factory;

import java.util.logging.Level;

import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;
import orion_spur.common.view.ImageActor;
import orion_spur.level.material.LevelElement;
import orion_spur.player.service.IPlayerService;
import orion_spur.player.view.Player;

public class ActorFactoryImpl implements IActorFactory
{
	private IPlayerService _playerService;

	public ActorFactoryImpl(IPlayerService playerService) {
		Contract.NotNull(playerService);
		
		_playerService = playerService;
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
				result = new Player(_playerService, levelElement.getAssetPath());
				break;
			default:
				result = new Actor();
				break;
		}
		
		Contract.NotNull(result);
		return result;
	}
}
