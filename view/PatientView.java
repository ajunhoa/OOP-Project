package view;

import controller.MedicalRecordController;
import java.util.Scanner;
import model.Patient;

public class PatientView {
    private Patient patient;
    private Scanner scanner;
    private MedicalRecordController medicalRecordController; // Controller for updating info

    public PatientView(Patient patient, Scanner scanner, MedicalRecordController medicalRecordController) {
        this.patient = patient;
        this.scanner = scanner;
        this.medicalRecordController = medicalRecordController;
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

    private void viewMedicalRecord() {
        if (patient.getMedicalRecord() != null) {
            patient.getMedicalRecord().displayMedicalRecord(patient); // Pass the patient object
        } else {
            System.out.println("No medical record available for this patient.");
        }
    }
    

    // Method to Update Personal Information
    private void updatePersonalInformation() {
        System.out.println("\n=== Update Personal Information ===");
        System.out.println("1. Update Contact Information");
        System.out.println("2. Update Contact Number");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // To consume the newline character after integer input

        switch (choice) {
            case 1:
                medicalRecordController.updateContactInfo(patient.getId()); // Using MedicalRecordController to update contact info
                break;
            case 2:
                medicalRecordController.updateContactNumber(patient.getId()); // Using MedicalRecordController to update contact number
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }
    // Placeholder for viewAvailableAppointmentSlots method
    private void viewAvailableAppointmentSlots() {
        System.out.println("Viewing available appointment slots...");
    }

    // Placeholder for scheduleAppointment method
    private void scheduleAppointment() {
        System.out.println("Scheduling an appointment...");
    }

    // Placeholder for rescheduleAppointment method
    private void rescheduleAppointment() {
        System.out.println("Rescheduling an appointment...");
    }

    // Placeholder for cancelAppointment method
    private void cancelAppointment() {
        System.out.println("Canceling an appointment...");
    }

    // Placeholder for viewScheduledAppointments method
    private void viewScheduledAppointments() {
        System.out.println("Viewing scheduled appointments...");
    }

    // Placeholder for viewPastAppointmentOutcomeRecords method
    private void viewPastAppointmentOutcomeRecords() {
        System.out.println("Viewing past appointment outcome records...");
    }

    // Other methods as you provided, e.g., viewAvailableAppointmentSlots, scheduleAppointment, etc.
}
