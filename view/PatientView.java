package view;

import controller.MedicalRecordController;
import java.util.Scanner;
import model.Patient;
import model.AppointmentSlot;
import controller.AppointmentSlotController;

public class PatientView {
    private Patient patient;
    private Scanner scanner;
    private MedicalRecordController medicalRecordController; 
    private AppointmentSlot appointmentSlot;
    private AppointmentSlotController appointmentSlotController;

    public PatientView(Patient patient, Scanner scanner, MedicalRecordController medicalRecordController) {
        this.patient = patient;
        this.scanner = scanner;
        this.medicalRecordController = medicalRecordController;
        this.appointmentSlot = new AppointmentSlot();
        this.appointmentSlotController = new AppointmentSlotController();
    }

    public void handleUserChoice() {
        boolean exit = false;
        while (!exit) {
            displayPatientMenu();
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    updatePersonalInformation();
                    break;
                case 3:
                    appointmentSlot.viewAvailableAppointmentSlots();
                    break;
                case 4:
                    appointmentSlotController.scheduleAppointment(patient.getId());
                    break;
                case 5:
                    appointmentSlotController.rescheduleAppointment(patient.getId());
                    break;
                case 6:
                    appointmentSlotController.cancelAppointment(patient.getId());
                    break;
                case 7:
                    appointmentSlot.viewScheduledAppointments(patient.getId());
                    break;
                case 8:
                   
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
    
    
}
