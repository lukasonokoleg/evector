package univ.evector.gwt.client.helper;

import lt.jmsys.spark.gwt.client.ui.button.ButtonHelper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class EvectorButtonHelper extends ButtonHelper {

    private static EvectorButtonHelper instance = GWT.create(EvectorButtonHelper.class);

    public static EvectorButtonHelper getInstance() {
        return instance;
    }

    /** icon style name suffix */
    protected interface IconName extends ButtonHelper.IconName {

        String STEP1 = ICON_SET_2 + "Step1";

        String STEP2 = ICON_SET_2 + "Step2";

        String STEP3 = ICON_SET_2 + "Step3";
    }

    public Widget makeStepOneIcon(Widget anchor) {
        return makeAnchor(null, anchor, IconName.STEP1, IconStyle.INHERITED);
    }

    public Widget makeStepTwoIcon(Widget anchor) {
        return makeAnchor(null, anchor, IconName.STEP2, IconStyle.INHERITED);
    }

    public Widget makeStepThreeIcon(Widget anchor) {
        return makeAnchor(null, anchor, IconName.STEP3, IconStyle.INHERITED);
    }

}

