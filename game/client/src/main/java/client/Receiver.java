package client;

import client.listeners.Listener;
import dto.DTO;
import dto.MessageDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static dto.MessageDTO.Command.DISCONNECTED;

class Receiver extends Thread {
    private boolean running = false;
    private final ObjectInputStream inputStream;
    private final Runnable onDisconnectedMessage;
    private final List<Listener> listeners = Collections.synchronizedList(new ArrayList<Listener>());

    public Receiver(ObjectInputStream inputStream, Runnable onDisconnectedMessage) {
        this.inputStream = inputStream;
        this.onDisconnectedMessage = onDisconnectedMessage;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                DTO dto = (DTO) inputStream.readObject();
                int handledBy = handle(dto);
                ////////////////////////DEBUG/////////////////////////
                System.out.println("Message received: " + dto.getClass().getSimpleName() + ", id:"+dto.getConversationId());
                if (MessageDTO.class.isAssignableFrom(dto.getClass())) {
                    System.out.println(((MessageDTO) dto).getCommand() + " " + ((MessageDTO) dto).getText());
                }
                if(handledBy > 0) System.out.println("DTO handled by "+handledBy);
                else System.out.println("DTO unhandled");
                //////////////////////////////////////////////////////
                checkForDisconnect(dto);
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Stopping receiver.");
        onDisconnectedMessage.run();
    }

    private void checkForDisconnect(DTO dto) {
        if(MessageDTO.class.isAssignableFrom(dto.getClass()) && ((MessageDTO)dto).getCommand() == DISCONNECTED){
            System.out.println("DISSCONNECTED BY SERVER");
            running = false;
        }
    }

    public void registerListener(Listener listener) {
        synchronized (inputStream) {
            System.out.println("Registering: "+listener.getClass().getName());
            listeners.add(listener);
        }
    }

    public void unregisterListener(Listener listener) {
        synchronized (inputStream) {
            System.out.println("Unregistering: "+listener.getClass().getName());
            listeners.remove(listener);
        }
    }

    public List<Listener> getListeners() {
        return new ArrayList<Listener>(listeners);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private int handle(DTO dto) {
        int handledBy = 0;
        synchronized (listeners) {
            for (int i = 0; i < listeners.size(); i++) {
                Listener listener = listeners.get(i);
                if (listener.getHandledType() == dto.getClass()) {
                    boolean handled = listener.handle(dto);
                    if (handled) {
                        handledBy++;
                        if (listener.oneTimeOnly()) {
                            listeners.remove(listener);
                            i--;
                        }
                    }
                }
            }
        }
        return handledBy;
    }

}
