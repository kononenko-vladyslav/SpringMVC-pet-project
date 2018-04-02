package laba17.tag;

import laba17.dao.bean.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TableTag extends SimpleTagSupport {

    private Map<User,String> users = new HashMap<>();
    private String cssClass;

    public void setUsers(Map<User, String> users) {
        this.users = users;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        StringBuilder sb = new StringBuilder();

        sb.append("<table class=\"" + cssClass + "\">");

        sb.append("<tr>");
        sb.append("<td>");
        sb.append("<b>Login</b>");
        sb.append("</td>");
        sb.append("<td>");
        sb.append("<b>First Name</b>");
        sb.append("</td>");
        sb.append("<td>");
        sb.append("<b>Last Name</b>");
        sb.append("</td>");
        sb.append("<td>");
        sb.append("<b>Age</b>");
        sb.append("</td>");
        sb.append("<td>");
        sb.append("<b>Role</b>");
        sb.append("</td>");
        sb.append("<td>");
        sb.append("<b>Actions</b>");
        sb.append("</td>");
        sb.append("</tr>");

        for (User user : users.keySet()) {
            sb.append("<tr>");
            sb.append("<td>");
            sb.append(user.getLogin());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(user.getFirstname());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(user.getLastname());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(calculateAge(user.getBirthday()));
            sb.append("</td>");
            sb.append("<td>");
            sb.append(users.get(user));
            sb.append("</td>");
            sb.append("<td>");
            sb.append("<a href=\"/admin/edit?action=edit&id=" + user.getId() + "\" >Edit</a> ");
            sb.append("<a href=\"/admin/delete?id=" + user.getId() + "\" onclick=\"return confirm('Are you sure?');\">Delete</a>");
            sb.append("</td>");
            sb.append("</tr>");
        }

        sb.append("</table>");

        out.println(sb);
    }

    private Integer calculateAge(Date birthday)
    {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(birthday);
        dob.add(Calendar.DAY_OF_MONTH, -1);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }
}
