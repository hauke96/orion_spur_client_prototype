package orion_spur.common.material;

import java.util.List;

public class AbstractMaterialDto 
{
	private IDDto ID;

	public AbstractMaterialDto(){}

	public AbstractMaterialDto(IDDto ID)
	{
		this.ID = ID;
	}

	public IDDto getID()
	{
		return ID;
	}
}


