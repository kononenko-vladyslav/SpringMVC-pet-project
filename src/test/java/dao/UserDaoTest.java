package dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import laba17.dao.bean.Role;
import laba17.dao.bean.User;
import laba17.interfaces.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testContext.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:user/user-data.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    @ExpectedDatabase(value = "classpath:user/user-data-create.xml", table = "user")
    public void create() throws Exception {
        Role role = new Role();
        role.setId(1);
        User user = new User("log3", "pass3", "email3", "fname3",
                "lname3", sdf.parse("2017-01-03"), role);
        userDao.create(user);
    }

    @Test
    @ExpectedDatabase(value = "classpath:user/user-data-update.xml", table = "user")
    public void update() throws Exception {
        Role role = new Role();
        role.setId(1);
        User user = new User(1,"log1", "pass4", "email4", "fname4",
                "lname4", sdf.parse("2017-01-04"), role);
        userDao.update(user);
    }

    @Test
    @ExpectedDatabase(value = "classpath:user/user-data-remove.xml", table = "user")
    public void remove() throws Exception {
        Role role = new Role();
        role.setId(1);
        User user = new User(1, "log1", "pass1", "email1", "fname1",
                "lname1", sdf.parse("2017-01-01"), role);
        userDao.remove(user);
    }

    @Test
    public void findAll() throws Exception {
        Role role = new Role();
        role.setId(1);
        List<User> users = new ArrayList<>();
        users.add(new User("log1", "pass1", "email1", "fname1",
                "lname1", sdf.parse("2017-01-01"), role));
        users.add(new User("log2", "pass2", "email2", "fname2",
                "lname2", sdf.parse("2017-01-02"), role));

        List<User> actualUser = userDao.findAll();

        assertEquals(users.size(), actualUser.size());
    }

    @Test
    public void findByLogin() throws Exception {
        Role role = new Role();
        role.setId(1);
        User user = new User("log3", "pass3", "email3", "fname3",
                "lname3", sdf.parse("2017-01-03"), role);
        userDao.create(user);
        assertEquals(user.getLogin(), userDao.findByLogin(user.getLogin()).getLogin());
    }

    @Test
    public void findByEmail() throws Exception {
        Role role = new Role();
        role.setId(1);
        User user = new User("log3", "pass3", "email3", "fname3",
                "lname3", sdf.parse("2017-01-03"), role);
        userDao.create(user);
        assertEquals(user.getEmail(), userDao.findByEmail(user.getEmail()).getEmail());
    }

    @Test
    public void findById() {
        User user = new User();
        user.setId(1);
        assertEquals(user.getId(), userDao.findById(user.getId()).getId());
    }
}
