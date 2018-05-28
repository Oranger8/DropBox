package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

import static my.orange.dropbox.client.Configuration.HOST;
import static my.orange.dropbox.client.Configuration.PORT;

public class AuthorizationTask implements Callable<Message> {

    private Message message;

    public AuthorizationTask(Message message) {
        this.message = message;
    }

    @Override
    public Message call() {
        Message answer = null;
        try {
            SocketChannel channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(HOST, PORT));
            ObjectInputStream input = new ObjectInputStream(Channels.newInputStream(channel));
            ObjectOutputStream output = new ObjectOutputStream(Channels.newOutputStream(channel));
            output.writeObject(message);
            answer = (Message) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //TODO close connection
        }
        return answer;
    }
}
