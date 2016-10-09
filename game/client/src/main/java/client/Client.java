package client;

import dto.CredentialsDTO;
import dto.DTO;
import dto.MessageDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Client {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    @Deprecated
    void communicate(String host, int port) throws IOException, ClassNotFoundException {
        connect(host, port);
        System.out.println("Connected");

        send(new CredentialsDTO.Builder("John", "Johnd").build());
        MessageDTO messageDTO = (MessageDTO) receive();
        System.out.println(messageDTO.getText());

        send(new CredentialsDTO.Builder("John", "Johnd").build());
        messageDTO = (MessageDTO) receive();
        System.out.println(messageDTO.getText());

        send(new CredentialsDTO.Builder("John", "Johnm").build());
        messageDTO = (MessageDTO) receive();
        System.out.println(messageDTO.getText());

        logout();
        disconnect();
    }

    DTO receive() throws IOException, ClassNotFoundException {
        return (DTO) inputStream.readObject();
    }

    void send(DTO data) throws IOException {
        outputStream.writeObject(data);
    }

    void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    void disconnect() throws IOException {
        socket.close();
        inputStream.close();
        outputStream.close();
    }

    private void logout() throws IOException {
        MessageDTO logout = new MessageDTO(MessageDTO.Command.LOGOUT);
        send(logout);
    }

    boolean isSocketConnected() {
        return socket != null && socket.isConnected();
    }
}