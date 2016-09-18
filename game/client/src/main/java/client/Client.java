package client;

import dto.CredentialsDTO;
import dto.TextMessageDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    private String host = "localHost";
    private int port = 4445;

    public void communicate() {

        try {
            Socket socket = new Socket(host, port);
            System.out.println("Connected");
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(new CredentialsDTO("John", "John"));


            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            TextMessageDTO textMessageDTO = (TextMessageDTO) inputStream.readObject();
            System.out.println(textMessageDTO.getMessage());


            TextMessageDTO logout = new TextMessageDTO("Logout");
            logout.setCode(-1);
            outputStream.writeObject(logout);

            socket.close();
            inputStream.close();
            outputStream.close();

        } catch (SocketException se) {
            se.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
