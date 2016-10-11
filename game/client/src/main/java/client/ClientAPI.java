package client;


import client.listeners.Listener;
import client.listeners.LoginListener;
import client.listeners.PlayerListListener;
import client.listeners.SuccessListener;
import dto.CredentialsDTO;
import dto.MessageDTO;

import java.io.IOException;

import static dto.MessageDTO.Command.HEARTBEAT;
import static dto.MessageDTO.Command.PLAYERLIST;

public class ClientAPI {
    private Client client = new Client();


    public void connect(String host, int port) {
        try {
            client.connect(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean disconnect() {
        try {
            client.disconnect();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void isConnected(SuccessListener listener) {
        if (!client.isSocketConnected()) {
            listener.onError();
        } else {
            try {
                client.registerListener(listener);
                client.send(new MessageDTO(HEARTBEAT)); //TODO: comes there when server thread fails on exception before instead of go to !client.isSocketConnected()
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registerListener(Listener listener) {
        client.registerListener(listener);
    }

    public void unregisterListener(Listener listener) {
        client.unregisterListener(listener);
    }

    public void login(String name, String password, final LoginListener listener) {
        CredentialsDTO credentials = new CredentialsDTO.Builder(name, password).build();
        try {
            client.registerListener(new SuccessListener() {
                @Override
                public void onSuccess() {
                    client.registerListener(listener);
                }

                @Override
                public void onError() {
                    listener.handlePlayer(null);
                }
            });
            client.send(credentials);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getConnectedPlayers(PlayerListListener listener) {
        client.registerListener(listener);
        promptForConnectedPlayers();
    }

    public void promptForConnectedPlayers() {
        try {
            client.send(new MessageDTO(PLAYERLIST));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
