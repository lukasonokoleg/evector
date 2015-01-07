package univ.evector.shared.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import univ.evector.beans.Message;
import univ.evector.beans.User;
import univ.evector.beans.privileges.EvectorDocumentType;
import eu.itreegroup.spark.application.shared.security.AccessFilterContext;
import eu.itreegroup.spark.application.shared.security.DocumentType;

public class EvectorAccessFilterContext extends AccessFilterContext {

    private static List<ProtectedObjectAdapter> adapters = new ArrayList<>();
    static {
        adapters.add(new ProtectedObjectAdapter() {

            @Override
            public DocumentType getType(Object protectedObject) {
                if (protectedObject instanceof Message) {
                    return EvectorDocumentType.MESSAGE;
                }
                return null;
            }

            @Override
            public Long getId(Object protectedObject) {
                Message msg = (Message) protectedObject;
                return msg.getMsg_id();
            }

            @Override
            public boolean accepts(Object protectedObject) {
                return protectedObject instanceof Message;
            }
        });
        adapters.add(new ProtectedObjectAdapter() {

            @Override
            public DocumentType getType(Object protectedObject) {
                if (protectedObject instanceof User) {
                    return EvectorDocumentType.USER;
                }
                return null;
            }

            @Override
            public Long getId(Object protectedObject) {
                User user = (User) protectedObject;
                return user.getUsr_id();
            }

            @Override
            public boolean accepts(Object protectedObject) {
                return protectedObject instanceof User;
            }
        });

        adapters = Collections.unmodifiableList(adapters);
    }

    @Override
    protected List<ProtectedObjectAdapter> adapters() {
        return adapters;
    }
}
