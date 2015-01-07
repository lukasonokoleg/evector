package lt.jmsys.spark.gwt.application.shared.security;

public enum Role {
    CMP_ADMIN("CMP_ADMIN"), CR_ADMIN("CR_ADMIN"), CR_FINANCE("CR_FINANCE"), CR_DIRECTOR("CR_DIRECTOR"), CR_TRANSPORT("CR_TRANSPORT"), MF_ADMIN("MF_ADMIN"), MF_PLAN("MF_PLAN"), SYS_ADMIN(
            "SYS_ADMIN"), SYS_FINANCE("SYS_FINANCE"), SYS_DIRECTOR("SYS_DIRECTOR"), SYS_OPER("SYS_OPER"), SYS_QUALITY("SYS_QUALITY"), SYS_TRANS("SYS_TRANSLATOR");

    private String code;

    private Role(String code) {
        this.code = code;
    }
    
    
    public String getCode() {
        return code;
    }

}
