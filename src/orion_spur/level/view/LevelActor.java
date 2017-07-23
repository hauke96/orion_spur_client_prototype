package orion_spur.level.view;

import orion_spur.common.service.LayerActor;
import orion_spur.common.view.ImageActor;

public class LevelActor extends LayerActor
{
	public LevelActor()
	{
		super();
		
		addToLayer(new ImageActor("assets/textures/milkyway.jpg"), LayerType.LAYER_BACKGROUND);
		addToLayer(new ImageActor("assets/textures/asteroid-0.png"), LayerType.LAYER_1_BEHIND);
		addToLayer(new ImageActor("assets/textures/asteroid-0.png"), LayerType.LAYER_0_BEHIND);
		addToLayer(new ImageActor("assets/textures/asteroid-0.png"), LayerType.LAYER_0_BEFORE);
		addToLayer(new ImageActor("assets/textures/asteroid-0.png"), LayerType.LAYER_1_BEFORE);
	}
}
