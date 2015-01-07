package eu.itreegroup.spark.application.server.db.security;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import lt.jmsys.spark.bind.service.params.InOut;
import lt.jmsys.spark.bind.service.params.Out;
import eu.itreegroup.spark.application.server.db.service.Als_priv;
import eu.itreegroup.spark.application.shared.db.bean.Als_YesNo;
import eu.itreegroup.spark.application.shared.db.bean.Spr_doc_priv_ot;
import eu.itreegroup.spark.application.shared.db.bean.Spr_field_priv_ot;
import eu.itreegroup.spark.application.shared.db.bean.Spr_function_priv_ot;
import eu.itreegroup.spark.application.shared.security.UnauthorizedAccessException;

public class ServiceAccessFilter {

    // private static final Pattern LEGAL_ORDER_BY_PATTERN = Pattern.compile("[\\w_]*");
    private static final ServiceAccessFilter instance = new ServiceAccessFilter();

    public static ServiceAccessFilter getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <I> I filter(I service, Class<I> serviceInterface, Als_priv privService) {
        return (I) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { serviceInterface }, new FilteringInvocationHandler<I>(new ServiceAccessFilterContext(privService),
                service));
    }

    private static class FilteringInvocationHandler<I> implements InvocationHandler {

        private I service;
        private ServiceAccessFilterContext context;

        public FilteringInvocationHandler(ServiceAccessFilterContext context, I service) {
            this.service = service;
            this.context = context;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object r;
            try {
                /*                if (null != args){
                                    for(Object arg : args){
                                        Object p = null;
                                        if (arg instanceof InOut<?>){
                                            p = ((InOut<?>)arg).get();
                                        }
                                        
                                        if (p instanceof Spr_paging_ot){
                                            Spr_paging_ot paging = (Spr_paging_ot)p;
                                            String orderClause = paging.getOrder_clause();
                                            if (null != orderClause){
                                                if (!LEGAL_ORDER_BY_PATTERN.matcher(orderClause).matches()){
                                                    throw new IllegalArgumentException("Illegal order by clause");
                                                }
                                            }
                                        }
                                    }
                                }*/
                r = method.invoke(service, args);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
            filter(r, context.getAccessRights(r));
            if (null != args) {
                for (Object arg : args) {
                    Object o = null;
                    if (arg instanceof Out<?>) {
                        o = ((Out<?>) arg).get();
                    } else if (arg instanceof InOut<?>) {
                        o = ((InOut<?>) arg).get();
                    }
                    if (null != o) {
                        filter(o, context.getAccessRights(o));
                    }
                }
            }
            return r;
        }

        protected void filter(Object o, Spr_doc_priv_ot accessRights) throws Exception {
            if (null != accessRights) {
                boolean hasView = hasViewFunction(accessRights);
                if (!hasView) {
                    throw new UnauthorizedAccessException("Unauthorized to access " + accessRights.getDoc_type()
                            + (accessRights.getDoc_id() != null ? "/" + accessRights.getDoc_id() : ""));
                }
                for (Spr_field_priv_ot f : accessRights.getField_privs()) {
                    if (Als_YesNo.NO.equals(f.getIs_accessible())) {
                        setNull(o, f.getField_code());
                    }
                }
            }
        }

    }

    private static String[] splitProperty(String property) {
        int index = property.indexOf('.');
        if (index != -1) {
            return new String[] { property.substring(0, index), property.substring(index + 1) };
        } else {
            return new String[] { property, null };
        }
    }

    private static void setNull(Object o, String property) throws Exception {
        if (o.getClass().isArray()) {
            Object[] array = (Object[]) o;
            for (Object _o : array) {
                setNull(_o, property);
            }
        } else {
            String[] p = splitProperty(property);
            if (null == p[1]) {
                String setterName = "set" + p[0].substring(0, 1).toUpperCase() + p[0].substring(1);
                for (Method m : o.getClass().getMethods()) {
                    if (m.getName().equals(setterName) && m.getParameterTypes().length == 1) {
                        m.invoke(o, new Object[] { null });
                    }
                }
            } else {
                String getterName = "get" + p[0].substring(0, 1).toUpperCase() + p[0].substring(1);
                for (Method m : o.getClass().getMethods()) {
                    if (m.getName().equals(getterName) && m.getParameterTypes().length == 0) {
                        Object r = m.invoke(o);
                        if (null != r) {
                            setNull(r, p[1]);
                            break;
                        }
                    }
                }
            }

        }
    }

    private static boolean hasViewFunction(Spr_doc_priv_ot accessRights) {
        for (Spr_function_priv_ot f : accessRights.getFunction_privs()) {
            if ("VIEW".equals(f.getFunction_code())) {
                return Als_YesNo.YES.equals(f.getIs_enabled());
            }
        }
        return false;
    }

}
