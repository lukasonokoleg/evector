package lt.jmsys.spark.gwt.application.shared.helper;

import java.math.BigDecimal;

public class BigDecimalHelper {

    public static BigDecimal nullAsZero(BigDecimal value) {
        BigDecimal retVal = value;
        retVal = retVal != null ? retVal : new BigDecimal(0L);
        return retVal;
    }

}
