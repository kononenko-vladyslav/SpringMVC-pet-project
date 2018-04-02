package laba17.dao.jpahibernate;

import laba17.dao.bean.Role;
import laba17.interfaces.RoleDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Repository
@Transactional
public class JpaHibernateRoleDao implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void update(Role role) {
        entityManager.merge(role);
    }

    @Override
    public void remove(Role role) {
        entityManager.remove(entityManager.contains(role) ? role : entityManager.merge(role));
    }

    @Override
    public Role findByName(String name) {
        String query = "FROM Role role WHERE name = :name";
        TypedQuery<Role> typedQuery = entityManager.createQuery(query, Role.class);
        typedQuery.setParameter("name", name);

        Role role = null;
        try {
            role = typedQuery.getSingleResult();
        } catch (NoResultException e) {

        }
        return role;
    }

    @Override
    public Role findById(long id) {
        return entityManager.find(Role.class, ((int) id));
    }
}
