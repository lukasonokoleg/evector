package univ.evector.security;

import java.lang.reflect.Method;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractGenericPointcutAdvisor;
import org.springframework.stereotype.Service;

@SuppressWarnings("serial")
public class ServiceMatchPointcutAdvisor extends AbstractGenericPointcutAdvisor {

    private String nameFilter;
    private ClassFilter classFilter = new PackageClassFilter();
    private MethodMatcher methodMatcher = new ServiceMethodMatcher();
    private Pointcut pointcut = new Pointcut() {

        @Override
        public MethodMatcher getMethodMatcher() {
            return methodMatcher;
        }

        @Override
        public ClassFilter getClassFilter() {
            return classFilter;
        }
    };

    public ServiceMatchPointcutAdvisor() {

    }

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    };

    private class PackageClassFilter implements ClassFilter {

        @Override
        public boolean matches(Class<?> clazz) {
            boolean matches = null != nameFilter ? clazz.getName().startsWith(nameFilter) : true;
            matches = matches && isService(clazz);
            // matches = matches && !PrivilegesService.class.isAssignableFrom(clazz);//Cannot check PrivilegesService
            // because checking depends on this service
            return matches;
        }

        private boolean isService(Class<?> clazz) {
            return null != clazz.getAnnotation(Service.class);
        }

    }

    private class ServiceMethodMatcher implements MethodMatcher {

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return matches(method, targetClass, null);
        }

        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object[] args) {
            boolean selfSecured = null != method.getAnnotation(SelfSecured.class);
            if (!selfSecured && !method.getDeclaringClass().isAssignableFrom(Object.class)) {
                try {
                    Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
                    selfSecured = selfSecured || null != m.getAnnotation(SelfSecured.class);
                } catch (NoSuchMethodException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            return !selfSecured && classFilter.matches(targetClass);
        }

    }

}
