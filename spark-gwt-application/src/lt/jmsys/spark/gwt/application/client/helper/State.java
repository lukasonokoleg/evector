package lt.jmsys.spark.gwt.application.client.helper;

public class State<E> {

    private String stateId;
    private String itemId;
    private E item;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public E getItem() {
        return item;
    }

    public void setItem(E item) {
        this.item = item;
    }

}
