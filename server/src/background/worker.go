package background

import (
	"common"
	"login"
	"player"
	"time"
)

func Run(playerService *player.PlayerService, loginService *login.LoginService) {
	x := common.Coordinate{LightYears: 0, Meters: 0}
	y := common.Coordinate{LightYears: -23013, Meters: -646735535105623500}
	// TODO use channels to exit this worker
	for true {
		time.Sleep(time.Second * 3)

		for _, v := range playerService.GetAll() {
			if !loginService.IsLoggedIn(v.Name) {
				playerService.UpdatePosition(v.Name, x, y, v.MovementVector, v.Rotation)
			}
		}
	}
}
