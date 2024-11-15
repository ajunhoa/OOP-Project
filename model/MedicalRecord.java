package model;

public class MedicalRecord {

    private String pastDiagnoses;
    private String treatment;

    public MedicalRecord(String pastDiagnoses, String treatment) {
        this.pastDiagnoses = pastDiagnoses;
        this.treatment = treatment;
    }

    public String getPastDiagnoses() {
        return pastDiagnoses;
    }

    public String getTreatment() {
        return treatment;
    }

    @Override
    public String toString() {
        return "Past Diagnoses: " + pastDiagnoses + ", Treatment: " + treatment;
    }
}
