package generated

type RemoteObjectDto struct {
	Name  string `json:"Name"`
	AssetFile  string `json:"AssetFile"`
	MovementVector  VectorDto `json:"MovementVector"`
	X  CoordinateDto `json:"X"`
	Y  CoordinateDto `json:"Y"`
	Rotation  float32 `json:"Rotation"`
}

func NewRemoteObjectDto(name string, assetFile string, movementVector VectorDto, x CoordinateDto, y CoordinateDto, rotation float32)RemoteObjectDto{
	return RemoteObjectDto{Name: name, AssetFile: assetFile, MovementVector: movementVector, X: x, Y: y, Rotation: rotation}
}

func (n RemoteObjectDto) GetName() string {
	return n.Name
}

func (a RemoteObjectDto) GetAssetFile() string {
	return a.AssetFile
}

func (m RemoteObjectDto) GetMovementVector() VectorDto {
	return m.MovementVector
}

func (x RemoteObjectDto) GetX() CoordinateDto {
	return x.X
}

func (y RemoteObjectDto) GetY() CoordinateDto {
	return y.Y
}

func (r RemoteObjectDto) GetRotation() float32 {
	return r.Rotation
}


