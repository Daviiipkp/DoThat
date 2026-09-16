package org.daviipkp.dothat;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.daviipkp.dothat.actions.Action;
import org.daviipkp.textrie.Textrie;

public class ActionManager {

    private static ActionManager instance;
    private static Map<String,Action> actionsMap;
    private static Thread executor;
    private static Queue<QueueMember> queue;

    private ActionManager() {
        actionsMap = new HashMap<>();
        queue = new LinkedList<>();
        executor = Thread.ofVirtual().name("do_that-executor").start(() -> {
            while(!executor.isInterrupted()){
                if(!queue.isEmpty()) {
                    executeQueueMember(queue.poll());
                }else{
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
    }

    public void setDebug(boolean debug) {
        Textrie.setDebug(debug);
    }

    public Set<String> listActions() {
        return actionsMap.keySet();
    }

    private void executeQueueMember(QueueMember q) {
        Action act = actionsMap.get(q.getID());
        Textrie.debug("Received execution of action with id " + q.getID());
        if(q.getArgs() != null) {
            fillAction(act, q.getArgs());
            Textrie.debug("Filled action with arguments...");
        }
        act.execute();
    }

    private void fillAction(Action act, Map<String, Object> map) {
        for(String s : map.keySet()) {
            try{
                Field f = act.getClass().getDeclaredField(s);
                f.setAccessible(true);
                f.set(act, map.get(s));
            }catch(Throwable e) {
                Textrie.debug("Exception catch filling fields: " + e.getMessage());
            }
        }
    }
    
    public static ActionManager getInstance() {
        if(instance == null) {
            instance = new ActionManager();
        }
        return instance;
    }

    

    public void submit(String actionID) {
        if(actionID != null && !actionID.isBlank()) {
            for(String ac : actionsMap.keySet()) {
                if(actionID.equals(ac)) {
                    queue.add(new QueueMember(actionID));
                }
            }
        }
    }

    public void submit(String actionID, ActionArgument... arguments) {
        if(actionID != null && !actionID.isBlank()) {
            for(String ac : actionsMap.keySet()) {
                if(actionID.equals(ac)) {
                    queue.add(new QueueMember(actionID, arguments));
                }
            }
        }
    }

    public void submit(String actionID, Map<String, Object> arguments) {
        if(actionID != null && !actionID.isBlank()) {
            for(String ac : actionsMap.keySet()) {
                if(actionID.equals(ac)) {
                    queue.add(new QueueMember(actionID, arguments));
                }
            }
        }
    }

    public <T extends Action> void registerAction(String id, T action) {
        actionsMap.put(id, action);
    }

}
