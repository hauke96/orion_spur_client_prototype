package particle

import (
	"common"
	commonRemoteObject "common/remoteObject"
	"testing"
)

func createDao() *LocalParticleDao {
	dao := &LocalParticleDao{}
	dao.Init()

	return dao
}

func createService() *ParticleService {
	dao := &LocalParticleDao{}
	dao.Init()

	service := &ParticleService{}
	service.Init(dao)

	return service
}

func createParticle(name string) *Particle {
	remoteObject := &commonRemoteObject.RemoteObject{
		Name:      name,
		AssetFile: "",
		MovementVector: common.Vector{
			X: 0,
			Y: 1,
		},
		X: common.Coordinate{
			LightYears: 2,
			Meters:     3,
		},
		Y: common.Coordinate{
			LightYears: 4,
			Meters:     5,
		},
		Rotation: 6.0,
	}

	return &Particle{
		RemoteObject: *remoteObject,
	}
}

func equals(a, b *Particle) bool {
	return a.Name == b.Name &&
		a.MovementVector.X == b.MovementVector.X &&
		a.MovementVector.Y == b.MovementVector.Y &&
		a.AssetFile == b.AssetFile &&
		a.Rotation == b.Rotation &&
		a.X.LightYears == b.X.LightYears &&
		a.X.Meters == b.X.Meters &&
		a.Y.LightYears == b.Y.LightYears &&
		a.Y.Meters == b.Y.Meters
}

func TestDaoAddWorks(t *testing.T) {
	// Arrange
	dao := createDao()
	particleA := createParticle("A")
	particleB := createParticle("B")

	// Act
	dao.Add(particleA)
	dao.Add(particleB)

	// Assert
	allParticles := dao.GetAll()
	if len(allParticles) != 2 {
		t.Fail()
	}

	actualA, err := dao.Get("A")
	if err != nil {
		t.Error("err should not be nil")
	}
	if !equals(actualA, particleA) {
		t.Error("particle A from dao is not equal with original")
	}

	actualB, err := dao.Get("B")
	if err != nil {
		t.Error("err should not be nil")
	}
	if !equals(actualB, particleB) {
		t.Error("particle B from dao is not equal with original")
	}
}

func TestDaoGetAllWorks(t *testing.T) {
	// Arrange
	dao := createDao()
	particleA := createParticle("A")
	particleB := createParticle("B")

	// Act
	dao.Add(particleA)
	dao.Add(particleB)

	// Assert
	allParticles := dao.GetAll()
	if len(allParticles) != 2 {
		t.Fail()
	}

	actualFirst := allParticles[0]
	// First entry is the "A" particle
	if actualFirst.Name == "A" {
		if !equals(actualFirst, particleA) {
			t.Error("particle A from dao is not equal with original")
		}
		// First entry is the "B" particle
	} else if actualFirst.Name == "B" {
		if !equals(actualFirst, particleB) {
			t.Error("particle B from dao is not equal with original")
		}
		// Something's wrong
	} else {
		t.Error("Nothing or an unknown entry found")
	}
}

func TestServiceAddWorks(t *testing.T) {
	// Arrange
	service := createService()
	particleA := createParticle("A")
	particleB := createParticle("B")

	// Act
	service.Add(particleA)
	service.Add(particleB)

	// Assert
	allParticles := service.GetAll()
	if len(allParticles) != 2 {
		t.Fail()
	}

	actualA, err := service.Get("A")
	if err != nil {
		t.Error("err should not be nil")
	}
	if !equals(actualA, particleA) {
		t.Error("particle A from dao is not equal with original")
	}

	actualB, err := service.Get("B")
	if err != nil {
		t.Error("err should not be nil")
	}
	if !equals(actualB, particleB) {
		t.Error("particle B from dao is not equal with original")
	}
}

func TestServiceGetAllWorks(t *testing.T) {
	// Arrange
	service := createService()
	particleA := createParticle("A")
	particleB := createParticle("B")

	// Act
	service.Add(particleA)
	service.Add(particleB)

	// Assert
	allParticles := service.GetAll()
	if len(allParticles) != 2 {
		t.Fail()
	}

	actualFirst := allParticles[0]
	// First entry is the "A" particle
	if actualFirst.Name == "A" {
		if !equals(actualFirst, particleA) {
			t.Error("particle A from dao is not equal with original")
		}
		// First entry is the "B" particle
	} else if actualFirst.Name == "B" {
		if !equals(actualFirst, particleB) {
			t.Error("particle B from dao is not equal with original")
		}
		// Something's wrong
	} else {
		t.Error("Nothing or an unknown entry found")
	}
}
