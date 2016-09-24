package game.server;

public class ServerRunner {


    public static void main(String[] args) {
        Server server = new Server(4445);
        server.communicate();
    }
}
