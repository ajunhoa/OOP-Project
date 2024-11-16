package model;

/**
 * The MedicalRecord class represents a patient's medical record in the hospital management system.
 * It contains information about the patient's past diagnoses and treatments.
 */
public class MedicalRecord {

    /** The ID of the patient associated with this medical record. */
    private String patientId;

    /** The past diagnoses of the patient. */
    private String pastDiagnoses;

    /** The treatment history of the patient. */
    private String treatment;

    /**
     * Constructs a MedicalRecord instance with the specified patient ID, past diagnoses, and treatment.
     *
     * @param patientId The ID of the patient.
     * @param pastDiagnoses The past diagnoses of the patient.
     * @param treatment The treatment history of the patient.
     */
    public MedicalRecord(String patientId, String pastDiagnoses, String treatment) {
        this.patientId = patientId;
        this.pastDiagnoses = pastDiagnoses;
        this.treatment = treatment;
    }

    /**
     * Gets the patient ID associated with this medical record.
     *
     * @return The patient ID.
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Sets the patient ID for this medical record.
     *
     * @param patientId The new patient ID to set.
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * Gets the past diagnoses of the patient.
     *
     * @return The past diagnoses.
     */
    public String getPastDiagnoses() {
        return pastDiagnoses;
    }

    /**
     * Sets the past diagnoses for the patient.
     *
     * @param pastDiagnoses The new past diagnoses to set.
     */
    public void setPastDiagnoses(String pastDiagnoses) {
        this.pastDiagnoses = pastDiagnoses;
    }

    /**
     * Gets the treatment history of the patient.
     *
     * @return The treatment history.
     */
    public String getTreatment() {
        return treatment;
    }

    /**
     * Sets the treatment history for the patient.
     *
     * @param treatment The new treatment history to set.
     */
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    /**
     * Displays the medical record details for the specified patient.
     *
     * @param patient The Patient object representing the patient whose medical record is to be displayed.
     */
    public void displayMedicalRecord(Patient patient) {
        System.out.println("=== Medical Record ===");
        System.out.println("Patient ID: " + patient.getId());
        System.out.println("Name: " + patient.getName());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Contact Information: " + patient.getContactInfo() + ", Phone Number: " + patient.getContactNumber());
        System.out.println("Blood Type: " + patient.getBloodType());
        System.out.println("Past Diagnoses: " + pastDiagnoses);
        System.out.println("Treatment: " + treatment);
    }

    /**
     * Returns a string representation of the medical record.
     *
     * @return A string containing the patient ID, past diagnoses, and treatment.
     */
    @Override
    public String toString() {
        return "Patient ID: " + patientId + ", Past Diagnoses: " + pastDiagnoses + ", Treatment: " + treatment;
    }
}