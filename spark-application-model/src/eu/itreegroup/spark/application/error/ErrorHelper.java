package eu.itreegroup.spark.application.error;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.Spr_user_message;

public class ErrorHelper {

    /**
     * Throws new business exception with certain error code
     * @param errorCode
     * @return
     */
    public static void throwBussinessException(ErrorCode errorCode) throws SparkBusinessException {
        throwBussinessException(errorCode, null, null, null, null, null);
    }

    public static void throwBussinessException(ErrorCode errorCode, String p1) throws SparkBusinessException {
        throwBussinessException(errorCode, p1, null, null, null, null);
    }

    public static void throwBussinessException(ErrorCode errorCode, String p1, String p2) throws SparkBusinessException {
        throwBussinessException(errorCode, p1, p2, null, null, null);
    }

    public static void throwBussinessException(ErrorCode errorCode, String p1, String p2, String p3) throws SparkBusinessException {
        throwBussinessException(errorCode, p1, p2, p3, null, null);
    }

    public static void throwBussinessException(ErrorCode errorCode, String p1, String p2, String p3, String p4) throws SparkBusinessException {
        throwBussinessException(errorCode, p1, p2, p3, p4, null);
    }

    public static void throwBussinessException(ErrorCode errorCode, String p1, String p2, String p3, String p4, String p5) throws SparkBusinessException {
        throw exception("E", errorCode.asString(), p1, p2, p3, p4, p5);
    }

    /**
     * Throws new login (authentication/session) exception with certain error code
     * @param loginErrorWrongUsernamePassword
     * @return
     */
    public static void throwLoginException(LoginErrorCode errorCode) throws SparkBusinessException {
        throwLoginException(errorCode, null, null, null, null, null);
    }

    public static void throwLoginException(LoginErrorCode errorCode, String p1) throws SparkBusinessException {
        throwLoginException(errorCode, p1, null, null, null, null);
    }

    public static void throwLoginException(LoginErrorCode errorCode, String p1, String p2) throws SparkBusinessException {
        throwLoginException(errorCode, p1, p2, null, null, null);
    }

    public static void throwLoginException(LoginErrorCode errorCode, String p1, String p2, String p3) throws SparkBusinessException {
        throwLoginException(errorCode, p1, p2, p3, null, null);
    }

    public static void throwLoginException(LoginErrorCode errorCode, String p1, String p2, String p3, String p4) throws SparkBusinessException {
        throwLoginException(errorCode, p1, p2, p3, p4, null);
    }

    public static void throwLoginException(LoginErrorCode errorCode, String p1, String p2, String p3, String p4, String p5) throws SparkBusinessException {
        throw exception("E", errorCode.asString(), p1, p2, p3, p4, p5);
    }

    /**
     * Throws new warning with certain error code
     * @param errorCode
     * @return
     */
    public static void throwBussinessWarning(ErrorCode errorCode) throws SparkBusinessException {
        throwBussinessWarning(errorCode, null, null, null, null, null);
    }

    public static void throwBussinessWarning(ErrorCode errorCode, String p1) throws SparkBusinessException {
        throwBussinessWarning(errorCode, p1, null, null, null, null);
    }

    public static void throwBussinessWarning(ErrorCode errorCode, String p1, String p2) throws SparkBusinessException {
        throwBussinessWarning(errorCode, p1, p2, null, null, null);
    }

    public static void throwBussinessWarning(ErrorCode errorCode, String p1, String p2, String p3) throws SparkBusinessException {
        throwBussinessWarning(errorCode, p1, p2, p3, null, null);
    }

    public static void throwBussinessWarning(ErrorCode errorCode, String p1, String p2, String p3, String p4) throws SparkBusinessException {
        throwBussinessWarning(errorCode, p1, p2, p3, p4, null);
    }

    public static void throwBussinessWarning(ErrorCode errorCode, String p1, String p2, String p3, String p4, String p5) throws SparkBusinessException {
        throw exception("W", errorCode.asString(), p1, p2, p3, p4, p5);
    }

    private static SparkBusinessException exception(String severity, String errorCode, String p1, String p2, String p3, String p4, String p5) {
        Spr_user_message message = new Spr_user_message();
        message.setCode(errorCode);
        message.setSeverity(severity);
        message.setParam1(p1);
        message.setParam2(p2);
        message.setParam3(p3);
        message.setParam4(p4);
        message.setParam5(p5);
        return new SparkBusinessException(message);
    }

    public static boolean hasErrorCode(SparkBusinessException e, ErrorCode errorCode) {
        Spr_user_message[] messages = e.getMessages();
        for (Spr_user_message message : messages) {
            if (message.getCode().equals(errorCode.asString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasErrorCode(SparkBusinessException e, LoginErrorCode errorCode) {
        Spr_user_message[] messages = e.getMessages();
        for (Spr_user_message message : messages) {
            if (message.getCode().equals(errorCode.asString())) {
                return true;
            }
        }
        return false;
    }
    
    //TODO: kodėl SparkBusinessException'u stack trace nesimato klaidu kodu???
   // Pvz.: ERR:S:20140718125236:473964 lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException

}
