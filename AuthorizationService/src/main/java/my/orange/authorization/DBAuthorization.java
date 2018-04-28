package my.orange.authorization;

import javax.persistence.EntityManager;

public class DBAuthorization implements AuthorizationService {

    private EntityManager em;

    public DBAuthorization() {
        em = PersistenceManager.INSTANCE.getEntityManager();
    }

    @Override
    public Status authenticate(String login, int password) {
        //TODO authentication
        return null;
    }

    @Override
    public Status register(String login, int password) {
        //TODO registration
        return null;
    }

    @Override
    public void close() {
        em.close();
        PersistenceManager.INSTANCE.close();
    }
}
