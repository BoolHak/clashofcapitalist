package com.yumait.clashofcapitalists.models;

import java.io.Serializable;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public class Employee  implements Serializable {

    public static int EMPLOYEE_TYPE_SIMPLE = 0;
    public static int EMPLOYEE_TYPE_TECH = 1;
    public static int EMPLOYEE_TYPE_ENGINEER = 2;

    private String employeeId;
    private int employeeType;
    private double employeeProductivity;
    private double employeeSalary;
    private int employeeLevel;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(int employeeType) {
        this.employeeType = employeeType;
    }

    public double getEmployeeProductivity() {
        return employeeProductivity;
    }

    public void setEmployeeProductivity(double employeeProductivity) {
        this.employeeProductivity = employeeProductivity;
    }

    public double getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(double employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public int getEmployeeLevel() {
        return employeeLevel;
    }

    public void setEmployeeLevel(int employeeLevel) {
        this.employeeLevel = employeeLevel;
    }
}
