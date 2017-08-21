package game.services;

import game.model.domain.Character;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

public class CharacterService implements CharacterServiceInterface {
    private static final String CHARACTERS_DIR = "characters/";
    private static final String CHARACTER_FILE_EXTENSION = ".chr";

    @Override
    public Character load(long id) {
        if (!doCharacterExists(id)) {
            throw new CharacterDontExistsException();
        }
        try {
            return loadCharacter(id);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            throw new ErrorWhileHandlingCharacterException(e);
        }
    }

    @Override
    public Character create(Character character) {
        try {
            if (doCharacterExists(character.getId())) {
                throw new CharacterAlreadyExistsException();
            }
            long id = getCharacterCount() + 1; //TODO: make smarter id generation
            Character characterWithId = new Character.Builder(character).withCharacterId(id).build();
            saveCharacter(characterWithId);
            return characterWithId;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | UnsupportedEncodingException | FileNotFoundException e) {
            throw new ErrorWhileHandlingCharacterException(e);
        }
    }

    @Override
    public Character update(Character character) {
        return null;
    }

    @Override
    public Character delete(Character character) {
        return null;
    }

    private void saveCharacter(Character character) throws FileNotFoundException, UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException {
        prepareDir();
        PrintWriter writer = new PrintWriter(CHARACTERS_DIR + character.getId() + CHARACTER_FILE_EXTENSION, "UTF-8");
        writer.println(character.getId());
        writer.println(character.getPlayerId());
        writer.println(character.getName());
        writer.println(character.getHp());
        writer.println(character.getMana());
        writer.close();
    }

    private boolean prepareDir() {
        File f = new File(CHARACTERS_DIR);
        return f.isDirectory() || f.mkdir();
    }

    private Character loadCharacter(long id) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        String characterFileDir = CHARACTERS_DIR + id + CHARACTER_FILE_EXTENSION;
        List<String> lines = readFile(characterFileDir);
        Character.Builder characterBuilder = new Character.Builder();
        characterBuilder.withCharacterId(Long.parseLong(lines.get(0)));
        characterBuilder.withPlayerId(Long.parseLong(lines.get(1)));
        characterBuilder.withName(lines.get(2));
        characterBuilder.withHp(Integer.parseInt(lines.get(3)));
        characterBuilder.withMana(Integer.parseInt(lines.get(4)));
        return characterBuilder.build();
    }


    private List<String> readFile(String pathname) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(pathname)));

        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();
        return lines;
    }


    private boolean doCharacterExists(long id) {
        File f = new File(CHARACTERS_DIR + id + CHARACTER_FILE_EXTENSION);
        return f.exists();
    }

    private int getCharacterCount() {
        return new File(CHARACTERS_DIR).list().length;
    }


    public static class ErrorWhileHandlingCharacterException extends RuntimeException {
        public ErrorWhileHandlingCharacterException(Exception e) {
            super(e);
        }
    }

    private static class CharacterDontExistsException extends RuntimeException {
    }

    private static class CharacterAlreadyExistsException extends RuntimeException {
    }
}
