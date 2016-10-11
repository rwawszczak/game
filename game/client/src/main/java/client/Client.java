package client;

import client.listeners.Listener;
import dto.CredentialsDTO;
import dto.DTO;
import dto.MessageDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.List;

class Client {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Receiver receiver;

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

    public void registerListener(Listener listener) {
        receiver.registerListener(listener);
    }

    public void unregisterListener(Listener listener) {
        receiver.unregisterListener(listener);
    }

    public List<Listener> getListeners() {
        return receiver.getListeners();
    }

    @Deprecated
    DTO receive() throws IOException, ClassNotFoundException {
        return (DTO) inputStream.readObject();
    }

    void send(DTO data) throws IOException {
        outputStream.writeObject(data);
    }

    void connect(String host, int port) throws IOException {
        try {
            socket = new Socket(host, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            receiver = new Receiver(inputStream, new OnDisconnectedMessage());
            receiver.start();
        } catch (ConnectException e){
            e.printStackTrace();
        }
    }

    void disconnect() throws IOException {
        outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.DISCONNECTED).build());
        closeConnection();
    }

    private void closeConnection() throws IOException {
        receiver.setRunning(false);
        socket.close();
        inputStream.close();
        outputStream.close();
    }

    private void logout() throws IOException {
        MessageDTO logout = new MessageDTO(MessageDTO.Command.LOGOUT);
        send(logout);
    }

    boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    private class OnDisconnectedMessage implements Runnable{
        @Override
        public void run() {
            try {
                closeConnection();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}