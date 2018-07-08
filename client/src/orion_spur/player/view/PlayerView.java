package orion_spur.player.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import juard.log.Logger;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.common.view.ImageActor;
import orion_spur.particles.material.BulletParticle;
import orion_spur.particles.service.IParticleService;
import orion_spur.player.material.SpaceShip;
import orion_spur.player.service.IPlayerService;

public class PlayerView extends ImageActor
{
	private long _lastShotTime;
	
	private IPlayerService		_playerService;
	private IParticleService	_particleService;
	
	public PlayerView(IPlayerService playerService, IUnitConverter unitConverter, IParticleService particleService, SpaceShip levelElement, Vector2 positionInLevel)
	{
		super(levelElement, 600, 600);
		
		Contract.NotNull(playerService);
		Contract.NotNull(unitConverter);
		Contract.NotNull(particleService);
		Contract.NotNull(positionInLevel);
		
		_playerService = playerService;
		_particleService = particleService;
	}
	
	@Override
	public void act(float delta)
	{
		Vector2 positionOfView = new Vector2(getX(), getY());
		Vector2 movementAdjustion = new Vector2();
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
		{
			movementAdjustion = movementAdjustion.add(getLevelElement().getAcceleration() * delta, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
		{
			movementAdjustion = movementAdjustion.add(-getLevelElement().getAcceleration() * delta, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
		{
			movementAdjustion = movementAdjustion.add(0, getLevelElement().getAcceleration() * delta);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
		{
			movementAdjustion = movementAdjustion.add(0, -getLevelElement().getAcceleration() * delta);
		}
		// TODO add number as a shot rate to space ship
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && _lastShotTime + 75 <= System.currentTimeMillis())
		{
			Vector2 vector = new Vector2(0, 1);
			vector = vector.rotate(getLevelElement().getRotation());
			vector = vector.setLength(10000);
			vector = vector.add(getLevelElement().getMovementVector());
			
			try
			{
				_particleService.add(new BulletParticle(getLevelElement().getPosition(),
				    vector,
				    getLevelElement().getRotation()));
			}
			catch (Exception e)
			{
				Logger.error("Could not save particle", e);
			}
			_lastShotTime = System.currentTimeMillis();
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.Q))
		{
			rotateBy(getLevelElement().getRotationSpeed() * delta);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E))
		{
			rotateBy(-getLevelElement().getRotationSpeed() * delta);
		}
		
		movementAdjustion.rotate(getLevelElement().getRotation());
		
		getLevelElement().accelerateShipBy(movementAdjustion);
		
		positionOfView.add(getLevelElement().getMovementVector().x * delta
		        / getScaleX(),
		    getLevelElement().getMovementVector().y * delta / getScaleY());
		
		setPosition(positionOfView.x, positionOfView.y);
	}
	
	@Override
	public void setPosition(float x, float y)
	{
		Vector2 oldPosition = getCenterWorldPosition();
		
		super.setPosition(x, y);
		
		try
		{
			_playerService.setPosition(getLevelElement(), oldPosition);
		}
		catch (Exception e)
		{
			Logger.error("Could not send updated position: ", e);
		}
	}
	
	public Vector2 getCenterWorldPosition()
	{
		return new Vector2(getX(), getY());
	}
	
	@Override
	protected SpaceShip getLevelElement()
	{
		return (SpaceShip) super.getLevelElement();
	}
}
