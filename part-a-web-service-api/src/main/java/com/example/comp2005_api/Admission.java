package com.example.comp2005_api;

public class Admission
{
    private int id;
    private int patientId;
    private String admissionDate;
    private String dischargeDate;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getAdmissionDate() { return admissionDate; };
    public void setAdmissionDate(String admissionDate) { this.admissionDate = admissionDate; }

    public String getDischargeDate() { return dischargeDate; };
    public void setDischargeDate(String dischargeDate) { this.dischargeDate = dischargeDate; }
}
