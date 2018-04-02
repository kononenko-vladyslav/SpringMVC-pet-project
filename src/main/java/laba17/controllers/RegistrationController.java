package laba17.controllers;

import laba17.UserBean;
import laba17.dao.bean.Role;
import laba17.dao.bean.User;
import laba17.interfaces.UserDao;
import laba17.util.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    @Qualifier("jpaHibernateUserDao")
    private UserDao userDao;

    /*@Autowired*/
    private VerifyUtils verifyUtils = new VerifyUtils();

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView getRegistrationPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new UserBean());
        modelAndView.setViewName("registration");

        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registration(@Valid @ModelAttribute("user") UserBean userBean, BindingResult result,
                                     @RequestParam("g-recaptcha-response") String gRecaptchaResponse) {
        ModelAndView modelAndView = new ModelAndView();

        if (!isUniqueLogin(userBean.getLogin())) {
            result.rejectValue("login", "message code", "User with login already exists");
        }

        if (!isUniqueEmail(userBean.getEmail())) {
            result.rejectValue("email", "message code", "User with email already exists");
        }

        if(verifyUtils.verify(gRecaptchaResponse)) {
            modelAndView.addObject("reCaptchaFail", true);
            modelAndView.addObject("user", userBean);
            modelAndView.setViewName("registration");

            return modelAndView;
        }

        if (result.hasErrors()) {
            modelAndView.addObject("user", userBean);
            modelAndView.setViewName("registration");

            return modelAndView;
        }

        User user = UserBean.UserBeanToUser(userBean);
        user.setRole(new Role(userBean.getRole()));

        userDao.create(user);

        return new ModelAndView("redirect:/?reg=true");
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
