package player

import (
	"errors"
	"fmt"
	"orion_spur_server_prototype/src/logger"
	"orion_spur_server_prototype/src/material"
)

type LocalPlayerDao struct {
	players map[string]*material.RemoteObjectDto
}

func (dao *LocalPlayerDao) Init() {
	dao.players = make(map[string]*material.RemoteObjectDto)
}

func (dao *LocalPlayerDao) CreatePlayer(player *material.RemoteObjectDto) error {
	_, err := dao.GetPlayer(player.Name)

	if err != nil {
		dao.players[player.Name] = player
		return nil
	}

	return errors.New("Player " + player.Name + " already exists")
}

func (dao *LocalPlayerDao) GetPlayer(name string) (*material.RemoteObjectDto, error) {
	player := dao.players[name]

	var err error

	if player == nil {
		err = errors.New("Player " + name + " does not exist")
	}

	return player, err
}

func (dao *LocalPlayerDao) GetAllPlayer() []*material.RemoteObjectDto {
	result := make([]*material.RemoteObjectDto, len(dao.players))

	i := 0
	for _, v := range dao.players {
		if v != nil {
			result[i] = v
			i++
		}
	}

	return result
}

func (dao *LocalPlayerDao) SetPlayerPosition(name string, newXPosition material.CoordinateDto, newYPosition material.CoordinateDto, newMovementVector material.VectorDto, rotation float32) error {
	player, err := dao.GetPlayer(name)

	if err != nil {
		return err
	}

	logger.Debug(fmt.Sprintf("%v", player))

	player.X = material.CoordinateDto{LightYears: newXPosition.LightYears, Meters: newXPosition.Meters}
	player.Y = material.CoordinateDto{LightYears: newYPosition.LightYears, Meters: newYPosition.Meters}
	player.Rotation = rotation
	player.MovementVector = newMovementVector

	p2, _ := dao.GetPlayer(name)
	logger.Debug(fmt.Sprintf("%v", p2))

	return nil
}
