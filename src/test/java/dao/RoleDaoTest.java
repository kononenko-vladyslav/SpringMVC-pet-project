package dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import laba17.dao.bean.Role;
import laba17.interfaces.RoleDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testContext.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:role/role-data.xml")
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    @ExpectedDatabase(value = "classpath:role/role-data-create.xml", table = "role")
    public void create() throws Exception {
        Role role = new Role();
        role.setName("user1");
        this.roleDao.create(role);
    }

    @Test
    @ExpectedDatabase(value = "classpath:role/role-data-update.xml", table = "role")
    public void update() throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setName("admin1");
        roleDao.update(role);
    }

    @Test
    @ExpectedDatabase(value = "classpath:role/role-data-remove.xml", table = "role")
    public void remove() throws Exception {
        Role role = new Role();
        role.setId(1);
        roleDao.remove(role);
    }

    @Test
    public void findByName() {
        Role role = new Role("user");
        assertEquals(role.getName(), roleDao.findByName(role.getName()).getName());
    }

    @Test
    public void findById() {
        Role role = new Role(1);
        assertEquals(role.getId(), roleDao.findById(role.getId()).getId());
    }
}