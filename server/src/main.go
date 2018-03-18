package main

import (
	"background"
	"common"
	commonRemoteObject "common/remoteObject"
	"encoding/json"
	"fmt"
	"generated"
	"logger"
	"login"
	"net/http"
	"os"
	"player"
	"remoteObject"

	"goms4go"

	"github.com/gorilla/mux"
)

var playerService *player.PlayerService
var remoteObjectService *remoteObject.RemoteObjectService
var loginService *login.LoginService

const gomsServerAddress string = "localhost"
const gomsServerPort string = "55545"

func main() {
	logger.DebugMode = true
	logger.Info("Create Services")

	messagingService, err := goms4go.Connect(gomsServerAddress, gomsServerPort)
	if err != nil {
		logger.Error("Could not connect to goms-server unter " + gomsServerAddress + ":" + gomsServerPort + ".")
		logger.Error("Error is: " + err.Error())
		os.Exit(1)
	}

	playerDao := &player.LocalPlayerDao{}
	playerDao.Init()

	playerService = &player.PlayerService{}
	playerService.Init(messagingService, playerDao)

	remoteObjectDao := &remoteObject.LocalRemoteObjectDao{}
	remoteObjectDao.Init(playerDao)

	remoteObjectService = &remoteObject.RemoteObjectService{}
	remoteObjectService.Init(remoteObjectDao)

	loginService = &login.LoginService{}

	go background.Run(playerService, loginService)

	router := mux.NewRouter()

	router.HandleFunc("/player/{playerName}", createPlayerHandler).Methods(http.MethodPost)
	router.HandleFunc("/player/{playerName}", getPlayerHandler).Methods(http.MethodGet)
	router.HandleFunc("/player/{playerName}", updatePlayerHandler).Methods(http.MethodPut)
	router.HandleFunc("/login/{playerName}", loginPlayer).Methods(http.MethodPost)
	router.HandleFunc("/login/{playerName}", logoutPlayer).Methods(http.MethodDelete)
	router.HandleFunc("/objects", getAllRemoteObjects).Methods(http.MethodGet)

	logger.Info("Registered handler functions. Start serving...")

	http.ListenAndServe(":8080", router)
}

func createPlayerHandler(w http.ResponseWriter, r *http.Request) {
	logger.Info("Called createPlayer")

	pathVariables := mux.Vars(r)
	playerName := pathVariables["playerName"]

	err := playerService.CreatePlayer(playerName)

	if err != nil {
		logger.Error(err.Error())
		w.WriteHeader(409)
		fmt.Fprintf(w, err.Error())
	}
}

func getPlayerHandler(w http.ResponseWriter, r *http.Request) {
	logger.Info("Called getPlayer")

	pathVariables := mux.Vars(r)
	playerName := pathVariables["playerName"]

	player, err := playerService.Get(playerName)

	if err == nil {
		remoteObjectDto := commonRemoteObject.ToDto(player.RemoteObject)

		spaceShipDto := generated.NewSpaceShipDto(*remoteObjectDto, player.Acceleration, player.MaxSpeed, player.RotationSpeed)

		encoder := json.NewEncoder(w)
		encoder.Encode(spaceShipDto)
	} else {
		logger.Error(err.Error())
		w.WriteHeader(404)
		fmt.Fprintf(w, err.Error())
	}
}

func updatePlayerHandler(w http.ResponseWriter, r *http.Request) {
	logger.Info("Called updatePlayer")

	player := &generated.RemoteObjectDto{}
	json.NewDecoder(r.Body).Decode(player)

	logger.Debug(fmt.Sprintf("%v", player.MovementVector))

	x := common.Coordinate{LightYears: player.GetX().LightYears, Meters: player.GetX().Meters}
	y := common.Coordinate{LightYears: player.GetY().LightYears, Meters: player.GetY().Meters}

	movementVector := common.Vector{X: player.GetMovementVector().GetX(), Y: player.GetMovementVector().GetY()}

	err := playerService.UpdatePosition(player.GetName(), x, y, movementVector, player.GetRotation())

	if err != nil {
		logger.Error(err.Error())
		w.WriteHeader(409)
		fmt.Fprintf(w, err.Error())
	}
}

func getAllPlayerHandler(w http.ResponseWriter, r *http.Request) {
	logger.Info("Called getAll for player")

	players := playerService.GetAll()

	encoder := json.NewEncoder(w)
	encoder.Encode(players)
}

func loginPlayer(w http.ResponseWriter, r *http.Request) {
	logger.Info("Called loginPlayer")

	pathVariables := mux.Vars(r)
	playerName := pathVariables["playerName"]

	err := loginService.Login(playerName)

	if err != nil {
		logger.Error(err.Error())
		w.WriteHeader(409)
		fmt.Fprintf(w, err.Error())
	}
}

func logoutPlayer(w http.ResponseWriter, r *http.Request) {
	logger.Info("Called logoutPlayer")

	pathVariables := mux.Vars(r)
	playerName := pathVariables["playerName"]

	err := loginService.Logout(playerName)

	if err != nil {
		logger.Error(err.Error())
		w.WriteHeader(409)
		fmt.Fprintf(w, err.Error())
	}
}

func getAllRemoteObjects(w http.ResponseWriter, r *http.Request) {
	logger.Info("Called getAll remote objects")

	array := remoteObjectService.GetAll()

	dtos := make([]generated.RemoteObjectDto, len(array), len(array))

	for i, v := range array {
		dtos[i] = *commonRemoteObject.ToDto(*v)
	}

	objects := generated.NewRemoteObjectListDto(dtos)

	encoder := json.NewEncoder(w)
	encoder.Encode(objects)
}
