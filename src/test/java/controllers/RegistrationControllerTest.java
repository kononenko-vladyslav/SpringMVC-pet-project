package controllers;

import laba17.controllers.RegistrationController;
import laba17.interfaces.UserDao;
import laba17.util.VerifyUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring-security-config.xml", "classpath:dispatcher-servlet.xml", "classpath:testContext.xml"})
public class RegistrationControllerTest {

    @Mock
    private VerifyUtils verifyUtils;

    @Mock
    private UserDao userDao;

    @InjectMocks
    @Autowired
    private RegistrationController registrationController;

    MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).setViewResolvers(viewResolver()).build();
    }

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Test
    public void testGetRegistrationPage() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/registration.jsp"));
    }

    @Test
    public void testRegistration() throws Exception {
        when(verifyUtils.verify("key")).thenReturn(false);
        when(userDao.findByLogin("user")).thenReturn(null);
        when(userDao.findByEmail("user@mail.com")).thenReturn(null);

        mockMvc.perform(post("/registration")
                .param("login", "user")
                .param("password", "user")
                .param("confirmPassword", "user")
                .param("email", "user@mail.com")
                .param("firstname", "Tyler")
                .param("lastname", "Durden")
                .param("birthday", "10.10.2010")
                .param("role", "2")
                .param("g-recaptcha-response", "key"))
                .andExpect(redirectedUrl("/?reg=true"));

        verify(userDao, times(1)).findByLogin(anyString());
        verify(userDao, times(1)).findByLogin(anyString());
        verify(verifyUtils, times(1)).verify(anyString());
    }

}
