package laba17.dao.jpahibernate;

import laba17.dao.bean.User;
import laba17.interfaces.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class JpaHibernateUserDao implements UserDao {

    private static final Logger LOG = LoggerFactory.getLogger(JpaHibernateUserDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void remove(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    @Override
    public List<User> findAll() {
        String query = "from User user";
        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        return typedQuery.getResultList();
    }

    @Override
    public User findByLogin(String login) {
        String query = "FROM User user WHERE login = :login";
        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        typedQuery.setParameter("login", login);
        User user = null;
        try {
            user = typedQuery.getSingleResult();
        } catch (NoResultException e) {
            LOG.error("Catch error", e);
        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        String query = "FROM User user WHERE email = :email";
        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        typedQuery.setParameter("email", email);

        User user = null;
        try {
            user = typedQuery.getSingleResult();
        } catch (NoResultException e) {
            LOG.error("Catch error", e);
        }
        return user;
    }

    @Override
    public User findById(long id) {
        return entityManager.find(User.class, ((int) id));
    }
}
