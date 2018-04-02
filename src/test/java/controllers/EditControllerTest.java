package controllers;

import laba17.UserBean;
import laba17.controllers.EditController;
import laba17.dao.bean.Role;
import laba17.dao.bean.User;
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

import java.util.Date;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring-security-config.xml", "classpath:dispatcher-servlet.xml", "classpath:testContext.xml"})
public class EditControllerTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    @Autowired
    private EditController editController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(editController).setViewResolvers(viewResolver()).build();
    }

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }
    
    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ROLE_ADMIN"})
    public void testGetAddPage() throws Exception {
        mockMvc.perform(get("/admin/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("action", "add"))
                .andExpect(forwardedUrl("/WEB-INF/view/edit.jsp"));
    }

    @Test
    public void testGetEditPage() throws Exception {
        User user = new User(1, "user", "user", "user@mail.com", "Tyler", "Durden", new Date(), new Role(1));
        when(userDao.findById(1)).thenReturn(user);

        mockMvc.perform(get("/admin/edit").param("id" ,"1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("action", "edit"))
                .andExpect(model().attribute("user", UserBean.UserToUserBean(user)))
                .andExpect(forwardedUrl("/WEB-INF/view/edit.jsp"));

        verify(userDao, times(1)).findById(1);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testEditUser() throws Exception {
        when(userDao.findByLogin("user")).thenReturn(null);
        when(userDao.findByEmail("user@mail.com")).thenReturn(null);

        mockMvc.perform(post("/admin/edit")
                .param("login", "user")
                .param("password", "user")
                .param("confirmPassword", "user")
                .param("email", "user@mail.com")
                .param("firstname", "Tyler")
                .param("lastname", "Durden")
                .param("birthday", "10.10.2010")
                .param("role", "2"))
                .andExpect(forwardedUrl("/admin"));

        verify(userDao, times(1)).findByLogin(anyString());
        verify(userDao, times(1)).findByLogin(anyString());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(get("/admin/delete").param("id" ,"1"))
                .andExpect(forwardedUrl("/admin"));

        verify(userDao, times(1)).remove(any());
    }
}
