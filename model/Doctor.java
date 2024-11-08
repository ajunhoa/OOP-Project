package model;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private String doctorID;
    private String role;
    private List<String> availabilitySlots = new ArrayList<>();
    // private List<Appointment> appointments = new ArrayList<>();  // Uncomment if you have an Appointment class

    public Doctor(String userID, String name, String role) {
        super(userID, name);
        this.doctorID = userID;
        this.role = role;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getRole() {
        return role;
    }

    public List<String> getAvailabilitySlots() {
        return availabilitySlots;
    }

    // Method to set availability (moved from DoctorController)
    public void setAvailability(String date, String time) {
        availabilitySlots.add(date + " " + time);
        System.out.println("Availability set for " + date + " at " + time);
    }

    // Method to view patient records (stub example, assuming Patient class exists)
    public void viewPatientRecord(String patientID) {
        System.out.println("Viewing medical record for patient ID: " + patientID);
        // Implement record retrieval logic if Patient class or records exist
    }

    // Method to manage appointments (stub example, assuming Appointment class exists)
    public void manageAppointment(String appointmentID, boolean accept) {
        if (accept) {
            // appointments.add(new Appointment(appointmentID)); // Uncomment if Appointment class exists
            System.out.println("Appointment accepted.");
        } else {
            // appointments.removeIf(appt -> appt.getID().equals(appointmentID)); // Uncomment if Appointment class exists
            System.out.println("Appointment declined.");
        }
    }

    // Method to get upcoming appointments (stub example)
    // public List<Appointment> getUpcomingAppointments() {
    //     return appointments;
    // }



}
