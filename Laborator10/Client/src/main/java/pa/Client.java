package pa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String address;
    private int port;
    private boolean running;
    private Socket socket;
    private PrintWriter pipeOut;
    private BufferedReader pipeIn;
    private BufferedReader userIn;

    Client(String address, int port) throws IOException {
        this.address = address;
        this.port = port;
        this.socket = new Socket(address, port);
        this.pipeIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.pipeOut = new PrintWriter(socket.getOutputStream(), true);
        this.userIn = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException {
        this.running = true;

        while(running) {
            String request = userIn.readLine();
            pipeOut.println(request);
            String response = pipeIn.readLine();
            System.out.println(response);

            if(request.equals("exit") || request.equals("stop")){
                running = false;
            }

        }
        socket.close();
    }
}
