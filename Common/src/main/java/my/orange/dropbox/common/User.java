package my.orange.dropbox.common;

import java.io.Serializable;

public class User implements Serializable {

    private String login;
    private int password;

    public User(String login, String password) {
        this.login = login;
        this.password = password.hashCode();
    }

    public String getLogin() {
        return login;
    }

    public int getPassword() {
        return password;
    }
}
