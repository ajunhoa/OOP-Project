package view;

import java.util.Scanner;
import model.Patient;

public class PatientView {
    private Patient patient;
    private Scanner scanner;

    public PatientView(Patient patient, Scanner scanner) {
        this.patient = patient;
        this.scanner = scanner;
    }

    public void handleUserChoice() {
        boolean exit = false;
        while (!exit) {
            displayPatientMenu();
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // To consume the newline character after the integer input

            switch (choice) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    updatePersonalInformation();
                    break;
                case 3:
                    viewAvailableAppointmentSlots();
                    break;
                case 4:
                    scheduleAppointment();
                    break;
                case 5:
                    rescheduleAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    viewScheduledAppointments();
                    break;
                case 8:
                    viewPastAppointmentOutcomeRecords();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Display Patient Menu
    private void displayPatientMenu() {
        System.out.println("\n=== Patient Menu ===");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Logout");
    }

    // Method to View Medical Record
    private void viewMedicalRecord() {
        System.out.println("Viewing medical record...");
        if (patient.getMedicalRecord() != null) {
            patient.viewMedicalRecord();  // Assuming this method exists in the Patient class
        } else {
            System.out.println("No medical record available for this patient.");
        }
    }   

    // Method to Update Personal Information
    private void updatePersonalInformation() {
        System.out.println("Updating personal information...");
        // MedicalRecordController.updatePersonalInformation(patient, scanner); // Placeholder for controller method
    }

    // Method to View Available Appointment Slots
    private void viewAvailableAppointmentSlots() {
        System.out.println("Viewing available appointment slots...");
        patient.viewAvailableSlots();  // Assuming this method exists in the Patient class
    }

    // Method to Schedule an Appointment
    private void scheduleAppointment() {
        System.out.println("Scheduling an appointment...");
        patient.scheduleAppointment(scanner);  // Assuming this method exists in the Patient class
    }

    // Method to Reschedule an Appointment
    private void rescheduleAppointment() {
        System.out.println("Rescheduling an appointment...");
        patient.rescheduleAppointment(scanner);  // Assuming this method exists in the Patient class
    }

    // Method to Cancel an Appointment
    private void cancelAppointment() {
        System.out.println("Canceling an appointment...");
        patient.cancelAppointment(scanner);  // Assuming this method exists in the Patient class
    }

    // Method to View Scheduled Appointments
    private void viewScheduledAppointments() {
        System.out.println("Viewing scheduled appointments...");
        patient.viewScheduledAppointments();  // Assuming this method exists in the Patient class
    }

    // Method to View Past Appointment Outcome Records
    private void viewPastAppointmentOutcomeRecords() {
        System.out.println("Viewing past appointment outcome records...");
        patient.viewPastAppointmentOutcomeRecords();  // Assuming this method exists in the Patient class
    }
}
