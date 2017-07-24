package orion_spur.common.converter;

import com.badlogic.gdx.math.Vector2;

import orion_spur.common.domainvalue.Position;

public interface ICoordinateConverter
{
	/**
	 * Converts a screen position (pixel) into the position in the level (meter).
	 * 
	 * @param position
	 *            Position on the screen in pixel.
	 * @return The position on the level in meter.
	 */
	Vector2 screenToWorld(Vector2 position);
	
	/**
	 * Converts the position in the level (=world position) into the position in the universe.
	 * 
	 * @param position
	 *            The position in the level in meters.
	 * @return Position in the universe in universe-position.
	 */
	Position worldToUniverse(Vector2 position);
	
	/**
	 * Converts the position in the level in meters into the screen position in pixel.
	 * 
	 * @param position
	 *            Position in the level in meters.
	 * @return Position on the screen in pixel.
	 */
	Vector2 worldToScreen(Vector2 position);
	
	/**
	 * Converts the universe-position into the position in the level in meters.
	 * 
	 * @param position
	 *            The universe-position.
	 * @return The position in the level in meters.
	 * @throws Exception
	 *             Will be thrown when the position to check is too far away from the actual level position.
	 */
	Vector2 universeToWorld(Position position) throws Exception;
}
