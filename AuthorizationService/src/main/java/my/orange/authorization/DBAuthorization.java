package my.orange.authorization;

import my.orange.authorization.entity.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import java.util.List;

import static my.orange.authorization.Status.*;

public class DBAuthorization implements AuthorizationService {

    private EntityManager em;

    public DBAuthorization() {
        em = PersistenceManager.INSTANCE.getEntityManager();
    }

    @Override
    public Status authenticate(String login, int password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        List<Integer> result = em.createQuery(
                "SELECT u.password " +
                        "FROM User u " +
                        "WHERE u.login = :login " +
                        "AND u.password = :password", Integer.class)
                .setParameter("login", user.getLogin())
                .setParameter("password", user.getPassword())
                .getResultList();
        if (result.isEmpty()) return LOGIN_INCORRECT;
        if (!result.get(0).equals(user.getPassword())) return PASSWORD_INCORRECT;
        return LOGIN_SUCCESS;
    }

    @Override
    public Status register(String login, int password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        try {
            em.persist(user);
        } catch (EntityExistsException e) {
            return LOGIN_BUSY;
        }
        return REGISTRATION_SUCCESS;
    }

    @Override
    public void close() {
        em.close();
        PersistenceManager.INSTANCE.close();
    }
}
