package org.daviipkp.dothat;

import java.util.HashMap;
import java.util.Map;

class QueueMember {

    private String id;
    private Map<String, Object> args = null;

    public QueueMember(String id) {
        this.id = id;
    }

    public QueueMember(String id, ActionArgument[] l) {
        this.id = id;
        args = new HashMap<>();
        for(ActionArgument arg : l) {
            args.put(arg.getName(), arg.getValue());
        }
    }

    public QueueMember(String id, Map<String, Object> l) {
        this.id = id;
        args = l;
    }

    public String getID() {
        return id;
    }

    public Map<String, Object> getArgs() {
        return args;
    }
    
}
