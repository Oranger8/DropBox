package my.orange.dropbox.server.handler;

import my.orange.authorization.AuthorizationService;
import my.orange.authorization.Status;
import my.orange.authorization.impl.DBAuthorization;
import my.orange.dropbox.common.Command;
import my.orange.dropbox.common.Message;
import my.orange.dropbox.common.User;
import my.orange.dropbox.server.util.LogManager;

import java.io.*;
import java.net.Socket;

import static my.orange.dropbox.common.Command.*;

public class ClientTask implements Runnable {

    private static AuthorizationService authorizationService = new DBAuthorization();
    private static FileManager fileManager = new FileManager();

    private LogManager logger;

    private Socket client;
    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput;
    private BufferedInputStream bufferedInput;
    private BufferedOutputStream bufferedOutput;

    public ClientTask(Socket socket) throws IOException {
        client = socket;
        objectInput = new ObjectInputStream(client.getInputStream());
    }

    @Override
    public void run() {
        try {
            logger = LogManager.getLogger();
            Message message = (Message) objectInput.readObject();
            switch (message.getCommand()) {

                case LOGIN:
                    objectOutput = new ObjectOutputStream(client.getOutputStream());
                    objectOutput.writeObject(new Message()
                            .setCommand(statusToCommand(authenticate(message.getUser()))));
                    break;

                case REGISTER:
                    objectOutput = new ObjectOutputStream(client.getOutputStream());
                    objectOutput.writeObject(new Message()
                            .setCommand(statusToCommand(register(message.getUser()))));
                    break;

                case LIST:
                    objectOutput = new ObjectOutputStream(client.getOutputStream());
                    objectOutput.writeObject(new Message()
                            .setFileList(fileManager.getFileList(message.getUser())));
                    break;

                case GET:
                    if (authenticate(message.getUser()) == Status.LOGIN_SUCCESS) {
                        fileManager.upload(message.getUser(), message.getFile(), client.getOutputStream());
                    }
                    break;

                case PUT:
                    if (authenticate(message.getUser()) == Status.LOGIN_SUCCESS) {
                        fileManager.download(message.getUser(), message.getFile(), client.getInputStream());
                    }
                    break;

                case DELETE:
                    if (authenticate(message.getUser()) == Status.LOGIN_SUCCESS) {
                        fileManager.delete(message.getUser(), message.getFile());
                        objectOutput.writeObject(new Message()
                                .setCommand(AUTH_SUCCESS)
                                .setFileList(fileManager.getFileList(message.getUser())));
                    } else {
                        objectOutput.writeObject(new Message().setCommand(LOGIN_INCORRECT));
                    }
                    break;

            }
        } catch (IOException | ClassNotFoundException e) {
            logger.log("Client connection reset", e);
        } finally {
            close();
        }
    }

    private Command statusToCommand(Status status) {
        switch (status) {
            case LOGIN_SUCCESS: return AUTH_SUCCESS;
            case REGISTRATION_SUCCESS: return AUTH_SUCCESS;
            case LOGIN_INCORRECT: return LOGIN_INCORRECT;
            case PASSWORD_INCORRECT: return PASSWORD_INCORRECT;
            case LOGIN_BUSY: return LOGIN_BUSY;
        }
        return null;
    }

    private Status authenticate(User user) {
        Status status = authorizationService.authenticate(user.getLogin(), user.getPassword());
        if (status == Status.LOGIN_SUCCESS) fileManager.addFolder(user);
        return status;
    }

    private Status register(User user) {
        Status status = authorizationService.authenticate(user.getLogin(), user.getPassword());
        if (status == Status.REGISTRATION_SUCCESS) fileManager.addFolder(user);
        return status;
    }

    private void close() {
        if (objectInput != null) {
            try {
                objectInput.close();
            } catch (IOException e) {
                logger.log("", e);
            }
        }
        if (objectOutput != null) {
            try {
                objectOutput.close();
            } catch (IOException e) {
                logger.log("", e);
            }
        }
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                logger.log("", e);
            }
        }
    }
}
