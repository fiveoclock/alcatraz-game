# alcatraz-game

Uni project for the distributed systems lecture. The task was to design and program client and server for a  multiplayer game that can be played over the network.

Requirements for the server:
  - the server should allow registration and de-registration of players.
  - as soon as there are enogh players the game should start
  - there should be multiple servers that replicate the registrated clients.
  - the servers should use the Spread Framework to synchronise with each other

Requirements for the client/player:
 - the clients have to connect to the server using Java RMI
 - it must be possible to start a game with 2, 3 or 4 players
 - as soon as enough players are registered the game should start
 - as soon as the game has started all game moves have to be exchanged directly between the clients (the server should not be needed anymore) using RMI again
 - clients should not crash if the network gets disconnected. Upon the network is available again the game should go on.

