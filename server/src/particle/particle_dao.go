package particle

import (
	"errors"
)

type LocalParticleDao struct {
	particles map[string]*Particle
}

func (dao *LocalParticleDao) Init() {
	dao.particles = make(map[string]*Particle)
}

func (dao *LocalParticleDao) Get(name string) (*Particle, error) {
	particle := dao.particles[name]

	var err error

	if particle == nil {
		err = errors.New("Particle " + name + " does not exist")
	}

	return particle, err
}

func (dao *LocalParticleDao) Add(particle *Particle) error {
	_, err := dao.Get(particle.Name)

	if err != nil {
		dao.particles[particle.Name] = particle

		return nil
	}

	return errors.New("Particle " + particle.Name + " already exists")
}

func (dao *LocalParticleDao) GetAll() []*Particle {
	result := make([]*Particle, len(dao.particles))

	i := 0
	for _, v := range dao.particles {
		if v != nil {
			result[i] = v
			i++
		}
	}

	return result
}
