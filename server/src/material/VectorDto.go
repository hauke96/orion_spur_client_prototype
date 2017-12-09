package material

type VectorDto struct {
	X  float32 `json:"X"`
	Y  float32 `json:"Y"`
}

func NewVectorDto(x float32, y float32)VectorDto{
	return VectorDto{X: x, Y: y}
}

func (x VectorDto) GetX() float32 {
	return x.X
}

func (y VectorDto) GetY() float32 {
	return y.Y
}


