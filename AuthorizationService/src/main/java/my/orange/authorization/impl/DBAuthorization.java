package my.orange.authorization.impl;

import my.orange.authorization.AuthorizationService;
import my.orange.authorization.PersistenceManager;
import my.orange.authorization.Status;
import my.orange.authorization.entity.UserEntity;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import java.io.File;
import java.util.List;

import static my.orange.authorization.Status.*;

public class DBAuthorization implements AuthorizationService {

    private EntityManager em;

    public DBAuthorization() {
        em = PersistenceManager.INSTANCE.getEntityManager();
    }

    @Override
    public Status authenticate(String login, int password) {
        List<UserEntity> result = em.createQuery(
                "SELECT u " +
                        "FROM UserEntity u " +
                        "WHERE u.login = :login " +
                        "AND u.password = :password", UserEntity.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getResultList();
        if (result.isEmpty()) return LOGIN_INCORRECT;
        return LOGIN_SUCCESS;
    }

    @Override
    public Status register(String login, int password) {
        UserEntity user = new UserEntity();
        user.setLogin(login);
        user.setPassword(password);
        em.getTransaction().begin();
        try {
            em.persist(user);
        } catch (EntityExistsException e) {
            em.getTransaction().rollback();
            return LOGIN_BUSY;
        }
        em.getTransaction().commit();
        return REGISTRATION_SUCCESS;
    }

    @Override
    public void close() {
        em.close();
        PersistenceManager.INSTANCE.close();
    }
}
