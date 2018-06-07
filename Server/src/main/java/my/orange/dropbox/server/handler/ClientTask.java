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

                case GET:
                    if (authenticate(message.getUser()) == Status.LOGIN_SUCCESS) {
                        objectOutput = new ObjectOutputStream(client.getOutputStream());
                        objectOutput.writeObject(new Message()
                                .setCommand(AUTH_SUCCESS));
                        fileManager.upload(message.getUser(), message.getFile(), client.getOutputStream());
                    } else {
                        objectOutput.writeObject(new Message().setCommand(LOGIN_INCORRECT));
                    }
                    break;

                case PUT:
                    if (authenticate(message.getUser()) == Status.LOGIN_SUCCESS) {
                        objectOutput = new ObjectOutputStream(client.getOutputStream());
                        objectOutput.writeObject(new Message()
                                .setCommand(AUTH_SUCCESS));
                        fileManager.download(message.getUser(), message.getFile(), client.getInputStream());
                        objectOutput.writeObject(new Message()
                                .setCommand(AUTH_SUCCESS)
                                .setFileList(fileManager.getFileList(message.getUser())));
                    } else {
                        objectOutput.writeObject(new Message().setCommand(LOGIN_INCORRECT));
                    }
                    break;

                case DELETE:
                    objectOutput = new ObjectOutputStream(client.getOutputStream());
                    if (authenticate(message.getUser()) == Status.LOGIN_SUCCESS) {
                        fileManager.delete(message.getUser(), message.getFile());
                        objectOutput.writeObject(new Message()
                                .setCommand(AUTH_SUCCESS)
                                .setFileList(fileManager.getFileList(message.getUser())));
                    } else {
                        objectOutput.writeObject(new Message().setCommand(LOGIN_INCORRECT));
                    }
                    break;

                default:
                    authAction(message);

            }
        } catch (IOException | ClassNotFoundException e) {
            logger.log("Client connection reset", e);
        } finally {
            close();
        }
    }

    private void authAction(Message message) throws IOException {
        Status status = null;
        if (message.getCommand() == LOGIN) status = authenticate(message.getUser());
        if (message.getCommand() == REGISTER) status = register(message.getUser());
        if (status == null) return;
        Command answer = Command.getByString(status.getTitle());
        objectOutput = new ObjectOutputStream(client.getOutputStream());
        if (answer == AUTH_SUCCESS) {
            objectOutput.writeObject(new Message()
                    .setCommand(answer)
                    .setFileList(fileManager.getFileList(message.getUser())));
        } else {
            objectOutput.writeObject(new Message()
                    .setCommand(answer));
        }
    }

    private Status authenticate(User user) {
        Status status = authorizationService.authenticate(user.getLogin(), user.getPassword());
        if (status == Status.LOGIN_SUCCESS) fileManager.addFolder(user);
        return status;
    }

    private Status register(User user) {
        Status status = authorizationService.register(user.getLogin(), user.getPassword());
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
                objectOutput.flush();
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
