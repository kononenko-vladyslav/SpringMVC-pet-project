package controllers;

import laba17.controllers.SecurityController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring-security-config.xml", "classpath:dispatcher-servlet.xml", "classpath:testContext.xml"})
public class SecurityControllerTest {

    @Autowired
    private SecurityController securityController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(securityController).build();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ROLE_ADMIN"})
    public void testAuthWithAdmin() throws Exception {
        mockMvc.perform(get("/welcome"))
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"ROLE_USER"})
    public void testAuthWithUser() throws Exception {
        mockMvc.perform(get("/welcome"))
                .andExpect(redirectedUrl("/user"));
    }
}
