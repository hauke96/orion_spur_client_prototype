package particle

import (
	"logger"
)

type ParticleService struct {
	dao *LocalParticleDao
}

func (service *ParticleService) Init(particleDao *LocalParticleDao) {
	service.dao = particleDao
}

func (service *ParticleService) Get(name string) (*Particle, error) {
	logger.Debug("Called GetParticle with name '" + name + "'")

	particle, err := service.dao.Get(name)

	if err != nil {
		return nil, err
	}

	return particle, nil
}

func (service *ParticleService) GetAll() []*Particle {
	logger.Debug("Called GetAllPlayer")

	list := []*Particle{}

	for _, v := range service.dao.GetAll() {

		particle, err := service.Get(v.Name)

		if err == nil {
			list = append(list, particle)
		}
	}

	return list
}

func (service *ParticleService) Add(particle *Particle) error {
	logger.Debug("Calles AddParticle")

	err := service.dao.Add(particle)

	return err
}
