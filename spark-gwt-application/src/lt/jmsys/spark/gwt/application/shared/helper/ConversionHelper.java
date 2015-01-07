package lt.jmsys.spark.gwt.application.shared.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.itreegroup.spark.application.shared.db.bean.Als_YesNo;

public class ConversionHelper {

    private final static int FIRST_ITEM_INDEX = 0;

    public static <E> boolean isNull(E value) {
        return value == null;
    }

    public static <E> List<E> toList(E[] array) {
        if (array == null) {
            return null;
        }
        ArrayList<E> arrayList = new ArrayList<E>();
        for (E obj : array) {
            arrayList.add(obj);
        }
        return arrayList;
    }

    public static <E> List<E> asArrayList(E... values) {
        List<E> retVal = new ArrayList<E>();

        if (values != null) {
            retVal.addAll(Arrays.asList(values));
        }

        return retVal;
    }

    public static String joinStrings(String... strings) {
        StringBuilder builder = new StringBuilder();
        if (strings != null) {
            for (String s : strings) {
                builder.append(s == null ? "" : s); // join without producing "null" words
            }
        }
        return builder.toString();
    }

    /**
     * Get element at array index without knowing if the index is within bounds, 
     * or if there actually was an array
     * @param array the array to get an element of
     * @param index index at which to probe for an element
     * @param alternative the fail-safe object to return if the array was null or has 0 elements
     * @return an object of the array type. If there is no array, then <code>alternative</code> is
     * returned.<br/>
     * If there is an array then an attempt is made to return element at specified index.<br/>
     * - Array contains index: the element at position is returned;<br/>
     * - Array Index out of bounds: the distance from specified index to array bounds is calculated:<br/>
     *  -The specified index is arithmetically determined to be closer to -<br/> 
     *  --Array start (index negative): first element is returned;<br/>
     *  --Array end (index positive, larger than length): last element is returned.
     */
    public static <E> E safeGet(E[] array, int index, E alternative) {
        if (array == null || array.length == 0) {
            return alternative;
        }
        try {
            return array[index];
        } catch (ArrayIndexOutOfBoundsException ex) {

            int length = array.length;

            if (Math.abs(index) < Math.abs(index - length)) {
                return array[0];
            } else {
                return array[length - 1];
            }

        }
    }

    public static Double maxValue(Double... doubles) {
        Double retValue = null;
        for (Double obj : doubles) {
            if (obj != null) {
                if (retValue == null || retValue.compareTo(obj) > 0) {
                    retValue = obj;
                }
            }
        }
        return retValue;
    }

    public static Double minValue(Double... doubles) {
        Double retValue = null;
        for (Double obj : doubles) {
            if (obj != null) {
                if (retValue == null || retValue.compareTo(obj) < 0) {
                    retValue = obj;
                }
            }
        }
        return retValue;
    }

    public static boolean toBoolean(String value) {
        if (!isEmpty(value)) {
            if (value.equals(Als_YesNo.YES)) {
                return true;
            } else if (value.equals(Als_YesNo.NO)) {
                return false;
            }
        }
        throw new RuntimeException("String value has to be either 'Y' or 'N' ! Current string value is:" + value);
    }

    public static Integer toInteger(String value) {
        if (isEmpty(value)) {
            return null;
        } else {
            return Integer.parseInt(value);
        }
    }

    public static String toStringDb(Boolean value) {
        if (null == value)
            return null;
        else {
            if (value)
                return Als_YesNo.YES;
            else
                return Als_YesNo.NO;
        }

    }

    /**
     * Mathematical clamp function with null checks.<br/>
     * A <i>clamp</i> is a function, that only provides values of a limited range. That is, <br/>
     * For all y=clamp(x, a, b): E(y)=[a;b], where x, a, b belong to set R.<br/>
     * A <code>null</code> is considered to be the smallest value.<br/>
     * All 3 values passed must be {@link Comparable}.
     * @param value the value to check against the clamp range
     * @param min the lowest allowed return value
     * @param max the largest allowed return value
     * @return the value itself if it resides in the range [min;max], 
     * <code>min</code> if value is smaller, <code>max</code> if its larger.
     */
    public static <E extends Comparable<E>> E clamp(E value, E min, E max) {
        if (value != null) {
            if (value.compareTo(min) < 0) {
                return min;
            }
            if (value.compareTo(max) > 1) {
                return max;
            }
            return value;
        } else {
            return min != null ? min : max;
        }
    }

