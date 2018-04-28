package my.orange.dropbox.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

import static my.orange.dropbox.server.Configuration.*;
import static my.orange.dropbox.server.LogManager.log;

public class Server {

    private ServerSocket serverSocket;
    private Vector<ClientHandler> clients;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            log(e);
        }
        clients = new Vector<>();
    }

    private void start() {
        while (true) {
            try {
                new ClientHandler(this, serverSocket.accept());
            } catch (IOException e) {
                log(e);
            }
        }
    }

    public void add(ClientHandler client) {
        clients.add(client);
    }

    public void remove(ClientHandler client) {
        clients.remove(client);
    }

    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.start();
    }
}
