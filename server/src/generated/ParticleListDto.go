package generated

type ParticleListDto struct {
	ParticleList  []ParticleDto `json:"ParticleList"`
}

func NewParticleListDto(particleList []ParticleDto)ParticleListDto{
	return ParticleListDto{ParticleList: particleList}
}

func (p ParticleListDto) GetParticleList() []ParticleDto {
	return p.ParticleList
}


