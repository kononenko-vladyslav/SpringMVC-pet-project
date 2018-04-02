package laba17;

import laba17.constraints.FieldEquals;
import laba17.dao.bean.Role;
import laba17.dao.bean.User;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@FieldEquals( field="password", equalsTo="confirmPassword" )
public class UserBean {

    private int id;

    @NotEmpty(message = "Please enter login")
    @Pattern(regexp = "^[a-z]+([-_]?[a-z0-9]+){0,2}$", message = "Incorrect login")
    @Size(min = 3, max = 10, message = "Your login must between 3 and 10 characters")
    private String login;

    @NotEmpty(message = "Please enter password")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Incorrect password")
    @Size(min = 4, max = 15, message = "Your password must between 4 and 15 characters")
    private String password;

    private String confirmPassword;

    @Pattern(regexp = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$", message = "Your email must be correct")
    private String email;

    @NotEmpty(message = "Please enter first name")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Incorrect first name")
    @Size(min = 1, max = 15, message = "Your first name must between 1 and 15 characters")
    private String firstname;

    @NotEmpty(message = "Please enter last name")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Incorrect last name")
    @Size(min = 1, max = 15, message = "Your last name must between 1 and 15 characters")
    private String lastname;

    @Pattern(regexp = "(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d", message = "Your date must be correct")
    private String birthday;
    private int role;

    public UserBean() {

    }

    public UserBean(int id, String login, String password, String confirmPassword, String email, String firstname, String lastname, String birthday, int role) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.role = role;
    }

    public static User UserBeanToUser(UserBean userBean) {
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Role role = new Role();

        user.setId(userBean.getId());
        user.setLogin(userBean.getLogin());
        user.setPassword(userBean.getPassword());
        user.setEmail(userBean.getEmail());
        user.setFirstname(userBean.getFirstname());
        user.setLastname(userBean.getLastname());
        try {
            user.setBirthday(sdf.parse(userBean.getBirthday()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        role.setId(userBean.getId());
        user.setRole(role);

        return user;
    }

    public static UserBean UserToUserBean(User user) {
        UserBean userBean = new UserBean();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        userBean.setId(user.getId());
        userBean.setLogin(user.getLogin());
        userBean.setPassword(user.getPassword());
        userBean.setEmail(user.getEmail());
        userBean.setFirstname(user.getFirstname());
        userBean.setLastname(user.getLastname());
        userBean.setBirthday(sdf.format(user.getBirthday()));
        userBean.setRole(user.getRole().getId());

        return userBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBean userBean = (UserBean) o;

        if (id != userBean.id) return false;
        if (role != userBean.role) return false;
        if (login != null ? !login.equals(userBean.login) : userBean.login != null) return false;
        if (password != null ? !password.equals(userBean.password) : userBean.password != null) return false;
        if (confirmPassword != null ? !confirmPassword.equals(userBean.confirmPassword) : userBean.confirmPassword != null)
            return false;
        if (email != null ? !email.equals(userBean.email) : userBean.email != null) return false;
        if (firstname != null ? !firstname.equals(userBean.firstname) : userBean.firstname != null) return false;
        if (lastname != null ? !lastname.equals(userBean.lastname) : userBean.lastname != null) return false;
        return birthday != null ? birthday.equals(userBean.birthday) : userBean.birthday == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (confirmPassword != null ? confirmPassword.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + role;
        return result;
    }
}
