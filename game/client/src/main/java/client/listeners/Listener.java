package client.listeners;

import dto.DTO;

public abstract class Listener<T extends DTO> {
    public abstract Class<T> getHandledType();
    public abstract boolean handle(T dto);
    public abstract boolean oneTimeOnly();
}
