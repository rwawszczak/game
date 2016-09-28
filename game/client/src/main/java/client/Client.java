package client;

import dto.CredentialsDTO;
import dto.DTO;
import dto.MessageDTO;
import dto.TextMessageDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;

    @Deprecated
    public void communicate(String host, int port) throws IOException, ClassNotFoundException {
        connect(host, port);
        System.out.println("Connected");
        send(new CredentialsDTO("John", "Johnd"));


        TextMessageDTO textMessageDTO = (TextMessageDTO) receive();
        System.out.println(textMessageDTO.getMessage());

        logoutAndDisconnect();
    }

    private DTO receive() throws IOException, ClassNotFoundException {
        return (DTO)inputStream.readObject();
    }

    public void send(DTO data) throws IOException {
        outputStream.writeObject(data);
    }

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void logoutAndDisconnect() throws IOException {
        MessageDTO logout = new MessageDTO(MessageDTO.Command.LOGOUT);
        send(logout);
        socket.close();
        inputStream.close();
        outputStream.close();
    }
}
