import pa.FakeClient;

import java.io.IOException;


public class testExport {

    public static void main(String[] args) throws IOException {
        FakeClient cl = new FakeClient("127.0.0.1", 8_001);
        cl.run();
    }
}
