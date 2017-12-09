package material

type PlayerDto struct {
	Name  string `json:"Name"`
	X  CoordinateDto `json:"X"`
	Y  CoordinateDto `json:"Y"`
	Rotation  float32 `json:"Rotation"`
}

func NewPlayerDto(name string, x CoordinateDto, y CoordinateDto, rotation float32)PlayerDto{
	return PlayerDto{Name: name, X: x, Y: y, Rotation: rotation}
}

func (n PlayerDto) GetName() string {
	return n.Name
}

func (x PlayerDto) GetX() CoordinateDto {
	return x.X
}

func (y PlayerDto) GetY() CoordinateDto {
	return y.Y
}

func (r PlayerDto) GetRotation() float32 {
	return r.Rotation
}


