package pa;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Client cl = new Client("127.0.0.1", 8_001);
        cl.run();
    }
}
