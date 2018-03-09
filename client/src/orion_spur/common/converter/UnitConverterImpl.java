package orion_spur.common.converter;

import juard.contract.Contract;

public class UnitConverterImpl implements IUnitConverter
{
	private float	_unitsPerPixel;
	private boolean	_isInitialized;
	
	@Override
	public void initialize(float unitsPerPixel)
	{
		Contract.Satisfy(unitsPerPixel >= 0);
		
		_unitsPerPixel = unitsPerPixel;
		_isInitialized = true;
	}
	
	@Override
	public boolean isInitialized()
	{
		return _isInitialized;
	}
	
	@Override
	public float convertToWorld(float value)
	{
		Contract.Satisfy(isInitialized());
		
		return value * _unitsPerPixel;
	}
	
	@Override
	public float convertFromWorld(float value)
	{
		Contract.Satisfy(isInitialized());
		
		return value / _unitsPerPixel;
	}
}
