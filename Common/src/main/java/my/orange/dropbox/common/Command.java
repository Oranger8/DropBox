package my.orange.dropbox.common;

import java.io.Serializable;

public enum Command implements Serializable {

    LOGIN("Login"),
    REGISTER("Register"),
    AUTH_SUCCESS("Auth success", "Login success", "Registration success"),
    LOGIN_INCORRECT("Login incorrect"),
    PASSWORD_INCORRECT("Password incorrect"),
    LOGIN_BUSY("Login busy"),
    PUT("Put"),
    GET("Get"),
    DELETE("Delete"),
    LIST("List");

    private String title, statuses[];

    Command(String title, String... statuses) {
        this.title = title;
        this.statuses = statuses;
    }

    public String getTitle() {
        return title;
    }

    public static Command getByString(String string) {
        for (Command c : Command.values()) {
            if (c.title.equalsIgnoreCase(string)) return c;
            for (String s : c.statuses) {
                if (s.equalsIgnoreCase(string)) return c;
            }
        }
        return null;
    }
}
