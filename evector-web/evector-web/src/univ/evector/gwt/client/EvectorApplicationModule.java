package univ.evector.gwt.client;

import lt.jmsys.spark.gwt.application.common.client.service.HasApplicationModuleName;

public enum EvectorApplicationModule implements HasApplicationModuleName {

    MAIN("index"), LOGIN("login");

    private String name;

    private EvectorApplicationModule(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
