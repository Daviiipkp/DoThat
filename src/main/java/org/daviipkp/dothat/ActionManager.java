package org.daviipkp.dothat;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.daviipkp.dothat.actions.Action;

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

    private void executeQueueMember(QueueMember q) {
        Action act = actionsMap.get(q.getID());
        if(q.getArgs() != null) {
            try{
                fillAction(act, q.getArgs());
            }catch(Exception e) {
                return; //TODO
            }
        }
        act.execute();
    }

    private void fillAction(Action act, Map<String, Object> map) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException  {
        for(String s : map.keySet()) {
            Field f = act.getClass().getField(s);
            f.setAccessible(true);
            f.set(act, map.get(s));
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
