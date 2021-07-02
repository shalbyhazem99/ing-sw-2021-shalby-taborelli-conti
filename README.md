# Prova finale di Ingegneria del Software


<p align="center">
  <img src="http://craniointernational.com/wp-content/uploads/2019/06/Masters-Of-Renaissance_scatola_ENG-e1560246260470.png">
</p>

The project consists in the implementation of the [Masters of the Renaissance](http://craniointernational.com/products/masters-of-renaissance/) Card Game.

__Masters of the Renaissance__ is a new engine building game that recreates the atmosphere of Lorenzo il Magnifico with simple rules and a limited duration. On your turn you may acquire a development card, take resources from the market or activate development cards on your board. Each development card allow you to transform resources by moving them from your limited depots to the unlimited bank space; in this way you may accumulate many resource to acquire the most powerful cards. As in Lorenzo you will have to follow the requests of your leaders to have access to their interested powers|

## Implementation Details
The project consists in the implementation of a distributed system with a single server and a multiple client (one for each player).
The development of the code had been done following the MVC (Model-View-Controller) pattern.
## Documentation

### UML

### JavaDoc

### Coverage Report
|Element|Class coverage|Methods coverage|Line coverage|
|-------|--------------|----------------|-------------|
|Model|100% (30/30)|90% (237/262)|80% (1104/1374)

### Libraries and Plugins
|Libraries/Plugins|Description|
|---------------|-----------|
|__Maven__|Build automation tool.|
|__JavaFx__|Software platform for creating and delivering desktop applications.|
|__Gson__|Java library to serialize and deserialize Java objects to (and from) JSON.|
|__JUnit__|Unit testing framework.|

## Functionality
### 
- __Complete rule__
- __CLI__
- __GUI__
- __socket__
### Advance Functionality
- __Multiple matches__ : The server is able to handle more than one match at the same time.
- __Local match__ :  The user is able to play a Solo Match without the connection to the server.
- __Resilience to disconnections__ : The Player is able to resume the game after disconnections issues.

## HOW TO EXCUTE JAR

The execution of the code is made using the following command

```
java -jar masters-of-renaissance.jar [--type <type>] [--address <address>] [--port <port>]  [--gui] [--cli] [--local]
```
__Parameters__
- `--type <type>` allow to select the type of service you are asking `s` (server) or `c` (client). Default is `s`.
- `--address <address>` allow to insert the address the client want to connect to (useful only for client). Default is `127.0.0.1`.
- `--port <port>` allow to insert the port for the server or for the client. If a port is not provided the server will run on the first free.
- `--gui` request a client running with gui.
- `--cli` request a client running with cli.
- `--local` request a client which run a the game in solo modo.

__DEFAULT COMMAND__

Execute the server:
```
java -jar masters-of-renaissance.jar --type s --port 12345
```
Execute the client in distributed way with GUI:
```
java -jar masters-of-renaissance.jar --type c --address 127.0.0.1 --port 12345  --gui
```
Execute the client in local way with GUI:
```
java -jar masters-of-renaissance.jar --type c --gui --local
```
Execute the client in distributed way with CLI:
```
java -jar masters-of-renaissance.jar --type c --address 127.0.0.1 --port 12345  --cli
```
Execute the client in local way with CLI:
```
java -jar masters-of-renaissance.jar --type c --cli --local
```
## Developers
- [__Conti Alessando__](https://github.com/alessandroconti99)
- [__Shalby Hazem__](https://github.com/shalbyhazem99)
- [__Taborelli Stefano__](https://github.com/stefanotaborelli)
