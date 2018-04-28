package my.orange.authorization;

public interface AuthorizationService {

    Status authenticate(String login, int password);

    Status register(String login, int password);

    void close();
}
