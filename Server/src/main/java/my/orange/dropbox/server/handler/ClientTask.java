package my.orange.dropbox.server.handler;

import my.orange.authorization.AuthorizationService;
import my.orange.authorization.DBAuthorization;
import my.orange.authorization.Status;
import my.orange.dropbox.common.Command;
import my.orange.dropbox.common.Message;
import my.orange.dropbox.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import static my.orange.dropbox.common.Command.*;

public class ClientTask implements Runnable {

    private static AuthorizationService authorizationService = new DBAuthorization();

    private SocketChannel channel;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    ClientTask(SelectionKey key) throws IOException {
        channel = (SocketChannel) key.channel();
        channel.configureBlocking(true);
        input = new ObjectInputStream(Channels.newInputStream(channel));
        output = new ObjectOutputStream(Channels.newOutputStream(channel));
    }

    @Override
    public void run() {
        try {
            Message message = (Message) input.readObject();
            switch (message.getCommand()) {

                case LOGIN:
                    output.writeObject(
                            new Message()
                            .setCommand(statusToCommand(authenticate(message.getUser())))
                    ); break;

                case REGISTER:
                    output.writeObject(
                            new Message()
                            .setCommand(statusToCommand(register(message.getUser())))
                    ); break;

                    //TODO File commands
                case PUT: break;
                case GET: break;
                case DELETE: break;

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        return authorizationService.authenticate(user.getLogin(), user.getPassword());
    }

    private Status register(User user) {
        return authorizationService.register(user.getLogin(), user.getPassword());
    }
}
