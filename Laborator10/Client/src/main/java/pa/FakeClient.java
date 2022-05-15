package pa;

import com.github.javafaker.Faker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakeClient {
    private Socket socket;
    private PrintWriter pipeOut;
    private BufferedReader pipeIn;

    public FakeClient(String address, int port) throws IOException {
        this.socket = new Socket(address, port);
        this.pipeIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.pipeOut = new PrintWriter(socket.getOutputStream(), true);
    }

    public void doCommand(String command) throws IOException {
        pipeOut.println(command);
        System.out.println(pipeIn.readLine());
    }

    public void run() throws IOException {
        int userCount = 40;
        int friendsMin = 5;
        int friendsMax = 20;
        List<String> users = new ArrayList<>();
        Faker faker = new Faker();

        //Populate
        for(int i = 0; i < userCount ; ++i) {
            String name = faker.name().firstName();
            users.add(name);
            doCommand("register " + name);
        }
        Random rng = new Random();

        //Make friendships
        for(var user : users) {
            int friendsCount = rng.nextInt(friendsMin, friendsMax);
            String friends = "";
            for(int i = 0; i < friendsCount; ++i) {
                friends += users.get(rng.nextInt(0, users.size() - 1)) + " ";
            }
            doCommand("login " + user);
            doCommand("friend " + friends);
        }
        //Export
        doCommand("export fake.svg");
        doCommand("exit");
        socket.close();
    }
}
