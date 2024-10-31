// File: src/models/Doctor.java
package model;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private String doctorID;
    private List<String> availabilitySlots = new ArrayList<>();
    // private List<Appointment> appointments = new ArrayList<>();

    public Doctor(String userID, String name, String doctorID) {
        super(userID, name);
        this.doctorID = doctorID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    // public List<Appointment> getAppointments() {
    //     return appointments;
    // }

    public void addAvailabilitySlot(String date, String time) {
        availabilitySlots.add(date + " " + time);
    }

    public List<String> getAvailabilitySlots() {
        return availabilitySlots;
    }

    @Override
    public void displayMenu() {
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Set Availability for Appointments");
        System.out.println("3. Accept or Decline Appointment Requests");
        System.out.println("4. View Upcoming Appointments");
        System.out.println("5. Logout");
    }
}
