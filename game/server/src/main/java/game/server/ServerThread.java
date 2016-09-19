package game.server;

import dto.DTO;
import game.server.commands.CommandExecutor;
import game.server.session.SessionObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private int connectionId;
    private Socket socket;

    public ServerThread(Socket socket, int connectionId) {
        this.socket = socket;
        this.connectionId = connectionId;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("Connected (connection " + connectionId + ")");
            SessionObject sessionObject = new SessionObject();
            CommandExecutor commandExecutor = new CommandExecutor();
            while (sessionObject.isOpened()) {
                DTO data = (DTO) inStream.readObject();
                commandExecutor.execute(data, outputStream, sessionObject);
            }
            System.out.println("Closing connection " + connectionId);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
