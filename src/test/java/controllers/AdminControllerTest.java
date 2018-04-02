package controllers;

import laba17.controllers.AdminController;
import laba17.dao.bean.Role;
import laba17.dao.bean.User;
import laba17.interfaces.RoleDao;
import laba17.interfaces.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring-security-config.xml", "classpath:dispatcher-servlet.xml", "classpath:testContext.xml"})
public class AdminControllerTest {

    @Mock
    private UserDao userDao;

    @Mock
    private RoleDao roleDao;

    @InjectMocks
    @Autowired
    private AdminController adminController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).setViewResolvers(viewResolver()).build();
    }

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    public void testGetAdminPage() throws Exception {
        Role roleAdmin = new Role(1, "admin");
        Role roleUser = new Role(2, "user");
        User user1 = new User(1, "admin", "admin", "admin@mail.com", "Jones", "Johnson", new Date(), roleAdmin);
        User user2 = new User(2, "user", "user", "user@mail.com", "Tyler", "Durden", new Date(), roleUser);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Map<User, String> usersWithRole = new HashMap<>();
        usersWithRole.put(user1, roleAdmin.getName());
        usersWithRole.put(user2, roleUser.getName());

        when(userDao.findByLogin("admin")).thenReturn(user1);
        when(userDao.findAll()).thenReturn(users);
        when(roleDao.findById(1)).thenReturn(roleAdmin);
        when(roleDao.findById(2)).thenReturn(roleUser);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/admin.jsp"))
                .andExpect(model().attribute("name", user1.getFirstname()))
                .andExpect(model().attribute("users", usersWithRole));

        verify(userDao, times(1)).findByLogin(anyString());
        verify(userDao, times(1)).findAll();
        verify(roleDao, times(2)).findById(anyInt());
    }
}
