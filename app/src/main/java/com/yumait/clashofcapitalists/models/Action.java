package com.yumait.clashofcapitalists.models;

import com.yumait.clashofcapitalists.models.interfaces.IAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public class Action {

    private List<IAction> history = new ArrayList<IAction>();

    public void storeAndExecute(IAction action) {
        history.add(action);
        action.execute();
    }

    public void removeAndUndo(IAction action) {
        history.remove(action);
        action.undo();
    }

    public List<IAction> getHistory() {
        return history;
    }

    public void setHistory(List<IAction> history) {
        this.history = history;
    }
}
