package lt.jmsys.spark.gwt.application.common.shared.vvsis.data;


public enum Tikslas {
    PurposeNewUser1("Naujas objekto savininkas"),
    PurposeNewUser2("Naujas vartotojas"),
    PurposeNewUser3("Laikinas vartotojas"),
    PurposeNewUser4("Nutraukti sutartį"),
    PurposeNewUser5("Keisti sutarties sąlygas"),
    PurposeNewUser6("Skaitiklių gedimas");
    
    private String text;
    
    Tikslas(String pText) {
        this.text = pText;
    }
    
    public String getText() {
        return this.text;
    }
}
