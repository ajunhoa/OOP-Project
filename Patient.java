public class Patient extends User {
    private String patientID;
    private String bloodType;
    private MedicalRecord medicalRecord;

    public Patient(String userID, String name, String patientID, String bloodType) {
        super(userID, name);
        this.patientID = patientID;
        this.bloodType = bloodType;
        // this.medicalRecord = new MedicalRecord();
    }

    // public MedicalRecord viewMedicalRecord() {
    //     return medicalRecord;
    // }

    public void updateContactInfo(String contactNumber, String email) {
        // Logic to update contact information (you might want to add fields for contactNumber and email)
    }

    // public boolean scheduleAppointment(Doctor doctor, String date, String time) {
    //     return doctor.acceptAppointment(new Appointment(this, doctor, date, time));
    // }

    public void cancelAppointment(Appointment appointment) {
        appointment.cancel();
    }

    @Override
    public void displayMenu() {
        // This can remain empty or include basic functionality, but it's required to override
    }
}
