package orion_spur.common.converter;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import juard.contract.Contract;
import orion_spur.common.domainvalue.Position;

public class CoordinateConverterImpl implements ICoordinateConverter
{
	private Viewport	_viewport;
	private Position	_levelPosition;
	private boolean		_isInitialized;
	
	@Override
	public void initialize(Viewport viewport, Position levelPosition)
	{
		Contract.NotNull(viewport);
		Contract.NotNull(levelPosition);
		
		_viewport = viewport;
		_levelPosition = levelPosition;
		_isInitialized = true;
	}
	
	@Override
	public boolean isInitialized()
	{
		return _isInitialized;
	}
	
	@Override
	public Vector2 screenToWorld(Vector2 position)
	{
		Contract.NotNull(position);
		Contract.Satisfy(isInitialized());
		
		return _viewport.unproject(position);
	}
	
	@Override
	public Position worldToUniverse(Vector2 position)
	{
		Contract.NotNull(position);
		Contract.Satisfy(isInitialized());
		
		Position positionInWorld = Position.create(0, 0, (long) position.x, (long) position.y);
		
		return _levelPosition.add(positionInWorld);
	}
	
	@Override
	public Vector2 worldToScreen(Vector2 position)
	{
		Contract.NotNull(position);
		Contract.Satisfy(isInitialized());
		
		return _viewport.project(position);
	}
	
	@Override
	public Vector2 universeToWorld(Position position) throws RuntimeException
	{
		Contract.NotNull(position);
		Contract.Satisfy(isInitialized());
		
		Position positionInLevel = position.subtract(_levelPosition);
		
		if (positionInLevel.getX().getLightYear() != 0 || positionInLevel.getY().getLightYear() != 0)
		{
			throw new RuntimeException(
			    "Position too far away from level (max 1 Ly). Distance is: " + positionInLevel.toString());
		}
		
		return new Vector2(positionInLevel.getX().getCentimeter(), positionInLevel.getY().getCentimeter());
	}
}
