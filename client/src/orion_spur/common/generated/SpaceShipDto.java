package orion_spur.common.generated;

import java.util.List;

public class SpaceShipDto 
{
	private RemoteObjectDto Base;
	private float Acceleration;
	private float MaxSpeed;
	private float RotationSpeed;

	public SpaceShipDto(){}

	public SpaceShipDto(RemoteObjectDto Base, float Acceleration, float MaxSpeed, float RotationSpeed)
	{
		this.Base = Base;
		this.Acceleration = Acceleration;
		this.MaxSpeed = MaxSpeed;
		this.RotationSpeed = RotationSpeed;
	}

	public RemoteObjectDto getBase()
	{
		return Base;
	}

	public float getAcceleration()
	{
		return Acceleration;
	}

	public float getMaxSpeed()
	{
		return MaxSpeed;
	}

	public float getRotationSpeed()
	{
		return RotationSpeed;
	}
}


