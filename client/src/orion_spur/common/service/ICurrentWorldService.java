package orion_spur.common.service;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public interface ICurrentWorldService
{
	boolean hasWorld();
	
	Body createBody(BodyDef def);
	
	void simulate(float delta);
	
	void dispose();
}
