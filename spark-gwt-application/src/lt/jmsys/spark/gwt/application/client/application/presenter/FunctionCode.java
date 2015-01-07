package lt.jmsys.spark.gwt.application.client.application.presenter;

public enum FunctionCode {
    CM_003, CM_004, CM_005, CR_001, CR_003, CR_004, CR_005, CR_008, CR_009, CR_010, CR_014, CR_016, CR_017, CR_026, CR_028, CR_034, CR_035, CR_038, MF_001, MF_006, MF_008, MF_012, MF_014, MF_015, SYS_001, SYS_002, SYS_006, SYS_0061, SYS_0062, SYS_0063, SYS_0064, SYS_0065, SYS_008, SYS_010, SYS_012, SYS_013, SYS_014, SYS_015, SYS_023, SYS_026, SYS_027, SYS_028, SYS_029, SYS_030, SYS_031, SYS_032, SYS_033, SYS_036, SYS_037, SYS_038, SYS_041, SYS_045, SYS_047, SYS_049, SYS_050, SYS_055, SYS_057, SYS_058, SYS_059, SYS_061, CR_PARTN, CR_INFO;

    public static FunctionCode vallueByCode(String code) {
        if (null == code) {
            return null;
        }
        code = code.toUpperCase().replace('.', '_');
        try {
            return valueOf(code);
        } catch (Throwable e) {
            return null;
        }
    }
}
