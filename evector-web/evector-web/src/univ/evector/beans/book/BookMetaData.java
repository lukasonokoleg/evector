package univ.evector.beans.book;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class BookMetaData implements Serializable {

    private List<BookProperty> properties = new ArrayList<BookProperty>();

    public BookMetaData() {

    }

    public void addProperty(String name, String value) {
        BookProperty property = new BookProperty();
        property.setName(name);
        property.setValue(value);

        properties.add(property);
    }

    public List<BookProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<BookProperty> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "BookMetaData [properties=" + properties + "]";
    }

}