package laba17.controllers;

import laba17.UserBean;
import laba17.dao.bean.Role;
import laba17.dao.bean.User;
import laba17.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class EditController {

    @Autowired
    @Qualifier("jpaHibernateUserDao")
    private UserDao userDao;

    @RequestMapping(value = "/admin/add", method = RequestMethod.GET)
    public ModelAndView getAddPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("action", "add");
        modelAndView.addObject("user", new UserBean());
        modelAndView.setViewName("edit");

        return modelAndView;
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
    public ModelAndView getEditPage(@ModelAttribute("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        UserBean userBean = UserBean.UserToUserBean(userDao.findById(id));
        modelAndView.addObject("user", userBean);
        modelAndView.addObject("action", "edit");
        modelAndView.setViewName("edit");

        return modelAndView;
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    public ModelAndView editUser(@Valid @ModelAttribute("user") UserBean userBean, BindingResult result,
                                 @ModelAttribute("action") String action) {
        ModelAndView modelAndView = new ModelAndView();

        if (!isUniqueLogin(userBean.getLogin()) && action.equals("add")) {
            result.rejectValue("login", "message code", "User with login already exists");
        }

        if (!isUniqueEmail(userBean.getEmail()) && action.equals("add")) {
            result.rejectValue("email", "message code", "User with email already exists");
        }

        if (result.hasErrors()) {
            modelAndView.addObject("action", action);
            modelAndView.addObject("user", userBean);
            modelAndView.setViewName("edit");

            return modelAndView;
        }

        User user = UserBean.UserBeanToUser(userBean);
        user.setRole(new Role(userBean.getRole()));

        if (action.equals("add")) {
            userDao.create(user);
        } else if (action.equals("edit")) {
            userDao.update(user);
        }

        return new ModelAndView("forward:/admin");
    }

    @RequestMapping(value = "/admin/delete")
    public String deleteUser(@ModelAttribute("id") int id) {
        User user  = new User();
        user.setId(id);
        userDao.remove(user);
        return "forward:/admin";
    }

    private boolean isUniqueLogin(String login) {
        User user = userDao.findByLogin(login);

        return user == null;

    }

    private boolean isUniqueEmail(String email) {
        User user = userDao.findByEmail(email);

        return user == null;
    }
}
