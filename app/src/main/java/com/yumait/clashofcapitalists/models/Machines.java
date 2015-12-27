package com.yumait.clashofcapitalists.models;

        import java.io.Serializable;
        import java.util.List;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public class Machines  implements Serializable {

    private String machineId;
    private int machineLevel;
    private List<Employee> machineEmployees;

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public int getMachineLevel() {
        return machineLevel;
    }

    public void setMachineLevel(int machineLevel) {
        this.machineLevel = machineLevel;
    }

    public List<Employee> getMachineEmployees() {
        return machineEmployees;
    }

    public void setMachineEmployees(List<Employee> machineEmployees) {
        this.machineEmployees = machineEmployees;
    }
}
