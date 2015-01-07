package eu.itreegroup.spark.gwt.application.client.security;

import com.google.gwt.core.shared.GWT;

import eu.itreegroup.spark.application.shared.security.AccessFilterContext;

public class FormAccessFilterContext extends AccessFilterContext {

    private static final AccessFilterContext instance = GWT.create(AccessFilterContext.class);

    public static AccessFilterContext getInstance() {
        return instance;
    }

}