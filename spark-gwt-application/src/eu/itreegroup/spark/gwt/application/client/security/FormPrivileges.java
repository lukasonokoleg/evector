package eu.itreegroup.spark.gwt.application.client.security;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.itreegroup.spark.application.shared.security.DocumentFunction;
import eu.itreegroup.spark.application.shared.security.DocumentPrivileges;
import eu.itreegroup.spark.application.shared.security.SparkDocumentFunction;
import eu.itreegroup.spark.application.shared.security.AccessFilterContext.ProtectedObjectAdapter;

@SuppressWarnings("serial")
public final class FormPrivileges implements Serializable {

    private DocumentPrivileges privileges;

    private FormPrivileges(DocumentPrivileges privileges) {
        this.privileges = privileges;
    }

    public static FormPrivileges getDefaultPrivileges() {
        return new FormPrivileges(null);
    }

    public static void getPrivileges(final Object protectedObject, final AsyncCallback<FormPrivileges> callback) {
        ProtectedObjectAdapter adapter = FormAccessFilterContext.getInstance().getAdapter(protectedObject);
        if (null != adapter) {
            AsyncCallback<DocumentPrivileges> cb = new AsyncCallback<DocumentPrivileges>() {

                @Override
                public void onFailure(Throwable caught) {
                    callback.onFailure(caught);
                }

                @Override
                public void onSuccess(DocumentPrivileges result) {
                    callback.onSuccess(new FormPrivileges(result));
                }
            };
            PrivilegesServiceAsync service = GWT.create(PrivilegesService.class);
            service.findDocumentPrivileges(adapter.getType(protectedObject), adapter.getId(protectedObject), cb);
        } else {
            callback.onSuccess(FormPrivileges.getDefaultPrivileges());
        }

    }

    public boolean hasFunction(DocumentFunction function) {
        final boolean has;
        if (null != privileges) {
            has = privileges.hasFunction(function);
        } else {
            has = hasFunctionByDefault(function);
        }
        return has;
    }

    public boolean hasEnabledFunction(DocumentFunction function) {
        boolean hasEnabled = false;
        if (null != privileges) {
            hasEnabled = privileges.isFunctionEnabled(function);
        }
        return hasEnabled;
    }

    protected boolean hasFunctionByDefault(DocumentFunction function) {
        boolean has = false;
        if (null == privileges) {
            has = has || SparkDocumentFunction.VIEW.getCode().equals(function.getCode());
            has = has || SparkDocumentFunction.EDIT.getCode().equals(function.getCode());
        }
        return has;
    }
}
