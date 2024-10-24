import java.util.List;

class Patient extends User {
    private String patientID;
    private String contactNumber;
    private String email;
    private String bloodType;
    private MedicalRecord medicalRecord;
    
    public Patient(String userID, String name, String patientID, String bloodType) {
        super(userID, name);
        this.patientID = patientID;
        this.bloodType = bloodType;
        this.medicalRecord = new MedicalRecord();
    }
    
    public MedicalRecord viewMedicalRecord() {
        return medicalRecord;
    }
    
    public void updateContactInfo(String contactNumber, String email) {
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public boolean scheduleAppointment(Doctor doctor, String date, String time) {
        return doctor.acceptAppointment(new Appointment(this, doctor, date, time));
    }

    public void cancelAppointment(Appointment appointment) {
        appointment.cancel();
    }

    @Override
    public void displayMenu() {
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Past Appointment Outcome Records");
        System.out.println("8. Logout");
    }
}
