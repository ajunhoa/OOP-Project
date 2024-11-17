package view;

import controller.MedicineController;
import controller.StaffController;
import java.util.Scanner;
import model.AppointmentOutcomeRecord;
import model.AppointmentSlot;

/**
 * The AdministratorView class provides the user interface for the administrator
 * of the hospital management system. It allows the administrator to manage 
 * hospital staff, view appointment details, manage medication inventory, 
 * and handle replenishment requests.
 */
public class AdministratorView {
    private Scanner scanner;
    private StaffController staffController;
    private AppointmentSlot appointmentSlot;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;
    private MedicineController medicineController;

    /** 
     * Constructs an AdministratorView instance with the specified Scanner.
     *
     * @param scanner The Scanner object used to read user input.
     */
    public AdministratorView(Scanner scanner) {
        this.scanner = scanner;
        this.staffController = new StaffController();
        this.appointmentSlot = new AppointmentSlot();
        this.appointmentOutcomeRecord = new AppointmentOutcomeRecord();
        this.medicineController = new MedicineController();
    }   

    /**
     * Displays the administrator menu and handles user input for various actions.
     */
    public void showMenu() {
        int pendingReplenishmentRequests = medicineController.countPendingReplenishmentRequests();
        if (pendingReplenishmentRequests > 0) {
            System.out.println("There are " + pendingReplenishmentRequests + " pending replenishment request(s).");
        }
        boolean exit = false;

    while (!exit) {
        this.displayMenu();

        // Input validation for menu choice
        int choice = -1;
        while (choice == -1) {
            System.out.print("Select an option: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.next(); // Consume invalid input
            }
        }
            switch (choice) {
                case 1:
                    manageHospitalStaff();
                    break;
                case 2:
                    appointmentSlot.viewAllAppointments();
                    appointmentOutcomeRecord.viewCompletedOutcomeRecord();
                    break;
                case 3:
                    medicineController.viewInventory();
                    manageMedicineInventory();
                    break;
                case 4:
                    medicineController.manageReplenishmentRequests(scanner);
                    break;
                case 5:
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the administrator menu options.
     */
    private void displayMenu() {
        System.out.println("\n=== Administrator Menu ===");
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointments Details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }

    /**
     * Manages hospital staff by providing options to add, update, remove, or display staff.
     */
    private void manageHospitalStaff() {
        boolean managing = true;
        while (managing) {
            System.out.println("\n=== Manage Hospital Staff ===");
            System.out.println("1. Add Staff");
            System.out.println("2. Update Staff");
            System.out.println("3. Remove Staff");
            System.out.println("4. Display Staff");
            System.out.println("5. Back to Administrator Menu");
    
            int choice = -1; // Initialize with an invalid value
            boolean validInput = false;
    
            // Input validation loop
            while (!validInput) {
                System.out.print("Select an option: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer
    
                    if (choice >= 1 && choice <= 5) {
                        validInput = true; // Valid input
                    } else {
                        System.out.println("Invalid choice. Please select a number between 1 and 5.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear invalid input
                }
            }
    
            // Process the choice
            switch (choice) {
                case 1:
                    staffController.addStaff();
                    break;
                case 2:
                    staffController.updateStaff();
                    break;
                case 3:
                    staffController.removeStaff();
                    break;
                case 4:
                    staffController.displayStaff();
                    break;
                case 5:
                    System.out.println("Returning to the Administrator Menu...");
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // This should never occur due to validation
            }
        }
    }
    
    /**
     * Manages the medicine inventory by providing options to add, delete, update stock, or go back to the main menu.
     */
    public void manageMedicineInventory() {
        boolean backToMain = false;
    
        while (!backToMain) {
            System.out.println("\n=== Manage Medicine Inventory ===");
            System.out.println("1. Add New Medicine");
            System.out.println("2. Delete Medicine");
            System.out.println("3. Update Stock");
            System.out.println("4. Back to Administrator Menu");
    
            // Input validation for menu choice
            int choice = -1;
            while (choice == -1) {
                System.out.print("Select an option: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                    scanner.next(); // Consume invalid input
                }
            }
    
            // Handle menu options
            switch (choice) {
                case 1:
                    medicineController.addMedicine(scanner);
                    break;
                case 2:
                    medicineController.deleteMedicine(scanner);
                    break;
                case 3:
                    medicineController.updateStock(scanner);
                    break;
                case 4:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
    
}