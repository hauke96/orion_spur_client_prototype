package generated

type IDDto struct {
	Value  string `json:"Value"`
}

func NewIDDto(value string)IDDto{
	return IDDto{Value: value}
}

func (v IDDto) GetValue() string {
	return v.Value
}


