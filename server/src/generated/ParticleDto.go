package generated

type ParticleDto struct {
	Base  RemoteObjectDto `json:"Base"`
}

func NewParticleDto(base RemoteObjectDto)ParticleDto{
	return ParticleDto{Base: base}
}

func (b ParticleDto) GetBase() RemoteObjectDto {
	return b.Base
}


