package org.daviipkp.dothat.actions;

public class DebugAction extends Action {

    private String payload;
    private String endpoint;
    private String server;
    private int server_port;

    @Override
    public void execute() {
        System.out.println();
        System.out.println("On server " + server + ", port " + server_port + " and endpoint " + endpoint + " received the payload: ");
        System.out.println(payload);
    }
    
}
