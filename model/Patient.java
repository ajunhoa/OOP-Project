package model;

import java.util.Scanner;

public class Patient extends User {

    public Patient(String id, String name, String dateOfBirth, String gender, String bloodType, 
                   String contactInfo, int newUser, String password, int contactNumber) {
        super(id, name, "Patient", gender, 0, dateOfBirth, bloodType, contactInfo, password, newUser, contactNumber);
    }

    @Override
    public String toString() {
        return "Patient ID: " + getId() + ", Name: " + getName() + ", Date of Birth: " + getDateOfBirth() +
               ", Gender: " + getGender() + ", Blood Type: " + getBloodType() +
               ", Contact Info: " + getContactInfo() + ", Contact Number: " + getContactNumber() +
               ", New User: " + (isNewUser() ? "Yes" : "No");
    }
    // Method to view the patient's medical record
    public void viewMedicalRecord() {
        // Code to view medical record
        System.out.println("Medical Record: [Placeholder for patient's medical record]");
    }

    // Method to view available appointment slots
    public void viewAvailableSlots() {
        // Code to show available appointment slots
        System.out.println("Available slots: [Placeholder for available appointment slots]");
    }

    // Method to schedule an appointment
    public void scheduleAppointment(Scanner scanner) {
        System.out.print("Enter doctor ID: ");
        String doctorId = scanner.nextLine();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter appointment time (HH:MM AM/PM): ");
        String time = scanner.nextLine();
        // Logic to schedule an appointment
        System.out.println("Appointment scheduled with doctor " + doctorId + " on " + date + " at " + time);
    }

    // Method to reschedule an appointment
    public void rescheduleAppointment(Scanner scanner) {
        System.out.print("Enter appointment ID to reschedule: ");
        String appointmentId = scanner.nextLine();
        System.out.print("Enter new date (YYYY-MM-DD): ");
        String newDate = scanner.nextLine();
        System.out.print("Enter new time (HH:MM AM/PM): ");
        String newTime = scanner.nextLine();
        // Logic to reschedule an appointment
        System.out.println("Appointment " + appointmentId + " rescheduled to " + newDate + " at " + newTime);
    }

    // Method to cancel an appointment
    public void cancelAppointment(Scanner scanner) {
        System.out.print("Enter appointment ID to cancel: ");
        String appointmentId = scanner.nextLine();
        // Logic to cancel the appointment
        System.out.println("Appointment " + appointmentId + " has been canceled.");
    }

    // Method to view scheduled appointments
    public void viewScheduledAppointments() {
        // Code to display scheduled appointments
        System.out.println("Scheduled appointments: [Placeholder for scheduled appointments]");
    }

    // Method to view past appointment outcome records
    public void viewPastAppointmentOutcomeRecords() {
        // Code to view past appointment outcomes
        System.out.println("Past appointment outcomes: [Placeholder for past appointment outcomes]");
    }

}
