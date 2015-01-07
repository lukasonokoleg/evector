package lt.jmsys.spark.gwt.application.client.common.browse;

import lt.jmsys.spark.gwt.application.common.shared.event.ValueClickEvent;
import lt.jmsys.spark.gwt.client.ui.browse.cell.Cell;

public class BrowseWidgetValueClickEvent<E, C> extends ValueClickEvent<E> {

    private final Cell<C> cell;

    public BrowseWidgetValueClickEvent(E value, Class<E> associatedClass, Cell<C> cell) {
        super(value, associatedClass);
        this.cell = cell;
    }

    public Cell<C> getCell() {
        return cell;
    }

}
