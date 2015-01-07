package lt.jmsys.spark.gwt.application.shared.clsf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.itreegroup.spark.application.bean.Classifier;

public class ClassifierFactory {

    private static ClassifierFactory instance;

    private Map<String, ArrayList<Classifier>> classifiers;

    private ClassifierFactory(Map<String, ArrayList<Classifier>> classifiers) {
        this.classifiers = classifiers;               
    }

    public static void init(Map<String, ArrayList<Classifier>> classifiers) {
        instance = new ClassifierFactory(classifiers);
    }

    public static ClassifierFactory getInstance() {
        if (null == instance) {
            throw new IllegalStateException("init() should be called first");
        }
        return instance;
    }
    
    public List<Classifier> getClassifier(String type, String parentCode) {
        return getClassifier(type + "." + parentCode);
    }

    public List<Classifier> getClassifier(String type) {
        if (null == instance) {
            throw new IllegalStateException("init() should be called first");
        }
        List<Classifier> list = classifiers.get(type);
        if (null == list) {
            //throw new IllegalArgumentException("unkonw classificator type: " + type);
            return new ArrayList<Classifier>();
        }
        return list;
    }

}
