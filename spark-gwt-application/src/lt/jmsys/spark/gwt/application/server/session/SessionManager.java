package lt.jmsys.spark.gwt.application.server.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {

    private Map<String, String> nidBySid = new HashMap<String, String>();
    private Map<String, Session> sessionByNid = Collections.synchronizedMap(new HashMap<String, Session>());
    private Map<Double, Session> sessionByDbSid = Collections.synchronizedMap(new HashMap<Double, Session>());

    private static final SessionManager instance = new SessionManager();

    private SessionManager() {

    }

    public static SessionManager getInstance() {
        return instance;
    }

    public void unregisterSession(String id) {
        synchronized (nidBySid) {
            String nid = nidBySid.get(id);
            if (null != nid) {
                Session s = sessionByNid.get(nid);
                if (null != s) {
                    sessionByDbSid.remove(s.getDbSessionId());
                }
                sessionByNid.remove(nid);
            }
            nidBySid.remove(id);
        }
    }

    /**
     * @param sid - http session id
     * @param nid - notification session id
     * @param uid - user id
     **/
    public void registerSession(String sid, String nid, Double uid, Double companyId, Double dbSessionId) {
        synchronized (nidBySid) {
            nidBySid.put(sid, nid);
            Session s = new Session(nid, uid, companyId, dbSessionId);
            sessionByNid.put(nid, s);
            sessionByDbSid.put(dbSessionId, s);
        }
    }

    public boolean checkNotificationId(String nid) {
        return sessionByNid.containsKey(nid);
    }

    public Double getUserId(String nid) {
        Session s = sessionByNid.get(nid);
        if (null != s) {
            return s.getUid();
        } else {
            return null;
        }
    }

    public Double getCompanyId(String nid) {
        Session s = sessionByNid.get(nid);
        if (null != s) {
            return s.getCid();
        } else {
            return null;
        }
    }
    
    public String getNidByDbSessionId(Double id) {
        Session s = sessionByDbSid.get(id);
        if (null != s) {
            return s.getNid();
        } else {
            return null;
        }
    }

    private static class Session {

        private String nid;
        private Double uid;
        private Double cid;
        private Double dbSessionId;

        public Session(String nid, Double uid, Double cid, Double dbSessionId) {
            super();
            this.nid = nid;
            this.uid = uid;
            this.cid = cid;
            this.dbSessionId = dbSessionId;
        }

        public String getNid() {
            return nid;
        }

        public Double getUid() {
            return uid;
        }

        public Double getCid() {
            return cid;
        }

        public Double getDbSessionId() {
            return dbSessionId;
        }

    }
}
