package univ.evector.facebook.reading;

import java.util.Date;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import facebook4j.Reading;

public class FacebookReadingBuilder {

    private Date sinceDate;
    private Date untilDate;
    private String searchText;

    public FacebookReadingBuilder() {

    }

    public FacebookReadingBuilder setSinceDate(Date sinceDate) {
        this.sinceDate = sinceDate;
        return this;
    }

    public FacebookReadingBuilder setUntilDate(Date untilDate) {
        this.untilDate = untilDate;
        return this;
    }

    public FacebookReadingBuilder setSearchText(String searchText) {
        this.searchText = searchText;
        return this;
    }

    public Reading build() {
        Reading retVal = new Reading();

        if (sinceDate != null) {
            retVal = retVal.since(sinceDate);
        }

        if (untilDate != null) {
            retVal = retVal.until(untilDate);
        }

        if (!ConversionHelper.isEmpty(searchText)) {
            retVal = retVal.filter(searchText);
        }

        return retVal;
    }
}
