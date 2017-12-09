This is a prototype of an 2D space RPG.
# Orion Spur client
Written in Java using the following frameworks:
* [LibGDX](https://github.com/libgdx/libgdx) (game engine)
* [json2code](https://github.com/hauke96/json2code) (generation of classes used by client and server)
* [juard](https://github.com/hauke96/juard) (code contracts, dependency injection, events)


# Orion Spur Server
## Start
Just run
```
go run ./server/src/main.go
```
Maybe you need to install the gorilla router package by executing
```
go get github.com/gorilla/mux
```

# goMS Server
The [go messaging service (goMS)](https://github.com/go-messaging-service/goms-server) sends updates to the client (e.g. when a player joined).
