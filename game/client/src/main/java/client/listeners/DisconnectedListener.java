package client.listeners;

import dto.MessageDTO;

public abstract class DisconnectedListener extends Listener<MessageDTO> {
    private boolean consumed = false;

    public abstract void onDisconnected();

    @Override
    public final Class<MessageDTO> getHandledType() {
        return MessageDTO.class;
    }

    @Override
    public final boolean handle(MessageDTO dto) {
        if (dto.getCommand() == MessageDTO.Command.DISCONNECTED) {
            consumed = true;
            onDisconnected();
            return true;
        }
        return false;
    }

    @Override
    public final boolean oneTimeOnly() {
        return true;
    }
}
