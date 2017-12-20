package main

import (
	"encoding/json"
	"fmt"
	"generated"
	"logger"
	"net/http"
	"os"
	"player"
	"remoteObject"

	"goms4go"

	"github.com/gorilla/mux"
)

var playerService *player.PlayerService
var remoteObjectService *remoteObject.RemoteObjectService

const gomsServerAddress string = "localhost"
const gomsServerPort string = "55545"

func main() {
	logger.Info("Create Services")

	messagingService, err := goms4go.Connect(gomsServerAddress, gomsServerPort)
	if err != nil {
		logger.Error("Could not connect to goms-server unter " + gomsServerAddress + ":" + gomsServerPort + ".")
		os.Exit(1)
	}

	playerDao := &player.LocalPlayerDao{}
	playerDao.Init()

	playerService = &player.PlayerService{}
	playerService.Init(messagingService, playerDao)

	//	playerService.CreatePlayer("1")
	//	playerService.CreatePlayer("2")

	//	p1, _ := playerService.GetPlayer("1")
	//	playerService.SetPlayerPosition("1", generated.CoordinateDto{LightYears: 0, Meters: 650}, p1.GetY())

	remoteObjectDao := &remoteObject.LocalRemoteObjectDao{}
	remoteObjectDao.Init(playerDao)

	remoteObjectService = &remoteObject.RemoteObjectService{}
	remoteObjectService.Init(remoteObjectDao)

	router := mux.NewRouter()

	router.HandleFunc("/player/{playerName}", createPlayerHandler).Methods(http.MethodPost)
	router.HandleFunc("/player/{playerName}", getPlayerHandler).Methods(http.MethodGet)
	router.HandleFunc("/player/{playerName}", updatePlayerHandler).Methods(http.MethodPut)
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

	position, err := playerService.GetPlayer(playerName)

	if err == nil {
		encoder := json.NewEncoder(w)
		encoder.Encode(position)
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

	logger.Info(fmt.Sprintf("%v", player.MovementVector))

	err := playerService.SetPlayerPosition(player.GetName(), player.GetX(), player.GetY(), player.GetMovementVector(), player.GetRotation())

	if err != nil {
		logger.Error(err.Error())
		fmt.Fprintf(w, err.Error())
		w.WriteHeader(409)
	}
}

func getAllPlayerHandler(w http.ResponseWriter, r *http.Request) {
	logger.Info("Called getAll for player")

	players := playerService.GetAllPlayer()

	encoder := json.NewEncoder(w)
	encoder.Encode(players)
}

func getAllRemoteObjects(w http.ResponseWriter, r *http.Request) {
	logger.Info("Called getAll remote objects")

	array := remoteObjectService.GetAll()

	// TODO: make converter for this
	dtos := make([]generated.RemoteObjectDto, len(*array), len(*array))

	for i, v := range *array {
		movementVector := generated.NewVectorDto(v.MovementVector.X, v.MovementVector.Y)

		x := generated.NewCoordinateDto(v.X.LightYears, v.X.Meters)
		y := generated.NewCoordinateDto(v.Y.LightYears, v.Y.Meters)

		dtos[i] = generated.RemoteObjectDto{
			Name:           v.Name,
			AssetFile:      v.AssetFile,
			MovementVector: movementVector,
			X:              x,
			Y:              y,
			Rotation:       v.Rotation,
		}
	}

	objects := generated.NewRemoteObjectListDto(dtos)

	encoder := json.NewEncoder(w)
	encoder.Encode(objects)
}