    /**
     * Removes duplicate entries of beans that have matching {@link #toString()} representations
     * @param list list to remove duplicates out of
     * @return same list without duplicate elements 
     */
    public static <E> List<E> removeListBeanDuplicates(List<E> list) {

        if (list != null && list.size() > 1) {
            HashMap<String, E> itemsMap = new HashMap<String, E>();
            for (E item : list) {
                if (item != null) {
                    itemsMap.put(item.toString(), item);
                }
            }

            if (itemsMap.size() < list.size()) {
                list.clear();
                list.addAll(itemsMap.values());
            }
        }

        return list;
    }

    /**
     * Merges separate lists of elements into a <i>distinct</i> list of all elements.
     * @param lists list of lists to merge
     * @return the new list, containing one instance of all different elements of all the provided lists.
     * <br/>Resulting list properties:<br/>
     * 1. Contains no <code>null</code> elements, they are cleared both at the end and during intermediate steps.<br/>
     * 2. Checks equality both using the standard {@link #equals(Object)} called from within <code>contains()</code> 
     * methods, and {@link #hasEqualInList(Object, List)} for DB beans lists support. If either of these are true,
     * the list doesn't add the element.
     */
    public static <E> List<E> mergeLists(List<List<E>> lists) {
        List<E> mergedList = new ArrayList<E>();
        if (lists != null && lists.size() > 0) {
            lists = ConversionHelper.removeNulls(lists);
            for (List<E> list : lists) {
                list = ConversionHelper.removeNulls(list);
                for (E elem : list) {
                    boolean contains = mergedList.contains(elem) || hasEqualInList(elem, mergedList);
                    if (!contains) {
                        mergedList.add(elem);
                    }
                }
            }
        }
        return mergedList;
    }

    public static Double roundDouble(Double value, Integer commaIndx) {
        Double tmpDouble = Math.pow(10.0, commaIndx.doubleValue());
        Double tmpValue = tmpDouble * value;
        Long tmpLong = Math.round(tmpValue);
        value = tmpLong.doubleValue() / tmpDouble;
        return value;
    }

    public static Boolean toBooleanYesNo(String dbYesNo) {
        if (Als_YesNo.YES.equals(dbYesNo)) {
            return Boolean.TRUE;
        } else if (Als_YesNo.NO.equals(dbYesNo)) {
            return Boolean.FALSE;
        } else {
            return null;
        }
    }

    public static Double toDouble(String value) {
        if (isEmpty(value)) {
            return null;
        } else {
            return Double.parseDouble(value);
        }
    }

    public static Double toDouble(Number value) {
        if (null == value) {
            return null;
        } else {
            return value.doubleValue();
        }
    }

    public static Long toLong(Integer value) {
        if (value == null) {
            return null;
        } else {
            return value.longValue();
        }
    }

    public static Long toLong(Double value) {
        if (null == value) {
            return null;
        } else {
            return value.longValue();
        }
    }

    public static String toString(Integer value) {
        if (null == value) {
            return null;
        } else {
            return value.toString();
        }
    }

    public static Integer toInteger(Double value) {
        return toInteger(value, false);
    }

    public static Integer toInteger(Double value, boolean round) {
        if (null == value) {
            return null;
        } else {
            return round ? (int) Math.round(value.doubleValue()) : value.intValue();
        }
    }

    public static String toNotNullIntegerString(Double value) {
        String retVal = toIntegerString(value);
        if (retVal != null) {
            return retVal;
        }
        return "";
    }

    public static String toIntegerString(Double value) {
        return toIntegerString(value, false);
    }

    public static String toIntegerString(Long value) {
        return toIntegerString(value, false);
    }

    public static String toIntegerString(String separator, boolean round, Double... doubleList) {
        List<String> newList = new ArrayList<String>();
        for (Double obj : doubleList) {
            if (obj != null) {
                newList.add(toIntegerString(obj, round));
            }
        }
        int lastItemIndx = newList.size() - 1;
        int curentItemIndx = 0;
        StringBuilder builder = new StringBuilder();
        if (lastItemIndx > -1) {
            for (String obj : newList) {
                builder.append(obj);
                if (!(curentItemIndx == lastItemIndx)) {
                    builder.append(separator);
                }
                curentItemIndx++;
            }
        }
        return builder.toString();
    }

    public static String toIntegerString(Double value, boolean round) {
        if (null == value) {
            return null;
        } else if (round) {
            return Integer.toString((int) Math.round(value));
        } else {
            return Integer.toString(value.intValue());
        }
    }

    public static String toIntegerString(Long value, boolean round) {
        if (null == value) {
            return null;
        } else if (round) {
            return Integer.toString((int) Math.round(value));
        } else {
            return Integer.toString(value.intValue());
        }
    }

    public static <E> List<E> removeNulls(List<E> list) {
        if (null == list) {
            return null;
        } else {
            List<E> newList = new ArrayList<E>();
            for (E e : list) {
                if (null != e) {
                    newList.add(e);
                }
            }
            if (newList.size() != 0) {
                return newList;
            } else {
                return null;
            }
        }

    }

