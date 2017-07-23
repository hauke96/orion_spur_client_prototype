package orion_spur.common.factory;

import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;
import orion_spur.common.view.ImageActor;
import orion_spur.level.material.LevelElement;

public class ActorFactoryImpl implements IActorFactory
{
	@Override
	public Actor convert(LevelElement levelElement)
	{
		Actor result = null;
		
		switch (levelElement.getType())
		{
			case IMAGE:
				result = new ImageActor(levelElement.getAssetPath());
				break;
			default:
				result = new Actor();
				break;
		}
		
		Contract.NotNull(result);
		return result;
	}
}
