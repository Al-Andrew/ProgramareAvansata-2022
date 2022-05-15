package pa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

class ClientThread extends Thread {
    private Socket socket = null;
    private Server parentServer = null;
    private boolean running;
    private boolean logged;
    private String username;

    public ClientThread(Socket socket, Server parent) {
        this.socket = socket;
        this.parentServer = parent;
    }

    public void run() {
        running = true;
        try {
            // Get the request from the input stream: client → server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            // Send the response to the oputput stream: server → client
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (running) {
                String request = in.readLine();

                if(request == null) {
                    break; // Client unexpected exit
                }

                System.out.println("Got request: " + request);
                String raspuns = doCommand(request);

                out.print(raspuns);
                out.println("");
            }

        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) {
                System.err.println(e);
            }
            if(this.logged) {
                parentServer.getSocialNetwork().logoutUser(this.username);
            }

        }
    }


    public String doCommand(String request) {
        String response = null;
        String[] requestTokens = request.trim().toLowerCase(Locale.ROOT).split(" ");

        try {
            switch (requestTokens[0]) {
                case "stop": {
                    this.parentServer.stop();
                    this.running = false;
                    response = "The server has been closed and your connection has been severed";
                    break;
                }
                case "exit" : {
                    this.running = false;
                    response = "Your connection has been severed";
                    break;
                }
                case "register" : {
                    if(requestTokens.length != 2) {
                        throw new Exception("Wrong number of arguments for command");
                    }
                    String user = requestTokens[1];
                    parentServer.getSocialNetwork().registerUser(user);
                    response = "User " + user + " registered successfully";
                    break;
                }
                case "login" : {
                    if(requestTokens.length != 2) {
                        throw new Exception("Wrong number of arguments for command");
                    }
                    parentServer.getSocialNetwork().loginUser(requestTokens[1]);
                    this.username = requestTokens[1];
                    this.logged = true;
                    response = "You are now logged in as " + this.username;
                    break;
                }
                case "friend" : {
                    if(requestTokens.length == 1) {
                        throw new Exception("Wrong number of arguments for command");
                    }

                    boolean first = true;
                    for (String friend : requestTokens) {
                        if (first) {
                            first = false;
                            continue;
                        }
                        parentServer.getSocialNetwork().makeFriends(this.username, friend);
                    }
                    response = "You have new friends";
                    break;
                }
                case "send" : {
                    if(requestTokens.length == 1) {
                        throw new Exception("Wrong number of arguments for command");
                    }

                    String message = Arrays.stream(requestTokens).skip(1).collect(Collectors.joining(" "));
                    parentServer.getSocialNetwork().sendMessage(this.username, message);
                    response = "Message sent";
                    break;
                }
                case "read" : {
                    if(requestTokens.length != 1) {
                        throw new Exception("Wrong number of arguments for command");
                    }

                    List<SocialNetwork.Message> inbox = parentServer.getSocialNetwork().getMessages(this.username);
                    StringBuilder bld = new StringBuilder();
                    for (var msg : inbox) {
                        bld.append("[From: " + msg.from + " | ");
                        bld.append(msg.message + "]");
                    }
                    if(bld.isEmpty()) {
                        response = "Your inbox is empty";
                    }else {
                        response = bld.toString();
                    }
                    break;
                }
                case "export" : {
                    if(requestTokens.length != 2) {
                        throw new Exception("Wrong number of arguments for command");
                    }
                    String file = requestTokens[1];
                    SocialNetworkVisualizer.exportSVG(parentServer.getSocialNetwork(), file);
                    response = "Saved as svg";
                    break;
                }


                default: {
                    response = "Your command is not recognized.";
                    break;
                }
            }
        } catch (Exception ex) {
            response = ex.toString();
        }

        return response;
    }
}
