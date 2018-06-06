package my.orange.authorization;

public enum Status {

    LOGIN_SUCCESS("Login success"),
    LOGIN_INCORRECT("Login incorrect"),
    PASSWORD_INCORRECT("Password incorrect"),
    REGISTRATION_SUCCESS("Registration success"),
    LOGIN_BUSY("Login busy");

    private String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
