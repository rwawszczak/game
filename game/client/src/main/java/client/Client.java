package client;

import client.listeners.Listener;
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
    private Runnable disconnectAction;

    public void registerListener(Listener listener) {
        receiver.registerListener(listener);
    }

    public void unregisterListener(Listener listener) {
        receiver.unregisterListener(listener);
    }

    public void setPerformOnDisconnectAction(Runnable action) {
        this.disconnectAction = action;
    }

    public List<Listener> getListeners() {
        return receiver.getListeners();
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
        } catch (ConnectException e) {
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
        MessageDTO logout = new MessageDTO.Builder(MessageDTO.Command.LOGOUT).build();
        send(logout);
    }

    boolean isSocketConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    private class OnDisconnectedMessage implements Runnable {
        @Override
        public void run() {
            try {
                closeConnection();
                if (disconnectAction != null) {
                    disconnectAction.run();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}