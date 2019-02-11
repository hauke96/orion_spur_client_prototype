package orion_spur.common.service;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public interface ICurrentWorldService
{
	boolean hasWorld();
	
	World getWorld();
	
	float meterPerPixel();
	
	Body createBody(BodyDef def);
	
	void simulate(float delta);
	
	void dispose();
}
