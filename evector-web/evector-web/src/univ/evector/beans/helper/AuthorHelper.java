package univ.evector.beans.helper;

import univ.evector.beans.Author;

public class AuthorHelper {

    public static Author newInstance(String name) {
        Author retVal = new Author();

        retVal.setAuth_first_name(name);

        return retVal;
    }

}