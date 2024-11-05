package controller;

import model.Doctor;
// import model.Appointment;
// import model.Patient;
import java.util.List;
import Staff;

public class DoctorController {
    private Doctor doctor;

    public DoctorController(Staff staff) {
        this.doctor = staff;
    }

    // Method to set availability
    public void setAvailability(String date, String time) {
        doctor.addAvailabilitySlot(date, time);
        System.out.println("Availability set for " + date + " at " + time);
    }

    // Method to view patient's medical record (for the sake of example, assume it fetches record)
    // public void viewPatientRecord(Patient patient) {
    //     System.out.println("Viewing medical record for patient: " + patient.getName());
    //     // Display the record; could be a call to patient.viewMedicalRecord()
    // }

    // // Accept or decline appointments
    // public void manageAppointment(Appointment appointment, boolean accept) {
    //     if (accept) {
    //         doctor.getAppointments().add(appointment);
    //         System.out.println("Appointment accepted.");
    //     } else {
    //         doctor.getAppointments().remove(appointment);
    //         System.out.println("Appointment declined.");
    //     }
    // }

    // // Fetch upcoming appointments
    // public List<Appointment> getUpcomingAppointments() {
    //     return doctor.getAppointments();
    // }
}
