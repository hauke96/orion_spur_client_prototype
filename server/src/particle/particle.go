package particle

import (
	"common/remoteObject"
	"generated"
)

type Particle struct {
	remoteObject.RemoteObject
}

func ToDto(particle Particle) *generated.ParticleDto {
	remoteObjectDto := remoteObject.ToDto(particle.RemoteObject)

	particleDto := generated.NewParticleDto(*remoteObjectDto)

	return &particleDto
}
