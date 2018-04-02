package laba17.controllers;

import laba17.dao.bean.User;
import laba17.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    @Qualifier("jpaHibernateUserDao")
    private UserDao userDao;

    @RequestMapping(value = "/user")
    public ModelAndView getUserPage() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userDao.findByLogin(login);
        modelAndView.addObject("name", user.getFirstname());
        modelAndView.setViewName("user");

        return modelAndView;
    }
}
