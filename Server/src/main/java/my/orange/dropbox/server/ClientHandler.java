package my.orange.dropbox.server;

import my.orange.authorization.AuthorizationService;
import my.orange.authorization.DBAuthorization;
import my.orange.authorization.Status;
import my.orange.dropbox.common.Command;
import my.orange.dropbox.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static my.orange.dropbox.server.LogManager.log;

public class ClientHandler implements Runnable {

    private static AuthorizationService authorizationService = new DBAuthorization();
    private static FileManager fileManager = new FileManager();

    private Server server;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private User user;

    ClientHandler(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream());
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                switch ((Command) input.readObject()) {

                    case LOGIN:
                        authenticate((User) input.readObject());
                        break;

                    case REGISTER:
                        register((User) input.readObject());
                        break;

                }
            } catch (IOException | ClassNotFoundException e) {
                log(e);
            }
        }
        while (!Thread.interrupted()) {
            //TODO file exchange
        }
        if (Thread.interrupted()) close();
    }

    private void authenticate(User user) throws IOException {
        Status status = authorizationService.authenticate(user.getLogin(), user.getPassword());
        if (status == Status.LOGIN_SUCCESS) {
            this.user = user;
            server.add(this);
        }
        output.writeObject(status);
    }
    private void register(User user) throws IOException {
        Status status = authorizationService.register(user.getLogin(), user.getPassword());
        if (status == Status.REGISTRATION_SUCCESS) {
            this.user = user;
            fileManager.addFolder(user);
            server.add(this);
        }
        output.writeObject(status);
    }

    protected void close() {
        server.remove(this);
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                log(e);
            }
        }
        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                log(e);
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                log(e);
            }
        }
    }
}
