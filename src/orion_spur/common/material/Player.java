package orion_spur.common.material;

import java.util.List;

public class Player 
{
	private String Name;
	private Coordinate X;
	private Coordinate Y;

	public Player(){}

	public Player(String Name, Coordinate X, Coordinate Y)
	{
		this.Name = Name;
		this.X = X;
		this.Y = Y;
	}

	public String getName()
	{
		return Name;
	}

	public Coordinate getX()
	{
		return X;
	}

	public Coordinate getY()
	{
		return Y;
	}
}


