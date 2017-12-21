package player

import (
	"common"
	"common/remoteObject"
	"encoding/json"
	"errors"
	"generated"
	"goms4go"
	"logger"
)

type PlayerService struct {
	dao              *LocalPlayerDao
	messagingService *goms4go.GomsClient
}

func (service *PlayerService) Init(messagingService *goms4go.GomsClient, playerDao *LocalPlayerDao) {
	service.messagingService = messagingService
	service.dao = playerDao
}

func (service *PlayerService) CreatePlayer(name string) error {
	logger.Info("Called CreatePlayer with name '" + name + "'")

	p := &remoteObject.RemoteObject{
		//0, -23013, 600, -6467355351055975L
		X:              common.Coordinate{LightYears: 0, Meters: 600},
		Y:              common.Coordinate{LightYears: -23013, Meters: -6467355351055975},
		Name:           name,
		Rotation:       0,
		MovementVector: common.Vector{X: 0, Y: 0},
		AssetFile:      "assets/textures/spaceship.png",
	}

	movementVector := generated.NewVectorDto(p.MovementVector.X, p.MovementVector.Y)

	x := generated.NewCoordinateDto(p.X.LightYears, p.X.Meters)
	y := generated.NewCoordinateDto(p.Y.LightYears, p.Y.Meters)

	dto := generated.RemoteObjectDto{
		Name:           p.Name,
		AssetFile:      p.AssetFile,
		MovementVector: movementVector,
		X:              x,
		Y:              y,
		Rotation:       p.Rotation,
	}

	err := service.dao.CreatePlayer(&dto)

	if err == nil {
		return service.sendPlayer(p, common.PLAYER_CREATED)
	}

	return err
}

func (service *PlayerService) GetPlayer(name string) (*remoteObject.RemoteObject, error) {
	logger.Info("Called GetPlayer with name '" + name + "'")

	player, err := service.dao.GetPlayer(name)

	vec := common.Vector{
		X: player.GetMovementVector().GetX(),
		Y: player.GetMovementVector().GetY(),
	}

	x := common.Coordinate{
		LightYears: player.GetX().GetLightYears(),
		Meters:     player.GetX().GetMeters(),
	}

	y := common.Coordinate{
		LightYears: player.GetY().GetLightYears(),
		Meters:     player.GetY().GetMeters(),
	}

	object := remoteObject.RemoteObject{Name: player.GetName(), AssetFile: "assets/textures/spaceship.png", MovementVector: vec, X: x, Y: y, Rotation: player.GetRotation()}

	if err != nil {
		return nil, err
	}

	return &object, nil
}

func (service *PlayerService) GetAllPlayer() []*remoteObject.RemoteObject {
	logger.Info("Called GetAllPlayer")

	list := []*remoteObject.RemoteObject{}

	for _, v := range service.dao.GetAllPlayer() {

		player, err := service.GetPlayer(v.Name)

		if err == nil {
			list = append(list, player)
		}
	}

	return list
}

func (service *PlayerService) SetPlayerPosition(name string, x generated.CoordinateDto, y generated.CoordinateDto, movementVector generated.VectorDto, rotation float32) error {
	logger.Info("Called SetPlayerPosition with name '" + name + "'")

	p, err := service.GetPlayer(name)

	if err != nil {
		return err
	}

	err = service.dao.SetPlayerPosition(name, x, y, movementVector, rotation)

	if err == nil {
		return service.sendPlayer(p, common.PLAYER_MOVED)
	}

	return err
}

func (service *PlayerService) sendPlayer(player *remoteObject.RemoteObject, topic string) error {
	movementVector := generated.NewVectorDto(player.MovementVector.X, player.MovementVector.Y)

	x := generated.NewCoordinateDto(player.X.LightYears, player.X.Meters)
	y := generated.NewCoordinateDto(player.Y.LightYears, player.Y.Meters)

	dto := generated.RemoteObjectDto{
		Name:           player.Name,
		AssetFile:      player.AssetFile,
		MovementVector: movementVector,
		X:              x,
		Y:              y,
		Rotation:       player.Rotation,
	}

	data, err := json.Marshal(dto)

	if err != nil {
		return errors.New("Could not send message: Error while converting player into JSON: " + err.Error())
	}

	playerString := string(data)

	return service.messagingService.Send(playerString, topic)
}
