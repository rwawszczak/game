package client;


import client.model.assemblers.PlayerAssembler;
import client.model.domain.Player;
import dto.CredentialsDTO;
import dto.MessageDTO;
import dto.PlayerDTO;
import dto.PlayersDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
        if (!client.isSocketConnected()) {
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

    public Player login(String name, String password) {
        CredentialsDTO credentials = new CredentialsDTO.Builder(name, password).build();
        try {
            client.send(credentials);
            if (isSuccess()) {
                PlayerDTO playerDTO = (PlayerDTO) client.receive();
                Player player = PlayerAssembler.toDomainObject(playerDTO);
                return player;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isSuccess() throws IOException, ClassNotFoundException {
        MessageDTO receive = (MessageDTO) client.receive();
        return receive.getCommand() == SUCCESS;
    }

    public List<Player> getConnectedPlayers() {
        try {
            client.send(new MessageDTO(PLAYERLIST));
            PlayersDTO players = (PlayersDTO) client.receive();
            return PlayerAssembler.toDomainObjects(players);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
