package controllers;

import laba17.controllers.UserController;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring-security-config.xml", "classpath:dispatcher-servlet.xml", "classpath:testContext.xml"})
public class UserControllerTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setViewResolvers(viewResolver()).build();
    }

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Test
    @WithMockUser
    public void testGetUserPage() throws Exception {
        User user = new User();
        user.setFirstname("Tyler");
        when(userDao.findByLogin("user")).thenReturn(user);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/user.jsp"))
                .andExpect(model().attribute("name", user.getFirstname()));

        verify(userDao, times(1)).findByLogin(anyString());
        verifyNoMoreInteractions(userDao);
    }



































   /* @Autowired
    private ApplicationContext applicationContext;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private HandlerAdapter handlerAdapter;
    private UserController userController;

    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        handlerAdapter = applicationContext.getBean(HandlerAdapter.class);
        userController = new UserController();
    }

    @Test
    public void test() throws Exception {
        request.setRequestURI("/user");
        ModelAndView modelAndView = handlerAdapter.handle(request, response, userController);

        assertViewName(modelAndView, "user");
    }*/
}
