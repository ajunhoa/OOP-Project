package model;

import java.util.Scanner;

public class Patient extends User {

    private MedicalRecord medicalRecord;  // Composition: Patient has a MedicalRecord

    public Patient(String id, String name, String dateOfBirth, String gender, String bloodType, 
                   String contactInfo, int newUser, String password, int contactNumber, MedicalRecord medicalRecord) {
        super(id, name, "Patient", gender, 0, dateOfBirth, bloodType, contactInfo, password, newUser, contactNumber);
        this.medicalRecord = medicalRecord;  // Initialize medicalRecord via constructor
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
        System.out.println("Medical Record: " + medicalRecord);  // Show the medical record details
    }

    // Method to view available appointment slots
    public void viewAvailableSlots() {
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
        System.out.println("Appointment " + appointmentId + " rescheduled to " + newDate + " at " + newTime);
    }

    // Method to cancel an appointment
    public void cancelAppointment(Scanner scanner) {
        System.out.print("Enter appointment ID to cancel: ");
        String appointmentId = scanner.nextLine();
        System.out.println("Appointment " + appointmentId + " has been canceled.");
    }

    // Method to view scheduled appointments
    public void viewScheduledAppointments() {
        System.out.println("Scheduled appointments: [Placeholder for scheduled appointments]");
    }

    // Method to view past appointment outcome records
    public void viewPastAppointmentOutcomeRecords() {
        System.out.println("Past appointment outcomes: [Placeholder for past appointment outcomes]");
    }
}
