package lt.jmsys.spark.gwt.application.common.shared.vvsis.data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class Klientas implements Serializable {
    
    private static final long serialVersionUID = 8336454229844613664L;
    
    public String id;
    public String loginName;
    public String vardasPavarde;
    public String asmensKodas;
    public String mobilusTelefonas;
    public Boolean beMobilaus;
    public String elPastas;
    public String kontaktinisAdresas;

    public List<Paraiska> paraiskos = new LinkedList<Paraiska>();
    public List<Objektas> objektai = new LinkedList<Objektas>();
    
    public Klientas() {}
    
    public Klientas(String pId, String pLoginName, String pVardasPavarde, String pAsmensKodas, String pMobilusTelefonas, Boolean pBeMobilaus, String pElPastas, String pKontaktinisAdresas) {
        this.id = pId;
        this.loginName = pLoginName;
        this.vardasPavarde = pVardasPavarde;
        this.asmensKodas = pAsmensKodas;
        this.mobilusTelefonas = pMobilusTelefonas;
        this.beMobilaus = pBeMobilaus;
        this.elPastas = pElPastas;
        this.kontaktinisAdresas = pKontaktinisAdresas;
    }

    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id;
    }

    
    public String getLoginName() {
        return loginName;
    }

    
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    
    public String getVardasPavarde() {
        return vardasPavarde;
    }

    
    public void setVardasPavarde(String vardasPavarde) {
        this.vardasPavarde = vardasPavarde;
    }

    
    public String getAsmensKodas() {
        return asmensKodas;
    }

    
    public void setAsmensKodas(String asmensKodas) {
        this.asmensKodas = asmensKodas;
    }

    
    public String getMobilusTelefonas() {
        return mobilusTelefonas;
    }

    
    public void setMobilusTelefonas(String mobilusTelefonas) {
        this.mobilusTelefonas = mobilusTelefonas;
    }

    
    public Boolean getBeMobilaus() {
        return beMobilaus;
    }

    
    public void setBeMobilaus(Boolean beMobilaus) {
        this.beMobilaus = beMobilaus;
    }

    
    public String getElPastas() {
        return elPastas;
    }

    
    public void setElPastas(String elPastas) {
        this.elPastas = elPastas;
    }

    
    public String getKontaktinisAdresas() {
        return kontaktinisAdresas;
    }

    
    public void setKontaktinisAdresas(String kontaktinisAdresas) {
        this.kontaktinisAdresas = kontaktinisAdresas;
    }

    
    public List<Paraiska> getParaiskos() {
        return paraiskos;
    }

    
    public void setParaiskos(List<Paraiska> paraiskos) {
        this.paraiskos = paraiskos;
    }

    
    public List<Objektas> getObjektai() {
        return objektai;
    }

    
    public void setObjektai(List<Objektas> objektai) {
        this.objektai = objektai;
    }
    
    
}