    /**
     * Bean lists are considered to equal each other in case the first list contains all the elements 
     * of the second list and vice versa. This mostly works for DB generated bean lists because it
     * relies on <code>.toString()</code> equality checks. 
     * Null lists are considered equal. Lists of different sizes immediately give <code>false</code>
     * @param beanList1 first list of beans
     * @param beanList2 second list of beans
     * @return <code>true</code> if the lists are equal, <code>false</code> otherwise
     */
    public static <E> boolean equalLists(List<E> beanList1, List<E> beanList2) {
        if (beanList1 == null || beanList2 == null) {
            return (beanList1 == null && beanList2 == null);
        }
        if (beanList1.size() != beanList2.size()) {
            return false;
        }
        boolean allEqual = true;
        for (E list1Elem : beanList1) {
            allEqual &= hasEqualInList(list1Elem, beanList2);
        }

        return allEqual;
    }

    /**
     * Checks if the provided DB bean element is contained within the list
     * @param elem the element to check
     * @param elemsList the list of elements
     * @return <code>true</code> if the list contains an element with a matching {@link #toString()}
     * result, or if the element passed is <code>null</code> and the current list elements is <code>null</code>;
     * <code>false</code> otherwise.
     */
    private static <E> boolean hasEqualInList(E elem, List<E> elemsList) {
        for (E listElem : elemsList) {
            if (listElem == null) {
                if (elem == null) {
                    return true;
                } else {
                    continue;
                }
            }
            if (listElem.toString().equals(elem.toString())) {
                return true;
            }
        }
        return false;
    }

    public static String[] toArray(List<String> list) {
        if (null == list) {
            return null;
        } else {
            return list.toArray(new String[list.size()]);
        }
    }

    public static boolean isEmpty(String s) {
        return (s == null || s.isEmpty());
    }

    public static boolean isTrimmedEmpty(String s) {
        boolean withTrim = true;
        return isEmpty(s, withTrim);
    }

    private static boolean isEmpty(String s, boolean withTrim) {
        return (s == null || s.isEmpty() || (withTrim && s.trim().isEmpty()));
    }

    public static boolean isNotEmpty(String s) {
        return (s != null && !s.isEmpty());
    }

    public static boolean isEmpty(String... values) {
        boolean retVal = true;
        if (values != null) {
            for (String value : values) {
                if (!isEmpty(value)) {
                    retVal = false;
                    break;
                }
            }
        }
        return retVal;

    }

    public static boolean isNotEmpty(String... newArray) {
        if (newArray == null) {
            return false;
        }
        for (String obj : newArray) {
            if (isEmpty(obj)) {
                return false;
            }
        }
        return true;
    }

    public static <E> boolean isEmpty(Collection<E> a) {
        return a == null || a.size() == 0;
    }

    public static <E> boolean isEmpty(E[] a) {
        return a == null || a.length == 0;

    }

    /**
     * <code>true</code> if either {@link #isEmpty(Object[])} returns <code>true</code>,
     * or every element in this non-null, non-zero length array is actually <code>null</code>.
     * @param a the array to check
     * @return <code>true</code> if this array is <code>null</code>, has a length of 0, or
     * all of it's elements are <code>null</code>. <code>false</code> otherwise.
     */
    public static <E> boolean isDeepEmpty(E[] a) {
        if (isEmpty(a)) {
            return true;
        } else {
            boolean deepEmpty = true;
            for (E elem : a) {
                deepEmpty &= (elem == null);
                if (!deepEmpty) {
                    return false;
                }
            }
            return deepEmpty;
        }
    }

    public static <E> E firstItem(List<E> values) {
        E retVal = null;
        if (!isEmpty(values)) {
            retVal = values.get(FIRST_ITEM_INDEX);
        }
        return retVal;

    }

    public static <E> boolean isNotEmpty(E[] a) {
        return a != null && a.length != 0;
    }

    public static Double toDouble(Integer value) {
        if (null == value) {
            return null;
        } else {
            return value.doubleValue();
        }
    }

    public static final boolean isEqual(Object o1, Object o2) {
        if (null != o1 && null != o2) {
            return o1.equals(o2);
        } else if ((null == o1 && null != o2) || (null != o1 && null == o2)) {
            return false;
        } else {
            return true;
        }
    }

    public static <T> T[] appendArray(T[] array, T[] newArray, T newItem) {
        int l = array != null ? array.length : 0;
        if (newArray.length != l + 1) {
            throw new IllegalArgumentException("new array size must be old array size plus one");
        }
        if (null != array) {
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
        }
        newArray[l] = newItem;
        return newArray;
    }

