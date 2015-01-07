package eu.itreegroup.spark.application.server.db.security;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import eu.itreegroup.spark.application.server.db.service.Als_priv;
import eu.itreegroup.spark.application.shared.db.bean.Spr_doc_priv_ot;
import eu.itreegroup.spark.application.shared.security.AccessFilterContext;

public class ServiceAccessFilterContext extends AccessFilterContext {

    private Als_priv privService;

    public ServiceAccessFilterContext(Als_priv privService) {
        this.privService = privService;
    }

    public Spr_doc_priv_ot getAccessRights(Object protectedObject) throws SparkBusinessException {
        ProtectedObjectAdapter a = getAdapter(protectedObject);
        if (null != a) {
            String type = a.getType(protectedObject).getCode();
            Long id = a.getId(protectedObject);
            return privService.find_doc_priv(type, id != null ? id.doubleValue() : null);
        } else {
            return null;
        }
    }

}
