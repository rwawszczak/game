package game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    private int connections = 0;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void communicate() {
        BattleInspector inspector = new BattleInspector();
        try {
            System.out.println("Server started.");
            ServerSocket serverSocket = new ServerSocket(port);
            inspector.start();

            while (true) {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, ++connections);
                ServerData.getThreads().add(serverThread);
                serverThread.start();
            }

        } catch (SocketException se) {
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inspector.stopGracefully();
        }
    }
}