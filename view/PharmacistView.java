package view;

import controller.MedicineController;
import java.util.Scanner;
import model.AppointmentOutcomeRecord;

/**
 * The PharmacistView class provides the user interface for the pharmacist
 * in the hospital management system. It allows the pharmacist to manage 
 * appointment outcome records, update prescription statuses, view medication 
 * inventory, and submit replenishment requests.
 */
public class PharmacistView {

    private Scanner scanner;
    private AppointmentOutcomeRecord appointmentOutcomeRecord; 
    private MedicineController medicineController;

    /**
     * Constructs a PharmacistView instance with the specified Scanner.
     *
     * @param scanner The Scanner object used to read user input.
     */
    public PharmacistView(Scanner scanner) {
        this.scanner = scanner;
        this.appointmentOutcomeRecord = new AppointmentOutcomeRecord(); 
        this.medicineController = new MedicineController(); 
    }

    /**
     * Displays the pharmacist menu and handles user input for various actions.
     */
    public void showMenu() {
        int lowStockCount = medicineController.countLowStockMedicines();
        if (lowStockCount > 0) {
            System.out.println("There are " + lowStockCount + " medicine(s) that are low in stock!");
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
    
            // Handle menu options
            switch (choice) {
                case 1:
                    appointmentOutcomeRecord.viewAppointmentOutcomeRecord();
                    break;
                case 2:
                    appointmentOutcomeRecord.updateMedicineStatus();
                    break;
                case 3:
                    medicineController.viewInventory();
                    break;
                case 4:
                    medicineController.submitReplenishmentRequest(scanner);
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
     * Displays the options available in the pharmacist menu.
     */
    public void displayMenu() {
        System.out.println("\n=== Pharmacist Menu ===");
        System.out.println("1. View Appointment Outcome Record");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Logout");
    }
}