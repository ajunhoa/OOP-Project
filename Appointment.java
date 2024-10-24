class Appointment {
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String time;
    private String outcome;
    private Prescription prescription;

    public Appointment(Patient patient, Doctor doctor, String date, String time) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    public void setOutcome(String outcome, Prescription prescription) {
        this.outcome = outcome;
        this.prescription = prescription;
    }

    public void cancel() {
        // Code for canceling appointment
    }

    public String getOutcome() {
        return outcome;
    }
}
