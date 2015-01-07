package eu.itreegroup.spark.application.shared.security;

import java.util.Collections;
import java.util.List;

public class AccessFilterContext {

    public interface ProtectedObjectAdapter {

        Long getId(Object protectedObject);

        DocumentType getType(Object protectedObject);

        boolean accepts(Object protectedObject);
    }

    public ProtectedObjectAdapter getAdapter(Object protectedObject) {
        if (null != protectedObject) {
            for (ProtectedObjectAdapter a : adapters()) {
                if (a.accepts(protectedObject)) {
                    return a;
                }
            }
        }
        return null;
    }

    protected List<ProtectedObjectAdapter> adapters() {
        return Collections.emptyList();
    }

}
