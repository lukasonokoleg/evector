package lt.jmsys.spark.gwt.application.common.shared.vvsis.data;

import java.io.Serializable;


public class Paraiska implements Serializable {
    
    private static final long serialVersionUID = 7823603355364452715L;

    //public String id;
    
    public String vardasPavarde;
    public String asmensKodas;
    public String mobilusTelefonas;
    public String elPastas;
    public String kontaktinisAdresas;
    public String naujasAdresas;
    public String namoNr;
    public String korpusoNr;
    public String butoNr;
    public String tikslas = Tikslas.PurposeNewUser1.getText();
    
    public String date;
    public String pastabos;
    
    public String busenosData;
    public String dokumentoNumeris;
    public String dokumentoTipas;
    public String busena;
    public String paraiskosStebesena;
    public Objektas objektas;
    
    public Paraiska() {
        this.objektas = new Objektas();
    }
    
    public Paraiska(//String pId,
            String pVardasPavarde,
            String pAsmensKodas,
            String pMobilusTelefonas,
            String pElPastas,
            String pKontaktinisAdresas,
            String pNaujasAdresas,
            String pNamoNr,
            String pKorpusoNr,
            String pButoNr,
            String pTikslas,
            String pDate,
            String pPastabos,
            String pBusenosData, String pDokumentoNumeris, String pDokumentoTipas, String pBusena, String pParaiskosStebesena, Objektas pObjektas) {
        
        //this.id = pId;
        this.vardasPavarde = pVardasPavarde;
        this.asmensKodas = pAsmensKodas;
        this.mobilusTelefonas = pMobilusTelefonas;
        this.elPastas = pElPastas;
        this.kontaktinisAdresas = pKontaktinisAdresas;
        this.naujasAdresas = pNaujasAdresas;
        this.namoNr = pNamoNr;
        this.korpusoNr = pKorpusoNr;
        this.butoNr = pButoNr;
        this.tikslas = pTikslas;
        this.date = pDate;
        this.pastabos = pPastabos;
        
        this.busenosData = pBusenosData;
        this.dokumentoNumeris = pDokumentoNumeris;
        this.dokumentoTipas = pDokumentoTipas;
        this.busena = pBusena;
        this.paraiskosStebesena = pParaiskosStebesena;
        this.objektas = pObjektas;
    }

    
//    public String getId() {
//        return id;
//    }
//
//    
//    public void setId(String id) {
//        this.id = id;
//    }

    
    
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

    
    public String getNaujasAdresas() {
        return naujasAdresas;
    }

    
    public void setNaujasAdresas(String naujasAdresas) {
        this.naujasAdresas = naujasAdresas;
    }

    
    public String getNamoNr() {
        return namoNr;
    }

    
    public void setNamoNr(String namoNr) {
        this.namoNr = namoNr;
    }

    
    public String getKorpusoNr() {
        return korpusoNr;
    }

    
    public void setKorpusoNr(String korpusoNr) {
        this.korpusoNr = korpusoNr;
    }

    
    public String getButoNr() {
        return butoNr;
    }

    
    public void setButoNr(String butoNr) {
        this.butoNr = butoNr;
    }

    
    
    
    public String getTikslas() {
        return tikslas;
    }

    
    public void setTikslas(String tikslas) {
        this.tikslas = tikslas;
    }
    
    
    

    
    public String getDate() {
        return date;
    }

    
    public void setDate(String date) {
        this.date = date;
    }

    
    public String getPastabos() {
        return pastabos;
    }

    
    public void setPastabos(String pastabos) {
        this.pastabos = pastabos;
    }

    public String getBusenosData() {
        return busenosData;
    }

    
    public void setBusenosData(String busenosData) {
        this.busenosData = busenosData;
    }

    
    public String getDokumentoNumeris() {
        return dokumentoNumeris;
    }

    
    public void setDokumentoNumeris(String dokumentoNumeris) {
        this.dokumentoNumeris = dokumentoNumeris;
    }

    
    public String getDokumentoTipas() {
        return dokumentoTipas;
    }

    
    public void setDokumentoTipas(String dokumentoTipas) {
        this.dokumentoTipas = dokumentoTipas;
    }

    
    public String getBusena() {
        return busena;
    }

    
    public void setBusena(String busena) {
        this.busena = busena;
    }

    
    public String getParaiskosStebesena() {
        return paraiskosStebesena;
    }

    
    public void setParaiskosStebesena(String paraiskosStebesena) {
        this.paraiskosStebesena = paraiskosStebesena;
    }

    
    public Objektas getObjektas() {
        return objektas;
    }

    
    public void setObjektas(Objektas objektas) {
        this.objektas = objektas;
    }
    
    
}
