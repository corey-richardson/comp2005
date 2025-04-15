package com.example.comp2005_api;

/* External API uses name 'patientID' where 'ID' is *fully* capitalised, therefore the model also
* needs to use that naming convention - even if it's not preferred. >:(  */

public class Admission
{
    private int id;
    private int patientID;
    private String admissionDate;
    private String dischargeDate;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientID() { return patientID; }
    public void setPatientID(int patientID) { this.patientID = patientID; }

    public String getAdmissionDate() { return admissionDate; };
    public void setAdmissionDate(String admissionDate) { this.admissionDate = admissionDate; }

    public String getDischargeDate() { return dischargeDate; };
    public void setDischargeDate(String dischargeDate) { this.dischargeDate = dischargeDate; }
}
