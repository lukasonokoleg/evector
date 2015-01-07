package lt.jmsys.spark.gwt.application.shared.clsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.jmsys.spark.gwt.application.client.common.field.view.ClassifierListBox;
import lt.jmsys.spark.gwt.application.shared.bean.ClassifierBean;
import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import lt.jmsys.spark.gwt.client.ui.form.field.ListField;
import eu.itreegroup.spark.application.bean.Classifier;

public class ClassifierHelper {

    public static Classifier getClassifierValueByCode(String clsfCode, String clsfDomain) {
        Classifier retVal = null;
        String csfValue = null;
        if (!ConversionHelper.isEmpty(clsfCode)) {
            List<Classifier> clsfList = ClassifierHelper.createClassifierList(clsfDomain);
            for (Classifier obj : clsfList) {
                csfValue = obj.getCode();
                if (!ConversionHelper.isEmpty(csfValue)) {
                    if (clsfCode.equals(csfValue)) {
                        return obj;
                    }
                }
            }
        }
        return retVal;
    }

    public static String getClassifierValue(String clsfCode, String clsfDomain) {
        String retVal = null;
        String csfValue = null;
        if (!ConversionHelper.isEmpty(clsfCode)) {
            List<Classifier> clsfList = ClassifierHelper.createClassifierList(clsfDomain);
            for (Classifier obj : clsfList) {
                csfValue = obj.getCode();
                if (!ConversionHelper.isEmpty(csfValue)) {
                    if (clsfCode.equals(csfValue)) {
                        return obj.getDisplayValue();
                    }
                }
            }
        }
        return retVal;
    }

    private static List<Classifier> filterClassifiers(List<Classifier> list, List<String> removeValues) {
        List<Classifier> retVal = new ArrayList<Classifier>();

        if (list != null) {
            for (Classifier clsf : list) {
                if (clsf != null) {
                    String value = clsf.getCode();
                    if (value != null) {
                        if (!removeValues.contains(value)) {
                            retVal.add(clsf);
                        }
                    }
                }
            }
        }
        return retVal;
    }

    public static ClassifierListBox<Classifier> createListbox(String domain, List<String> removeValues) {
        List<Classifier> clsfList = ClassifierFactory.getInstance().getClassifier(domain);
        clsfList = filterClassifiers(clsfList, removeValues);
        ClassifierListBox<Classifier> field = new ClassifierListBox<Classifier>(clsfList);
        return field;
    }

    public static ClassifierListBox<Classifier> createListbox(String domain, List<String> removeValues, boolean addEmptyItem) {
        List<Classifier> clsfList = ClassifierFactory.getInstance().getClassifier(domain);
        clsfList = filterClassifiers(clsfList, removeValues);
        ClassifierListBox<Classifier> field = new ClassifierListBox<Classifier>(clsfList, addEmptyItem);
        return field;
    }

    public static ClassifierListBox<Classifier> createListbox(String domain) {
        ClassifierListBox<Classifier> field = new ClassifierListBox<Classifier>(ClassifierFactory.getInstance().getClassifier(domain));
        return field;
    }

    public static ClassifierListBox<Classifier> createListbox(List<Classifier> values, boolean addEmptyItem) {
        ClassifierListBox<Classifier> field = new ClassifierListBox<Classifier>(values, addEmptyItem);
        return field;
    }

    public static List<Classifier> createClassifierList(String domain) {
        return ClassifierFactory.getInstance().getClassifier(domain);
    }

    public static ClassifierListBox<Classifier> createListbox(String domain, ListField listField) {
        ClassifierListBox<Classifier> field = new ClassifierListBox<Classifier>(listField, ClassifierFactory.getInstance().getClassifier(domain));
        return field;
    }

    public static ClassifierListBox<Classifier> createListbox(String domain, boolean addEmptyItem) {
        ClassifierListBox<Classifier> field = new ClassifierListBox<Classifier>(ClassifierFactory.getInstance().getClassifier(domain), addEmptyItem);
        return field;
    }

    public static <E extends Classifier> Map<String, E> getValuesMap(List<E> values) {
        Map<String, E> valuesByCode = new HashMap<String, E>();
        if (null != values) {
            for (E v : values) {
                valuesByCode.put(v.getCode(), v);
            }
        }
        return valuesByCode;
    }

    public static Classifier createClassifier(Double id, String description) {
        return new ClassifierBean(ConversionHelper.toIntegerString(id), description);
    }

}
