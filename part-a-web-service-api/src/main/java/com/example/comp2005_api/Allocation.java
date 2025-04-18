package com.example.comp2005_api;

public class Allocation
{
    private int id;
    private int admissionId;
    private int employeeId;
    private String startTime;
    private String endTime;

    public Allocation() {
        // No argument constructor
    }

    public Allocation(int id, int admissionId, int employeeId, String startTime, String endTime) {
        this.id = id;
        this.admissionId = admissionId;
        this.employeeId = employeeId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAdmissionId() { return admissionId; }
    public void setAdmissionId(int admissionId) { this.admissionId = admissionId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
}
