package orion_spur.common.converter;

public interface IUnitConverter
{
	/**
	 * Takes the value (e.g. an x-coordinate) and projects it into the world unit
	 * scale.
	 * 
	 * @param value
	 *            The value to convert.
	 * @return The value scaled into the worlds unit system.
	 */
	float convertToWorld(float value);
	
	/**
	 * Takes the world value (e.g. an x-coordinate) and projects it into the libgdx
	 * unit scale.
	 * 
	 * @param value
	 *            The value to convert.
	 * @return The value scaled from the worlds unit system.
	 */
	float convertFromWorld(float value);
}
