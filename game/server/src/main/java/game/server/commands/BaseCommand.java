package game.server.commands;

import dto.DTO;
import game.server.session.SessionObject;

import java.io.ObjectOutputStream;

public interface BaseCommand<T extends DTO> {
    void execute(T data, ObjectOutputStream outputStream, SessionObject sessionObject);
}
