package lt.jmsys.spark.gwt.suggest.oracle;

import java.util.Map;

import eu.itreegroup.spark.application.shared.search.TransliterationTable;

public class EvectorTransliterationHelper {

    private Map<Character, String> transliterationTable;

    /**
     * INitialize the helper with a transliteration table to transliterate
     * the queries.
     * @param transliterationTable The transliteration table to use. This
     * {@link Map} should contain strings assocaited with every character in the query,
     * like resolving shorthands.
     */
    public void init(Map<Character, String> transliterationTable) {
        this.transliterationTable = transliterationTable;
    }

    public EvectorTransliterationHelper() {
        init(TransliterationTable.map());
    }

    /**
     * Transliterates (or more correctly, transabbreviates, as transliteration performed
     *  like this would never do more than one helpful iteration of the cycle),
     *   query by resolving shorthand characters. 
     * If some query characters don't have a longer word associated with them,
     * the character itself is put into the final string, otherwise - the longer word is.
     * @param queryString the query to transliterate.
     * @return the translierated version of the query provided. 
     * <code>null</code> if the provided <code>queryString</code> was 
     * <code>null</code>
     */
    public String transliterate(String queryString) {
        if (null != queryString) {
            StringBuilder sb = new StringBuilder();
            int l = queryString.length();
            for (int i = 0; i < l; i++) {
                char c = queryString.charAt(i);
                String to = transliterationTable.get(c);
                if (null == to) {
                    sb.append(c);
                } else {
                    sb.append(to);
                }
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    public String transliterateUpperCased(String queryString) {
        String retVal = transliterate(queryString);
        retVal = retVal != null ? retVal.toUpperCase() : retVal;
        return retVal;
    }

    public String transliterateLowerCased(String queryString) {
        String retVal = transliterate(queryString);
        retVal = retVal != null ? retVal.toUpperCase() : retVal;
        return retVal;
    }

}
