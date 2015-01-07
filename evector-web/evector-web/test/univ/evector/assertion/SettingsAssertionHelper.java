package univ.evector.assertion;

import org.junit.Assert;

import univ.evector.beans.Setting;
import univ.evector.beans.helper.SettingsHelper;

public class SettingsAssertionHelper {

    public static void assertHasName(Setting setting, String name) {
        if (!SettingsHelper.hasName(setting, name)) {
            Assert.fail("Specified setting name is different. setting.name: " + setting.getSet_name() + ", name:" + name);
        }
    }

    public static void assertHasStringValue(Setting setting, String value) {
        if (!SettingsHelper.hasValue(setting, value)) {
            Assert.fail("Specified setting string value is different. setting.value: " + setting.getSet_name() + ", value:" + value);
        }
    }

    public static void assertHasLongValue(Setting setting, Long value) {
        if (!SettingsHelper.hasValue(setting, value)) {
            Assert.fail("Specified setting long value is different. setting.value: " + setting.getSet_name() + ", value:" + value);
        }
    }

    public static void assertHasBooleanValue(Setting setting, Boolean value) {
        if (!SettingsHelper.hasValue(setting, value)) {
            Assert.fail("Specified setting boolean value is different. setting.value: " + setting.getSet_name() + ", value:" + value);
        }
    }

    public static void assertHasDoubleValue(Setting setting, Double value) {
        if (!SettingsHelper.hasValue(setting, value)) {
            Assert.fail("Specified setting double value is different. setting.value: " + setting.getSet_name() + ", value:" + value);
        }
    }

    public static void assertHasIntegerValue(Setting setting, Integer value) {
        if (!SettingsHelper.hasValue(setting, value)) {
            Assert.fail("Specified setting integer value is different. setting.value: " + setting.getSet_name() + ", value:" + value);
        }
    }

}
