package game.services;

import game.model.domain.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

public class UserService implements UserServiceInterface {
    private static final String USERS_DIR = "users/";
    private static final String USER_FILE_EXTENSION = ".usr";

    @Override
    public User login(String login, String password) throws UserDontExistsException, WrongPasswordException {
        if (!doUserExists(login)) {
            throw new UserDontExistsException();
        }
        try {
            return loadUser(login, password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            throw new ErrorWhileHandlingUserException(e);
        }
    }

    @Override
    public User register(String login, String password) throws UserAlreadyExistsException {
        try{
            if(doUserExists(login)){
               throw new UserAlreadyExistsException();
            }
            long id = getUserCount() + 1; //TODO: make smarter id generation
            User user = new User(id, login);
            saveUser(user, password);
            return user;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | UnsupportedEncodingException | FileNotFoundException e) {
            throw new ErrorWhileHandlingUserException(e);
        }

    }

    private void saveUser(User user, String password) throws FileNotFoundException, UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException {
        prepareDir();
        PrintWriter writer = new PrintWriter(USERS_DIR +user.getName()+ USER_FILE_EXTENSION, "UTF-8");
        writer.println(user.getId());
        writer.println(user.getName());
        writer.println(hashPassword(password));
        writer.close();
    }

    private boolean prepareDir() {
        File f = new File(USERS_DIR);
        return f.isDirectory() || f.mkdir();
    }

    private User loadUser(String login, String password) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, WrongPasswordException {
        String userFileDir = USERS_DIR + login + USER_FILE_EXTENSION;
        List<String> lines = readFile(userFileDir);
        if(!hashPassword(password).equals(lines.get(2))){
            throw new WrongPasswordException();
        }
        return new User(Long.parseLong(lines.get(0)), lines.get(1));
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

    private String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = new byte[16];
        Random random = new Random(1);
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(hash);
    }

    private boolean doUserExists(String login){
        File f = new File(USERS_DIR+login+USER_FILE_EXTENSION);
        return f.exists();
    }

    private int getUserCount(){
        return new File(USERS_DIR).list().length;
    }


    public static class ErrorWhileHandlingUserException extends RuntimeException{
        public ErrorWhileHandlingUserException(Exception e) {
            super(e);
        }
    }
}
