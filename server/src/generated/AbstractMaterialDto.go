package generated

type AbstractMaterialDto struct {
	ID  IDDto `json:"ID"`
}

func NewAbstractMaterialDto(iD IDDto)AbstractMaterialDto{
	return AbstractMaterialDto{ID: iD}
}

func (i AbstractMaterialDto) GetID() IDDto {
	return i.ID
}


