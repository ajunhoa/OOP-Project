package view;

import controller.MedicineController;
import java.util.Scanner;
import model.AppointmentOutcomeRecord;
public class PharmacistView {

    private Scanner scanner;
    private AppointmentOutcomeRecord appointmentOutcomeRecord; 
    private MedicineController medicineController;

    public PharmacistView(Scanner scanner) {
        this.scanner = scanner;
        this.appointmentOutcomeRecord = new AppointmentOutcomeRecord(); 
        this.medicineController = new MedicineController(); 
    }

    public void displayPharmacistMenu() {
        boolean exit = false;
        while (!exit) {
            this.displayMenu();
            System.out.print("Select an option: ");
            int choice = scanner.nextInt(); 
            scanner.nextLine();

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

    public void displayMenu() {
        System.out.println("\n=== Pharmacist Menu ===");
        System.out.println("1. View Appointment Outcome Record");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Logout");

    }
}
