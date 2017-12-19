package generated

type CoordinateDto struct {
	LightYears  int64 `json:"LightYears"`
	Meters  int64 `json:"Meters"`
}

func NewCoordinateDto(lightYears int64, meters int64)CoordinateDto{
	return CoordinateDto{LightYears: lightYears, Meters: meters}
}

func (l CoordinateDto) GetLightYears() int64 {
	return l.LightYears
}

func (m CoordinateDto) GetMeters() int64 {
	return m.Meters
}


