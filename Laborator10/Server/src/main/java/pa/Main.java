package pa;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server sv = new Server(8001, 30_000);
        sv.start();
    }
}
