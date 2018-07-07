package remoteObject

import (
	"common"
	"generated"
)

func ToDto(player RemoteObject) *generated.RemoteObjectDto {
	movementVector := generated.NewVectorDto(player.MovementVector.X, player.MovementVector.Y)

	x := generated.NewCoordinateDto(player.X.LightYears, player.X.Meters)
	y := generated.NewCoordinateDto(player.Y.LightYears, player.Y.Meters)

	dto := generated.RemoteObjectDto{
		Name:           player.Name,
		AssetFile:      player.AssetFile,
		MovementVector: movementVector,
		X:              x,
		Y:              y,
		Rotation:       player.Rotation,
	}

	return &dto
}

func FromDto(dto *generated.RemoteObjectDto) *RemoteObject {
	name := dto.Name

	x := common.Coordinate{LightYears: dto.GetX().LightYears, Meters: dto.GetX().Meters}
	y := common.Coordinate{LightYears: dto.GetY().LightYears, Meters: dto.GetY().Meters}

	movementVector := common.Vector{
		X: dto.GetMovementVector().GetX(),
		Y: dto.GetMovementVector().GetY(),
	}

	rotation := dto.Rotation

	assetPath := dto.AssetFile

	remoteObject := &RemoteObject{
		Name:           name,
		AssetFile:      assetPath,
		MovementVector: movementVector,
		X:              x,
		Y:              y,
		Rotation:       rotation,
	}

	return remoteObject
}
