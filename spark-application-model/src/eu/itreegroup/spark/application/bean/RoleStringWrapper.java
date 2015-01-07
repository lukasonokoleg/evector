package eu.itreegroup.spark.application.bean;

public class RoleStringWrapper implements Role {

    final String name;

    public RoleStringWrapper(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }
}
