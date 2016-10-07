package client;


import dto.CredentialsDTO;
import dto.LightPlayerDTO;
import dto.MessageDTO;
import dto.PlayersDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static dto.MessageDTO.Command.*;

public class ClientAPI {
    private Client client = new Client();


    public boolean connect(String host, int port) {
        try {
            client.connect(host, port);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    public boolean isConnected() {
        if(!client.isSocketConnected()){
            return false;
        }
        try {
            client.send(new MessageDTO(HEARTBEAT));
            return isSuccess();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(String name, String password) {
        CredentialsDTO credentials = new CredentialsDTO(name, password);
        try {
            client.send(credentials);
            return isSuccess();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isSuccess() throws IOException, ClassNotFoundException {
        MessageDTO receive = (MessageDTO) client.receive();
        return receive.getCommand() == SUCCESS;
    }

    public Map<Long, String> getConnectedPlayers(){
        try {
            client.send(new MessageDTO(PLAYERLIST));
            PlayersDTO players = (PlayersDTO)client.receive();
            Map<Long, String> transformedPlayers = new HashMap<Long, String>();
            for(LightPlayerDTO player : players.getPlayers()){
                transformedPlayers.put(player.getId(), player.getName());
            }
            return transformedPlayers;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
