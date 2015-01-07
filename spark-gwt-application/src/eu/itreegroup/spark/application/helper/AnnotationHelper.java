package eu.itreegroup.spark.application.helper;

import java.lang.annotation.Annotation;

public class AnnotationHelper {

    public static Annotation getAnnotation(Annotation[][] annotations, int index, Class<? extends Annotation> annotationClass) {
        if (null != annotations && index < annotations.length) {
            Annotation[] aa = annotations[index];
            if (null != aa) {
                for (Annotation a : aa) {
                    if (annotationClass.isAssignableFrom(a.annotationType())) {
                        return a;
                    }
                }
            }
        }
        return null;
    }
}
