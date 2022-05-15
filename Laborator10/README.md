# Laboratorul 10


# TODO:

- [x] Compulsory (1p)
    - [x] Create the project for the server application.
    - [x] Implement the class responsible with the creation of a ServerSocket running at a specified port. The server will receive requests (commands) from clients and it will execute them.
    - [x] Create a class that will be responsible with communicating with a client Socket. The communication will be on a separate thread. If the server receives the command stop it will stop and will return to the client the respons "Server stopped", otherwise it return: "Server received the request ... ".
    - [x] Create the project for the client application.
    - [x] A client will read commands from the keyboard and it will send them to the server. The client stops when it reads from the keyboard the string "exit".
- [x] Homework (2p)
    - [x] Create an object-oriented model for your application and implement the commands.
    - [x] The command stop should "gracefully" stop the server - it will not accept new games but it will finish those in progress. When there are no more games, it will shutdown.
    - [x] Implement a timeout for a connection (a number of minutes). If the server does not receive any command from a logged in client in the specified period of time, it will terminate the connection.
    - [x] (+0.5p) Create a SVG representation of the social network, using Apache Batik, or other technology.
    - [ ] (+0.5p) Upload a HTML document containing the social network representation directly from the application to a Web server. You may use JCraft for connecting to a server using SFTP and transferring a file (or a similar solution).
- [ ] Bonus (2p)
    - [ ] Create a command that returns various properties of the social network. You may use JGraphT or other library.
    - [ ] Using a maximum network flow algorithm, determine the structural cohesion of the network. You may want to read this (combinatorial applications of network flows).
