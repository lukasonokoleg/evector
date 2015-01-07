package eu.itreegroup.spark.application.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class PasswordValidator {

    private Logger LOGGER = Logger.getLogger(PasswordValidator.class);

    private List<String> patternsAsString = new ArrayList<String>();
    {
        patternsAsString.add(".*\\d{1,}.*");
        patternsAsString.add("(.*[a-z]{2,}.*)|(.*[a-z].*[a-z].*)");
        patternsAsString.add("(.*[A-Z]{2,}.*)|(.*[A-Z].*[A-Z].*)");
        patternsAsString.add(".{8,}");
    }

    private List<Pattern> patterns = new ArrayList<Pattern>();
    {
        for (String patternAsString : patternsAsString) {
            patterns.add(Pattern.compile(patternAsString));
        }
    }

    /*private static final String PASSWORD_PATTERN = "((?=.*\\d{1,})(?=.*[a-z]{2,})(?=.*[A-Z]{2,}).{8,})";*/

    public PasswordValidator() {

    }

    /**
     * Validate password with regular expression
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validate(final String password) {
        boolean retVal = false;
        if (password != null) {
            retVal = true;
            Matcher matcher;
            for (Pattern pattern : patterns) {
                matcher = pattern.matcher(password);
                retVal = matcher.matches();
                if (!retVal) {
                    LOGGER.info("Password did not matched pattern:  " + pattern.toString());
                    break;
                }
            }
        }
        return retVal;
    }

}
