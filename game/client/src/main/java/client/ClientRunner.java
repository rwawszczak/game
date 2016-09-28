package client;

import java.io.IOException;

public class ClientRunner {
    private static String host = "localHost";
    private static int port = 4445;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();
        client.communicate(host, port);
    }
}
