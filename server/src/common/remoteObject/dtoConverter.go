package remoteObject

import (
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
