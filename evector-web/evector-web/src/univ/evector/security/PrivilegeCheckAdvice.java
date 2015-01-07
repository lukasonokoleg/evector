package univ.evector.security;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import univ.evector.shared.security.EvectorAccessFilterContext;
import eu.itreegroup.spark.application.shared.security.AccessFilterContext;
import eu.itreegroup.spark.application.shared.security.AccessFilterContext.ProtectedObjectAdapter;
import eu.itreegroup.spark.application.shared.security.DocumentPrivileges;
import eu.itreegroup.spark.application.shared.security.SparkDocumentFunction;
import eu.itreegroup.spark.application.shared.security.UnauthorizedAccessException;
import eu.itreegroup.spark.gwt.application.client.security.PrivilegesService;

public class PrivilegeCheckAdvice implements MethodBeforeAdvice, AfterReturningAdvice {

    private static final Logger log = Logger.getLogger(PrivilegeCheckAdvice.class);

    private static final AccessFilterContext filterContext = new EvectorAccessFilterContext();

    @Autowired
    private PrivilegesService privilegesService;

    @Override
    public void before(Method method, Object[] arg1, Object arg2) throws Throwable {

    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        log.debug("Check privileges " + method.getDeclaringClass().getName() + "." + method.getName());
        try {
            checkPrivileges(returnValue, method.getReturnType());
        } catch (Throwable t) {
            log.error("Privilege error", t);
            throw t;
        }
    }

    protected void checkPrivileges(Object o, Class<?> clazz) throws Exception {
        DocumentPrivileges privileges = null;
        ProtectedObjectAdapter adapter = filterContext.getAdapter(o);
        if (null != adapter) {
            privileges = privilegesService.findDocumentPrivileges(adapter.getType(o), adapter.getId(o));
        }
        if (null != privileges) {
            boolean hasView = privileges.hasFunction(SparkDocumentFunction.VIEW);
            if (!hasView) {
                throw new UnauthorizedAccessException("Unauthorized to access " + privileges.getDocumentType()
                        + (privileges.getDocumentId() != null ? "/" + privileges.getDocumentId() : ""));
            }
        } else if (!canSkipCheck(o, clazz)) {
            throw new UnauthorizedAccessException("No privileges defined for type " + clazz.getName());
        }
    }

    protected boolean canSkipCheck(Object o, Class<?> clazz) {
        boolean skip = false;
        skip = skip || clazz.isPrimitive();
        skip = skip || Number.class.isAssignableFrom(clazz);
        skip = skip || String.class.isAssignableFrom(clazz);
        skip = skip || Date.class.isAssignableFrom(clazz);
        skip = skip || Boolean.class.isAssignableFrom(clazz);
        skip = skip || DocumentPrivileges.class.isAssignableFrom(clazz);
        return skip;
    }

}
