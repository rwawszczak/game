package client;


import client.listeners.Listener;
import client.listeners.LoginListener;
import client.listeners.SuccessListener;
import client.model.domain.User;
import dto.*;
import dto.battle.BattleInvitationDTO;
import dto.battle.BattleInvitationResponseDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static dto.MessageDTO.Command.HEARTBEAT;
import static dto.MessageDTO.Command.USERLIST;

public class ClientAPI {
    private Client client = new Client();
    private long nextConversationId = 1;


    public void connect(String host, int port) {
        try {
            client.connect(host, port);
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
            long conversationId = nextConversationId++;
            try {
                listener.setConversationId(conversationId);
                client.registerListener(listener);
                client.send(
                        new MessageDTO.Builder(HEARTBEAT)
                                .withConversationId(conversationId)
                                .build()
                );
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
                    listener.handleUser(null);
                }
            });
            client.send(credentials);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPerformOnDisconnectAction(Runnable action) {
        client.setPerformOnDisconnectAction(action);
    }

    public void sendChatMessage(long to, String message) {
        try {
            client.send(
                    new ChatMessageDTO.Builder(to, message)
                            .build()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void promptForConnectedUsers() {
        try {
            client.send(new MessageDTO.Builder(USERLIST).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void promptForBattle(List<User> users) {
        try {
            List<UserDTO> userDtos = new ArrayList<>();
            for(User user : users){
                userDtos.add(new UserDTO.Builder(user.getId(), user.getName()).build());
            }
            client.send(
                    new BattleInvitationDTO.Builder(userDtos).build()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptBattleInvitation(long battleId){
        sendBattleInvitationResponse(battleId, BattleInvitationResponseDTO.Response.ACCEPTED);
    }

    public void declineBattleInvitation(long battleId){
        sendBattleInvitationResponse(battleId, BattleInvitationResponseDTO.Response.DECLINED);
    }

    private void sendBattleInvitationResponse(long battleId, BattleInvitationResponseDTO.Response response){
        try {
            client.send(
                    new BattleInvitationResponseDTO.Builder(battleId)
                            .withResponse(response)
                            .build()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
