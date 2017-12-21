package player

import (
	"errors"
	"fmt"
	"generated"
	"logger"
)

type LocalPlayerDao struct {
	players map[string]*generated.RemoteObjectDto
}

func (dao *LocalPlayerDao) Init() {
	dao.players = make(map[string]*generated.RemoteObjectDto)
}

func (dao *LocalPlayerDao) CreatePlayer(player *generated.RemoteObjectDto) error {
	_, err := dao.GetPlayer(player.Name)

	if err != nil {
		dao.players[player.Name] = player

		return nil
	}

	return errors.New("Player " + player.Name + " already exists")
}

func (dao *LocalPlayerDao) GetPlayer(name string) (*generated.RemoteObjectDto, error) {
	player := dao.players[name]

	var err error

	if player == nil {
		err = errors.New("Player " + name + " does not exist")
	}

	return player, err
}

func (dao *LocalPlayerDao) GetAllPlayer() []*generated.RemoteObjectDto {
	result := make([]*generated.RemoteObjectDto, len(dao.players))

	i := 0
	for _, v := range dao.players {
		if v != nil {
			result[i] = v
			i++
		}
	}

	return result
}

func (dao *LocalPlayerDao) SetPlayerPosition(name string, newXPosition generated.CoordinateDto, newYPosition generated.CoordinateDto, newMovementVector generated.VectorDto, rotation float32) error {
	player, err := dao.GetPlayer(name)

	if err != nil {
		return err
	}

	logger.Debug(fmt.Sprintf("%v", player))

	player.X = generated.CoordinateDto{LightYears: newXPosition.LightYears, Meters: newXPosition.Meters}
	player.Y = generated.CoordinateDto{LightYears: newYPosition.LightYears, Meters: newYPosition.Meters}
	player.Rotation = rotation
	player.MovementVector = newMovementVector

	p2, _ := dao.GetPlayer(name)
	logger.Debug(fmt.Sprintf("%v", p2))

	return nil
}
