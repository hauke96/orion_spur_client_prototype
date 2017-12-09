package orion_spur.common.factory;

import com.badlogic.gdx.scenes.scene2d.Actor;

import orion_spur.level.material.LevelElement;

public interface IActorFactory
{
	Actor convert(LevelElement levelElement) throws Exception;
}
