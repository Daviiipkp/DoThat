package org.daviipkp.dothat;

import java.util.Map;

import org.daviipkp.dothat.actions.DebugAction;

class Main {
    
    public static void main(String[] args) throws InterruptedException {
        ActionManager ins = ActionManager.getInstance();
        ins.registerAction("debug", new DebugAction());
        Map<String, Object> arguments = Map.of("endpoint", "endpoint", "payload", "payload", "server", "server.getName()", "server_port", 8080);
            
        ins.submit("debug", arguments);
        Thread.sleep(505050);
    }

}
