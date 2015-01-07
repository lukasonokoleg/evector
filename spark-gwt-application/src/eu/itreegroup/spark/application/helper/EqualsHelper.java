package eu.itreegroup.spark.application.helper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;

public class EqualsHelper {

    public static boolean equalsIgnoreOrder(Object o1, Object o2) {
        if (null != o1 && null != o2) {
            return new EqualsWrapper(o1).equals(new EqualsWrapper(o2));
        } else if (null != o1 || null != o2) {
            return false;
        } else {
            return true;
        }
    }

    static class EqualsWrapper {

        private Object o;
        private PropertyDescriptor[] properties;

        public EqualsWrapper(Object o) {
            this.o = o;
            properties = BeanUtils.getPropertyDescriptors(o.getClass());
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return "@EqualsWrapper[o=" + o + "]";
        }

        @Override
        public boolean equals(Object obj) {
            try {
                boolean equals = obj instanceof EqualsWrapper;
                if (equals) {
                    EqualsWrapper w = (EqualsWrapper) obj;
                    if (null != o && null != w.o) {
                        if (canUseSimpleEquals(o.getClass())) {
                            equals = o.equals(w.o);
                        } else {
                            equals = equals && equalsScalars(w);
                            equals = equals && equalsCollections(w);
                        }
                    } else if (null != o || null != w.o) {
                        equals = false;
                    }
                }
                return equals;
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        boolean equalsScalars(EqualsWrapper w) throws InvocationTargetException, IllegalAccessException {
            for (PropertyDescriptor p : properties) {
                Class<?> type = p.getPropertyType();
                if (!type.isArray() && !Collection.class.isAssignableFrom(type)) {
                    Object o1 = p.getReadMethod().invoke(o, (Object[]) null);
                    Object o2 = p.getReadMethod().invoke(w.o, (Object[]) null);
                    if (!equalsScalarPropertyValue(type, o1, o2)) {
                        return false;
                    }
                }
            }
            return true;
        }

        boolean equalsScalarPropertyValue(Class<?> type, Object o1, Object o2) {
            if (null != o1 && null != o2) {
                if (canUseSimpleEquals(type)) {
                    return o1.equals(o2);
                } else {
                    return equalsIgnoreOrder(o1, o2);
                }
            } else if (null != o1 || null != o2) {
                return false;
            }
            return true;
        }

        boolean equalsCollectionPropertyValue(Class<?> type, Object o1, Object o2) {
            if (null != o1 && null != o2) {
                Collection<?> c1;
                Collection<?> c2;
                if (type.isArray()) {
                    if (type.getComponentType().isPrimitive()) {
                        c1 = Arrays.asList(toArray(o1));
                        c2 = Arrays.asList(toArray(o2));
                    } else {
                        c1 = Arrays.asList((Object[]) o1);
                        c2 = Arrays.asList((Object[]) o2);
                    }
                } else {
                    c1 = (Collection<?>) o1;
                    c2 = (Collection<?>) o2;
                }
                if (c1.size() != c2.size()) {
                    return false;
                }
                Set<Object> set1 = prepareSet(c1);
                Set<Object> set2 = prepareSet(c2);

                if (!set1.equals(set2)) {
                    return false;
                }
            } else if (null != o1 || null != o2) {
                return false;
            }
            return true;
        }

        boolean equalsCollections(EqualsWrapper w) throws InvocationTargetException, IllegalAccessException {
            if (o == null && w.o == null) {
                return true;
            } else if (o == null || w.o == null) {
                return false;
            } else if (o.getClass().isArray() || Collection.class.isAssignableFrom(o.getClass())) {
                if (!equalsCollectionPropertyValue(o.getClass(), o, w.o)) {
                    return false;
                }
            }
            for (PropertyDescriptor p : properties) {
                Class<?> type = p.getPropertyType();
                if (type.isArray() || Collection.class.isAssignableFrom(type)) {
                    Object o1 = p.getReadMethod().invoke(o, (Object[]) null);
                    Object o2 = p.getReadMethod().invoke(w.o, (Object[]) null);
                    if (!equalsCollectionPropertyValue(type, o1, o2)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private Set<Object> prepareSet(Collection<?> c) {
            Set<Object> set = new HashSet<Object>();
            for (Object o : c) {
                if (null != o) {
                    o = !canUseSimpleEquals(o.getClass()) ? new EqualsWrapper(o) : o;
                    set.add(o);
                }
            }
            return set;
        }

        boolean canUseSimpleEquals(Class<?> type) {
            return type.isPrimitive() || Number.class.isAssignableFrom(type) || String.class.isAssignableFrom(type) || Date.class.isAssignableFrom(type)
                    || Boolean.class.isAssignableFrom(type) || Class.class.isAssignableFrom(type);
        }

        Object[] toArray(Object array) {
            int l = Array.getLength(array);
            Object[] oa = new Object[l];
            for (int i = 0; i < l; i++) {
                oa[i] = Array.get(array, i);
            }
            return oa;
        }

    }
}
