package orion_spur.remoteObjects.material;

import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.domainvalue.Position;
import orion_spur.common.generated.RemoteObjectDto;
import orion_spur.common.generated.VectorDto;

public class RemoteObjectDtoConverter
{
	public RemoteObject convert(RemoteObjectDto dto)
	{
		Contract.NotNull(dto);
		
		VectorDto vectorDto = dto.getMovementVector();
		Vector2 movementVector = new Vector2(vectorDto.getX(), vectorDto.getY());
		
		Position position =
		        Position.create(dto.getX().getLightYears(), dto.getY().getLightYears(), dto.getX().getMeters(), dto.getY().getMeters());
		
		RemoteObject result =
		        new RemoteObject(dto.getName(), movementVector, dto.getAssetFile(), position, dto.getRotation());
		
		Contract.NotNull(result);
		return result;
	}
}
