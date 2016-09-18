package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port = 4445;
    private int connections = 0;

    public void communicate() {
        try {
            System.out.println("Server started.");
            ServerSocket serverSocket = new ServerSocket(port);
            List<ServerThread> serverThreads = new ArrayList<ServerThread>();

            while (true) {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, ++connections);
                serverThread.start();
                serverThreads.add(serverThread);
            }

        } catch (SocketException se) {
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}