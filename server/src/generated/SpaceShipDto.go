package generated

type SpaceShipDto struct {
	Base  RemoteObjectDto `json:"Base"`
	Acceleration  float32 `json:"Acceleration"`
	MaxSpeed  float32 `json:"MaxSpeed"`
	RotationSpeed  float32 `json:"RotationSpeed"`
}

func NewSpaceShipDto(base RemoteObjectDto, acceleration float32, maxSpeed float32, rotationSpeed float32)SpaceShipDto{
	return SpaceShipDto{Base: base, Acceleration: acceleration, MaxSpeed: maxSpeed, RotationSpeed: rotationSpeed}
}

func (b SpaceShipDto) GetBase() RemoteObjectDto {
	return b.Base
}

func (a SpaceShipDto) GetAcceleration() float32 {
	return a.Acceleration
}

func (m SpaceShipDto) GetMaxSpeed() float32 {
	return m.MaxSpeed
}

func (r SpaceShipDto) GetRotationSpeed() float32 {
	return r.RotationSpeed
}


