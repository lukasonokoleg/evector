package lt.jmsys.spark.gwt.application.shared.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import lt.jmsys.spark.gwt.application.common.shared.session.SessionInfo;
import eu.itreegroup.spark.application.bean.Classifier;
import eu.itreegroup.spark.application.bean.Setting;
import eu.itreegroup.spark.application.shared.db.bean.Find_char_translations;
import eu.itreegroup.spark.application.shared.db.bean.User_menu;

public class PreloadedSessionData implements Serializable {

    private static final long serialVersionUID = 1L;

    private SessionInfo sessionInfo;

    private HashMap<Setting, String> clientSettings;
    private HashMap<String, ArrayList<Classifier>> classifierMap;
    private ArrayList<User_menu> userMenu;
    private ArrayList<Find_char_translations> charTranslations;

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public HashMap<String, ArrayList<Classifier>> getClassifierMap() {
        return classifierMap;
    }

    public void setClassifierMap(HashMap<String, ArrayList<Classifier>> classifierMap) {
        this.classifierMap = classifierMap;
    }

    public ArrayList<User_menu> getUserMenu() {
        return userMenu;
    }

    public void setUserMenu(ArrayList<User_menu> userMenu) {
        this.userMenu = userMenu;
    }

    public ArrayList<Find_char_translations> getCharTranslations() {
        return charTranslations;
    }

    public void setCharTranslations(ArrayList<Find_char_translations> charTranslations) {
        this.charTranslations = charTranslations;
    }

    public HashMap<Setting, String> getClientSettings() {
        return clientSettings;
    }

    public void setClientSettings(HashMap<Setting, String> clientSettings) {
        this.clientSettings = clientSettings;
    }

}
