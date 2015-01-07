package univ.evector.gwt.client.F005.bean;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class F005Query implements Serializable {

    private Date sinceDate;

    private Date untilDate;

    private String searchText;

    public F005Query() {

    }

    public Date getSinceDate() {
        return sinceDate;
    }

    public void setSinceDate(Date sinceDate) {
        this.sinceDate = sinceDate;
    }

    public Date getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(Date untilDate) {
        this.untilDate = untilDate;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public String toString() {
        return "F005Query [searchText=" + searchText + "]";
    }

}
