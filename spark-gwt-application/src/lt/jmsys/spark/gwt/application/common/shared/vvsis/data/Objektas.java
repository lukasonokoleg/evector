package lt.jmsys.spark.gwt.application.common.shared.vvsis.data;

import java.io.Serializable;


public class Objektas implements Serializable {
    
    private static final long serialVersionUID = 5454566334351885598L;

    public String id;
    public String pavadinimas;
    public String adresas;
    public Boolean beAdreso = Boolean.FALSE;
    public Boolean buitinisKlientas = Boolean.TRUE;
    
    public Objektas() {}
    
    public Objektas(String pId, String pPavadinimas, String pAdresas, Boolean pBeAdreso, Boolean pBuitinisKlientas) {
        this.id = pId;
        this.pavadinimas = pPavadinimas;
        this.adresas = pAdresas;
        this.beAdreso = pBeAdreso;
        this.buitinisKlientas = pBuitinisKlientas;
    }

    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id;
    }

    
    public String getPavadinimas() {
        return pavadinimas;
    }

    
    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    
    public String getAdresas() {
        return adresas;
    }

    
    public void setAdresas(String adresas) {
        this.adresas = adresas;
    }

    
    public Boolean getBeAdreso() {
        return beAdreso;
    }

    
    public void setBeAdreso(Boolean beAdreso) {
        this.beAdreso = beAdreso;
    }

    
    public Boolean getBuitinisKlientas() {
        return buitinisKlientas;
    }

    
    public void setBuitinisKlientas(Boolean buitinisKlientas) {
        this.buitinisKlientas = buitinisKlientas;
    }
    
    
}
