    package model;

    public class MedicalRecord {

        private String patientId;
        private String pastDiagnoses;
        private String treatment;

        public MedicalRecord(String patientId, String pastDiagnoses, String treatment) {
            this.patientId = patientId;
            this.pastDiagnoses = pastDiagnoses;
            this.treatment = treatment;
        }

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

    @Override
    public String toString() {
        return "Patient ID: " + patientId + ", Past Diagnoses: " + pastDiagnoses + ", Treatment: " + treatment;
    }
}
