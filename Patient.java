import java.util.ArrayList;

public class Patient extends User {
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

    // Method to view medical record
    public MedicalRecord viewMedicalRecord() {
        return medicalRecord;
    }

    // Method to update contact information
    public void updateContactInfo(String contactNumber, String email) {
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // Method to schedule an appointment
    public boolean scheduleAppointment(Doctor doctor, String date, String time) {
        return doctor.acceptAppointment(new Appointment(this, doctor, date, time));
    }

    // Method to reschedule an appointment
    public boolean rescheduleAppointment(Appointment appointment, String newDate, String newTime) {
        appointment.reschedule(newDate, newTime);
        return true;
    }

    // Method to cancel an appointment
    public void cancelAppointment(Appointment appointment) {
        appointment.cancel();
    }

    // Method to view past appointment outcome records
    public ArrayList<String> viewPastAppointments() {
        // Mocked past appointments logic
        ArrayList<String> pastAppointments = new ArrayList<>();
        pastAppointments.add("Appointment with Dr. Smith on 2023-10-01");
        pastAppointments.add("Appointment with Dr. Lee on 2023-11-15");
        return pastAppointments;
    }
}
