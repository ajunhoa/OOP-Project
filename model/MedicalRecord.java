package model;

public class MedicalRecord {

    private String patientId;        // Patient ID (linked to the Patient class)
    private String pastDiagnoses;    // Stores the past medical diagnoses of the patient
    private String treatment;        // Stores the treatment information

    // Constructor to initialize the medical record with necessary details
    public MedicalRecord(String patientId, String pastDiagnoses, String treatment) {
        this.patientId = patientId;
        this.pastDiagnoses = pastDiagnoses;
        this.treatment = treatment;
    }

    // Getters and Setters for the fields
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPastDiagnoses() {
        return pastDiagnoses;
    }

    public void setPastDiagnoses(String pastDiagnoses) {
        this.pastDiagnoses = pastDiagnoses;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    // Method to display the medical record details
    public void displayMedicalRecord() {
        System.out.println("=== Medical Record ===");
        System.out.println("Patient ID: " + patientId);
        System.out.println("Past Diagnoses: " + pastDiagnoses);
        System.out.println("Treatment: " + treatment);
    }

    // Overriding toString method to represent the medical record in a human-readable format
    @Override
    public String toString() {
        return "Patient ID: " + patientId + ", Past Diagnoses: " + pastDiagnoses + ", Treatment: " + treatment;
    }
}
