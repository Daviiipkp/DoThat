package org.daviipkp.dothat.actions;

public class SimpleAction extends Action {

    private Runnable runnable;

    public SimpleAction(Runnable r) {
        this.runnable = r;
    }

    @Override
    public void execute() {
        if(runnable != null) {
            runnable.run();
        }
    }

    public void setRunnable(Runnable r) {
        this.runnable = r;
    }

}
