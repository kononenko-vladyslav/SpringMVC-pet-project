package laba17.interfaces;

import laba17.dao.bean.User;

import java.util.List;

public interface UserDao {

    void create(User user);

    void update(User user);

    void remove(User user);

    List<User> findAll();

    User findByLogin(String login);

    User findByEmail(String email);

    User findById(long id);
}