    public static String truncateDisplayText(String text, int length) {
        length -= 3;
        if (null != text && text.length() > length) {
            return text.substring(0, length) + "...";
        } else {
            return text;
        }
    }

    public interface FieldAccessor<FIELD_TYPE, BEAN> {

        FIELD_TYPE getFieldValue(BEAN bean);
    }

    public static abstract class BaseFieldAccessor<FIELD_TYPE, BEAN> implements FieldAccessor<FIELD_TYPE, BEAN> {

        public boolean hasFieldValue(BEAN bean) {
            boolean retVal = getFieldValue(bean) != null;
            return retVal;
        }

        public boolean hasFieldValue(BEAN bean, FIELD_TYPE value) {
            boolean retVal = value != null && value.equals(getFieldValue(bean));
            return retVal;
        }

        public abstract FIELD_TYPE getFieldValue(BEAN bean);
    }

    public static <FIELD_TYPE, BEAN> Set<FIELD_TYPE> getFieldSet(List<BEAN> beans, FieldAccessor<FIELD_TYPE, BEAN> fieldAccessor) {
        Set<FIELD_TYPE> retVal = new HashSet<FIELD_TYPE>();

        if (beans != null) {
            for (BEAN bean : beans) {
                FIELD_TYPE fieldValue = fieldAccessor.getFieldValue(bean);
                retVal.add(fieldValue);
            }
        }

        return retVal;
    }

    public static <FIELD_TYPE, BEAN> Set<FIELD_TYPE> getNotNullFieldSet(List<BEAN> beans, FieldAccessor<FIELD_TYPE, BEAN> fieldAccessor) {
        Set<FIELD_TYPE> retVal = new HashSet<FIELD_TYPE>();

        if (beans != null) {
            for (BEAN bean : beans) {
                FIELD_TYPE fieldValue = fieldAccessor.getFieldValue(bean);
                if (fieldValue != null) {
                    retVal.add(fieldValue);
                }
            }
        }

        return retVal;
    }

    public static <FIELD_TYPE, BEAN> List<FIELD_TYPE> getFieldList(List<BEAN> beans, FieldAccessor<FIELD_TYPE, BEAN> fieldAccessor) {
        List<FIELD_TYPE> retVal = new ArrayList<FIELD_TYPE>();
        if (beans != null) {
            for (BEAN bean : beans) {
                FIELD_TYPE fieldValue = fieldAccessor.getFieldValue(bean);
                retVal.add(fieldValue);
            }
        }
        return retVal;
    }

    public static <FIELD_TYPE, BEAN> List<FIELD_TYPE> getNotNullFieldList(List<BEAN> beans, FieldAccessor<FIELD_TYPE, BEAN> fieldAccessor) {
        List<FIELD_TYPE> retVal = new ArrayList<FIELD_TYPE>();
        if (beans != null) {
            for (BEAN bean : beans) {
                FIELD_TYPE fieldValue = fieldAccessor.getFieldValue(bean);
                if (fieldValue != null) {
                    retVal.add(fieldValue);
                }
            }
        }
        return retVal;
    }

    public static <FIELD_TYPE, BEAN> Map<FIELD_TYPE, BEAN> getMapByFieldAccessor(List<BEAN> beans, FieldAccessor<FIELD_TYPE, BEAN> fieldAccessor) {
        Map<FIELD_TYPE, BEAN> retVal = new HashMap<FIELD_TYPE, BEAN>();
        if (beans != null) {
            for (BEAN bean : beans) {
                if (bean != null) {
                    FIELD_TYPE key = fieldAccessor.getFieldValue(bean);
                    if (!retVal.containsKey(key)) {
                        retVal.put(key, bean);
                    }
                }
            }
        }
        return retVal;
    }

    public static <FIELD_TYPE, BEAN> Map<FIELD_TYPE, List<BEAN>> getListMapByFieldAccessor(List<BEAN> beans, FieldAccessor<FIELD_TYPE, BEAN> fieldAccessor) {
        Map<FIELD_TYPE, List<BEAN>> retVal = new HashMap<FIELD_TYPE, List<BEAN>>();
        if (beans != null) {
            for (BEAN bean : beans) {
                if (bean != null) {
                    List<BEAN> tmpProperties = null;
                    FIELD_TYPE key = fieldAccessor.getFieldValue(bean);
                    if (retVal.containsKey(key)) {
                        tmpProperties = retVal.get(key);
                    } else {
                        tmpProperties = new ArrayList<BEAN>();
                        retVal.put(key, tmpProperties);
                    }
                    tmpProperties.add(bean);
                }
            }
        }
        return retVal;
    }

}
