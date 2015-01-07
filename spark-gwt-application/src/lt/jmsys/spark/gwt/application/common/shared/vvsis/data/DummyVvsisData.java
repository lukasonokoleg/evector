package lt.jmsys.spark.gwt.application.common.shared.vvsis.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class DummyVvsisData {
    
    private Map<String, Klientas> zinomiKlientai = new HashMap<String, Klientas>();
    
    // Map<savininkoKodas, Map<paraiskosNumeris, paraiska>>
    private Map<String, Map<String, Paraiska>> zinomosParaiskos = new HashMap<String, Map<String, Paraiska>>();

    // Map<savininkoKodas, Map<objektoNumeris, objektas>>
    private Map<String, Map<String, Objektas>> zinomiObjektai = new HashMap<String, Map<String, Objektas>>();
    

    {
        final String TADO_KODAS = "13761309";
        
        Map<String, Objektas> TADO_OBJEKTAI = new HashMap<String, Objektas>();
        Objektas o = new Objektas("12120208", "objekto pavadinimas", "Ozo g. 30-90, Vilnius, Vilniaus m. sav.", Boolean.FALSE, Boolean.TRUE);
        TADO_OBJEKTAI.put(o.id, o);
        
        Map<String, Paraiska> TADO_PARAISKOS = new HashMap<String, Paraiska>();
        Paraiska p = new Paraiska(//"1234567",
                "TADAS BAKUTIS", "38311160063", "612398765", "tadas@gmail.com", "Ozo 30-90, 07150 Vilnius", "", "", "", "", "Naujas vartotojas", "", "",
                "2013-01-02", "1234567", "Naujas vartotojas", "Užregistruota", "", o);
        TADO_PARAISKOS.put(p.dokumentoNumeris, p);
        
        zinomiKlientai.put("tadas", new Klientas(TADO_KODAS, "tadas", "TADAS BAKUTIS", "38311160011", "861060011", Boolean.FALSE, "tadas.bakutis@gmail.com", "Ozo 30-40, 07150 Vilnius"));
        zinomosParaiskos.put(TADO_KODAS, TADO_PARAISKOS);
        zinomiObjektai.put(TADO_KODAS, TADO_OBJEKTAI);
    }
    
    
    private Klientas pasirinktasKlientas = null;
    
    
    public Klientas login(String pUsername, String pPassword) {
        this.pasirinktasKlientas = pUsername == null 
                ? null 
                : zinomiKlientai.get(pUsername.toLowerCase());
        
        return this.pasirinktasKlientas;
    }
    
    public void logout() {
        this.pasirinktasKlientas = null;
    }
    
    public Klientas getKlientas() {
        return this.pasirinktasKlientas;
    }
    
    public Paraiska getParaiska(String pParaiskosNumeris, String pSavininkoKodas) {
        Map<String, Paraiska> m = this.zinomosParaiskos.get(pSavininkoKodas);
        if (m == null) {
            return null;
        }
        return m.get(pParaiskosNumeris);
    }
    
    public List<Paraiska> getParaiskos() {
        if (this.pasirinktasKlientas == null) {
            throw new IllegalStateException();
        }
        List<Paraiska> result = new LinkedList<Paraiska>();
        Map<String, Paraiska> p = this.zinomosParaiskos.get(this.pasirinktasKlientas.id);
        if (p != null) {
            result.addAll(p.values());
        }
        return result;
    }
    
    public List<Objektas> getObjektai() {
        if (this.pasirinktasKlientas == null) {
            throw new IllegalStateException();
        }
        List<Objektas> result = new LinkedList<Objektas>();
        Map<String, Objektas> p = this.zinomiObjektai.get(this.pasirinktasKlientas.id);
        if (p != null) {
            result.addAll(p.values());
        }
        return result;
    }
    
    public void saveParaiska(Paraiska pParaiska) {
        if (this.pasirinktasKlientas == null) {
            saveParaiska("123456", pParaiska);
        } else {
            saveParaiska(this.pasirinktasKlientas.id, pParaiska);
        }
    }
    
    private void saveParaiska(String pSavininkoKodas, Paraiska pParaiska) {
        Map<String, Paraiska> m = this.zinomosParaiskos.get(pSavininkoKodas);
        if (m == null) {
            m = new HashMap<String, Paraiska>();
            this.zinomosParaiskos.put(pSavininkoKodas, m);
        }
        m.put(pParaiska.dokumentoNumeris, pParaiska);
    }
    
}
