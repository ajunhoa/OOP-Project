class MedicalRecord {
    private String diagnosis;
    private String treatment;
    private Prescription prescription;

    public void addDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void addTreatment(String treatment) {
        this.treatment = treatment;
    }

    public void addPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    @Override
    public String toString() {
        return "Diagnosis: " + diagnosis + "\nTreatment: " + treatment + "\nPrescription: " + prescription;
    }
}
