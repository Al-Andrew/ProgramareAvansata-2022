package pa;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    // Define the port on which the server is listening
    private final int port;
    private final int timeout;
    private boolean running = true;
    private ServerSocket serverSocket = null;
    private SocialNetwork sn = new SocialNetwork();


    public Server(int port, int timeout) throws IOException {
        this.port = port;
        this.timeout = timeout;
        serverSocket = new ServerSocket(port);
        serverSocket.setReuseAddress(true);
        serverSocket.setSoTimeout(timeout);
    }

    public void start() throws IOException {
        while(running) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("Got a new client at: " + client.getInetAddress());
                (new ClientThread(client, this)).start();
            } catch (SocketTimeoutException ex) {

            };
        }
        try {
            serverSocket.close(); // or use try-with-resources
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void stop() {
        running = false;
    }

    public SocialNetwork getSocialNetwork() {
        return this.sn;
    }

}
