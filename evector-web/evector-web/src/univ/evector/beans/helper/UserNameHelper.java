package univ.evector.beans.helper;

import lt.jmsys.spark.gwt.application.shared.helper.AppendHelper;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import univ.evector.beans.User;

public class UserNameHelper {

    public static String getSesUserFullUserName(User sesUser) {
        String firstname = null;
        String lastname = null;
        String userEmail = null;

        if (sesUser != null) {
            firstname = sesUser.getUsr_first_name();
            lastname = sesUser.getUsr_last_name();
            userEmail = sesUser.getUsr_email();
        }

        StringBuilder builder = new StringBuilder();
        AppendHelper.append(builder, firstname, "");
        AppendHelper.append(builder, lastname, " ");

        String prefix = builder.length() == 0 ? "" : "(";
        String postfix = builder.length() == 0 ? "" : ")";
        AppendHelper.append(builder, userEmail, " " + prefix, "", postfix);
        return builder.toString();
    }

    public static String getVvkacUserFirstLastName(User vvkacUser) {
        String vvkacUserUserName = null;
        if (vvkacUser != null) {
        }
        StringBuilder builder = new StringBuilder();

        if (!ConversionHelper.isEmpty(vvkacUserUserName)) {
            builder.append("(");
            builder.append(vvkacUserUserName);
            builder.append(")");
        }

        return builder.toString();
    }

    public static String getFirstLastName(User user) {
        StringBuilder builder = new StringBuilder();
        AppendHelper.append(builder, user.getFirstName(), null);
        AppendHelper.append(builder, user.getLastName(), AppendHelper.DELIMETER_SPACE);
        return builder.toString();
    }

}
