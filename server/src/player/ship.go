package player

import (
	"common/remoteObject"
	"generated"
)

type SpaceShip struct {
	remoteObject.RemoteObject
	Acceleration  float32
	MaxSpeed      float32
	RotationSpeed float32
}

func ToDto(ship SpaceShip) *generated.SpaceShipDto {
	remoteObjectDto := remoteObject.ToDto(ship.RemoteObject)

	spaceShipDto := generated.NewSpaceShipDto(*remoteObjectDto, ship.Acceleration, ship.MaxSpeed, ship.RotationSpeed)

	return &spaceShipDto
}
