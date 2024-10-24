import java.util.ArrayList;
import java.util.List;

class Doctor extends User {
    private List<Appointment> appointments = new ArrayList<>();
    private String doctorID;
    
    public Doctor(String userID, String name, String doctorID) {
        super(userID, name);
        this.doctorID = doctorID;
    }

    public void viewPatientRecord(Patient patient) {
        System.out.println(patient.viewMedicalRecord());
    }

    public void updateMedicalRecord(Patient patient, String diagnosis, String treatment, Prescription prescription) {
        MedicalRecord record = patient.viewMedicalRecord();
        record.addDiagnosis(diagnosis);
        record.addTreatment(treatment);
        record.addPrescription(prescription);
    }
    
    public void setAvailability(String date, String time) {
        // Code for setting availability
    }
    
    public boolean acceptAppointment(Appointment appointment) {
        appointments.add(appointment);
        return true;
    }
    
    public void declineAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    public void recordAppointmentOutcome(Appointment appointment, String outcome, Prescription prescription) {
        appointment.setOutcome(outcome, prescription);
    }

    public void showAvailableSlots() {
        System.out.println("Available slots: 10:00 AM, 2:00 PM, 4:00 PM");
    }
    

    @Override
    public void displayMenu() {
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Accept or Decline Appointment Requests");
        System.out.println("6. View Upcoming Appointments");
        System.out.println("7. Record Appointment Outcome");
        System.out.println("8. Logout");
    }
}
