package view;

import controller.AppointmentSlotController;
import controller.MedicalRecordController;
import java.util.Scanner;
import model.AppointmentOutcomeRecord;
import model.AppointmentSlot;
import model.Patient;

/**
 * The PatientView class is responsible for handling the patient's view of the Hospital Management System.
 * It provides a menu for patients to interact with their medical records, appointments, and personal information.
 * 
 * Author: Jun hoa
 * Version: 1.0
 * Since: 16/11/2024
 */
public class PatientView {
    private Patient patient;
    private Scanner scanner;
    private MedicalRecordController medicalRecordController; 
    private AppointmentSlot appointmentSlot;
    private AppointmentSlotController appointmentSlotController;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;

    /**
     * Constructs a PatientView instance with the specified patient, scanner, and medical record controller.
     *
     * @param patient The Patient object representing the logged-in patient.
     * @param scanner The Scanner object used to read user input.
     * @param medicalRecordController The MedicalRecordController object used to manage medical records.
     */
    public PatientView(Patient patient, Scanner scanner, MedicalRecordController medicalRecordController) {
        this.patient = patient;
        this.scanner = scanner;
        this.medicalRecordController = medicalRecordController;
        this.appointmentSlot = new AppointmentSlot();
        this.appointmentSlotController = new AppointmentSlotController();
        this.appointmentOutcomeRecord = new AppointmentOutcomeRecord();
    }

    /**
     * Displays the patient menu and handles user input for various patient-related actions.
     */
    public void showMenu() {
        boolean exit = false;
        while (!exit) {
            displayPatientMenu();
            System.out.print("Select an option: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                scanner.next(); // Clear invalid input
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    patient.viewMedicalRecord();
                    break;
                case 2:
                    viewPersonalInformation();
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
                    appointmentOutcomeRecord.viewPastAppointmentOutcomeRecords(patient.getId());
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

    /**
     * Displays the options available in the patient menu.
     */
    private void displayPatientMenu() {
        System.out.println("\n=== Patient Menu ===");
        System.out.println("1. View Medical Record");
        System.out.println("2. View Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Logout");
    }


    

    /**
     * Allows the patient to update their personal information, such as contact email and contact number.
     */
    public void viewPersonalInformation() {
        System.out.println("\n=== View Personal Information ===");
        System.out.println("1. Update Contact Email");
        System.out.println("2. Update Contact Number");
        
        int choice = -1;
    
        // Input validation for the menu choice
        while (choice == -1) {
            System.out.print("Select an option: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
    
                // Validate if choice is within range
                if (choice < 1 || choice > 2) {
                    System.out.println("Invalid choice. Please select a valid option.");
                    choice = -1; // Reset choice for re-prompt
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        }
    
        // Handle valid choices
        switch (choice) {
            case 1:
                medicalRecordController.updateContactInfo(patient.getId());
                break;
            case 2:
                medicalRecordController.updateContactNumber(patient.getId());
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu."); // This should not occur after validation
        }
    }
    
}