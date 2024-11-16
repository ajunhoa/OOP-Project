package view;

import controller.StaffController;
import controller.MedicineController;
import model.Staff;
import model.AppointmentSlot;
import model.AppointmentOutcomeRecord;

import java.util.Scanner;

public class AdministratorView {
    private Scanner scanner;
    private StaffController staffController;
    private AppointmentSlot appointmentSlot;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;
    private MedicineController medicineController;


    public AdministratorView(Scanner scanner) {
        this.scanner = scanner;
        this.staffController = new StaffController();
        this.appointmentSlot = new AppointmentSlot();
        this.appointmentOutcomeRecord = new AppointmentOutcomeRecord();
        this.medicineController = new MedicineController();

    }

    public void showMenu() {
        int pendingReplenishmentRequests = medicineController.countPendingReplenishmentRequests();
        if (pendingReplenishmentRequests > 0) {
            System.out.println("There are " + pendingReplenishmentRequests + " pending replenishment request(s).");
        }
        boolean exit = false;
        while (!exit) {
            this.displayMenu();
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageHospitalStaff();
                    break;
                case 2:
                    appointmentSlot.viewAllAppointments();
                    appointmentOutcomeRecord.viewCompletedOutcomeRecord();
                    break;
                case 3:
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

    private void displayMenu() {
        System.out.println("\n=== Administrator Menu ===");
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointments Details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }

    private void manageHospitalStaff() {
        boolean managing = true;
        while (managing) {
            System.out.println("\n=== Manage Hospital Staff ===");
            System.out.println("1. Add Staff");
            System.out.println("2. Update Staff");
            System.out.println("3. Remove Staff");
            System.out.println("4. Display Staff");
            System.out.println("5. Back to Administrator Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addStaff();
                    break;
                case 2:
                    updateStaff();
                    break;
                case 3:
                    removeStaff();
                    break;
                case 4:
                    displayStaff();
                    break;
                case 5:
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addStaff() {
        System.out.print("Enter Staff ID: ");
        String id = scanner.nextLine().toUpperCase();
        System.out.print("Enter Staff Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Role (Doctor/Pharmacist): ");
        String role = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        Staff newStaff = new Staff(id, name, role, gender, age, 1 , password); 
        if (staffController.addStaff(newStaff)) {
            System.out.println("Staff added successfully.");
        } else {
            System.out.println("Failed to add staff.");
        }
    }

    private void updateStaff() {
        System.out.print("Enter Staff ID to update: ");
        String id = scanner.nextLine();
        System.out.print("Enter New Staff Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Role (Doctor/Pharmacist): ");
        String role = scanner.nextLine();
        System.out.print("Enter New Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter New Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter New Password: ");
        String password = scanner.nextLine();

        Staff updatedStaff = new Staff(id, name, role, gender, age, 1, password);
        if (staffController.updateStaff(updatedStaff)) {
            System.out.println("Staff updated successfully.");
        } else {
            System.out.println("Failed to update staff.");
        }
    }

    private void removeStaff() {
        System.out.print("Enter Staff ID to remove: ");
        String id = scanner.nextLine();
        if (staffController.removeStaff(id)) {
            System.out.println("Staff removed successfully.");
        } else {
            System.out.println("Failed to remove staff.");
        }
    }

    private void displayStaff() {
        System.out.print("Enter Role to filter, Doctor / Pharmacist (or press Enter to skip): ");
        String role = scanner.nextLine();
        if (role.isEmpty()) {
            role = null;
        }
    
        System.out.print("Enter Gender to filter, Male / Female (or press Enter to skip): ");
        String gender = scanner.nextLine();
        if (gender.isEmpty()) {
            gender = null;
        }
    
        System.out.print("Enter Age to filter (or press Enter to skip): ");
        String ageInput = scanner.nextLine();
        Integer age = null;
        if (!ageInput.isEmpty()) {
            age = Integer.parseInt(ageInput);
        }
    
        staffController.displayStaff(role, gender, age);
    }

    public void manageMedicineInventory() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n=== Manage Medicine Inventory ===");
            System.out.println("1. Add New Medicine");
            System.out.println("2. Delete Medicine");
            System.out.println("3. Update Stock");
            System.out.println("4. Back to Administrator Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
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