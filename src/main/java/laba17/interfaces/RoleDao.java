package laba17.interfaces;

import laba17.dao.bean.Role;

public interface RoleDao {

    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);

    Role findById(long id);
}
