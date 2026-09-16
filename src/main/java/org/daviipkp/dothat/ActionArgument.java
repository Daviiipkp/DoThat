package org.daviipkp.dothat;

public class ActionArgument {
    
    private String name;
    private Object value;

    public ActionArgument(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    

}
