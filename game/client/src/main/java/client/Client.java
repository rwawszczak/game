package client;

import dto.CredentialsDTO;
import dto.DTO;
import dto.MessageDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Client {
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;

    @Deprecated
    public void communicate(String host, int port) throws IOException, ClassNotFoundException {
        connect(host, port);
        System.out.println("Connected");

        send(new CredentialsDTO("John", "Johnd"));
        MessageDTO messageDTO = (MessageDTO) receive();
        System.out.println(messageDTO.getText());

        send(new CredentialsDTO("John", "Johnd"));
        messageDTO = (MessageDTO) receive();
        System.out.println(messageDTO.getText());

        send(new CredentialsDTO("John", "Johnm"));
        messageDTO = (MessageDTO) receive();
        System.out.println(messageDTO.getText());

        logout();
        disconnect();
    }

    public DTO receive() throws IOException, ClassNotFoundException {
        return (DTO) inputStream.readObject();
    }

    public void send(DTO data) throws IOException {
        outputStream.writeObject(data);
    }

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void disconnect() throws IOException {
        socket.close();
        inputStream.close();
        outputStream.close();
    }

    private void logout() throws IOException {
        MessageDTO logout = new MessageDTO(MessageDTO.Command.LOGOUT);
        send(logout);
    }

    public boolean isSocketConnected() {
        return socket != null && socket.isConnected();
    }
}