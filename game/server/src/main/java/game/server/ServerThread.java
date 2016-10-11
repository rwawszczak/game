package game.server;

import dto.DTO;
import game.server.commands.CommandExecutor;
import game.server.session.ServerBroadcasting;
import game.server.session.SessionObject;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private int connectionId;
    private Socket socket;
    private ObjectOutputStream outputStream;

    public ServerThread(Socket socket, int connectionId) {
        this.socket = socket;
        this.connectionId = connectionId;
    }

    @Override
    public void run() {
        SessionObject sessionObject = new SessionObject();
        try {
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("Connected (connection " + connectionId + ")");
            CommandExecutor commandExecutor = new CommandExecutor();
            while (sessionObject.isOpened()) {
                DTO data = (DTO) inStream.readObject();
                commandExecutor.execute(data, outputStream, sessionObject);
            }
            System.out.println("Closing connection " + connectionId);
            closeAll(inStream, outputStream);
        } catch (EOFException e){
            System.out.println("Client terminated connection.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            cleanup(sessionObject);
            ServerBroadcasting.broadcastConnectedUsers();
        }
    }

    public void send(DTO dto) throws IOException {
        outputStream.writeObject(dto);
    }

    private void cleanup(SessionObject sessionObject) {
        ServerData.getThreads().remove(this);
        if(sessionObject.getPlayer() != null) {
            ServerData.getPlayers().remove(sessionObject.getPlayer().getId());
        }
    }

    private void closeAll(ObjectInputStream inStream, ObjectOutputStream outputStream) throws IOException {
        inStream.close();
        outputStream.close();
        socket.close();
    }
}
