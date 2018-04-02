package laba17.controllers;

import laba17.dao.bean.Role;
import laba17.dao.bean.User;
import laba17.interfaces.RoleDao;
import laba17.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    @Qualifier("jpaHibernateUserDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("jpaHibernateRoleDao")
    private RoleDao roleDao;

    @RequestMapping(value = "/admin")
    public ModelAndView getAdminPage() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userDao.findByLogin(login);
        modelAndView.addObject("name", user.getFirstname());

        Map<User, String> usersWithRole = new HashMap<>();
        List<User> users = userDao.findAll();

        for (User u : users) {
            Role role = roleDao.findById(u.getRole().getId());
            usersWithRole.put(u, role.getName());
        }

        modelAndView.addObject("users", usersWithRole);
        modelAndView.setViewName("admin");

        return modelAndView;
    }
}
