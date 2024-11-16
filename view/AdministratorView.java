package view;

import controller.MedicineController;
import controller.StaffController;
import java.util.Scanner;
import model.AppointmentOutcomeRecord;
import model.AppointmentSlot;
import model.Staff;

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
    

<<<<<<< Updated upstream
    /**
     * Prompts the user to enter details for a new staff member and adds them to the system.
     */
    private void addStaff() {
        System.out.print("Enter Staff ID: ");
        String id = scanner.nextLine().toUpperCase();
        while (id.isEmpty()) {
            System.out.print("Staff ID cannot be empty. Enter Staff ID: ");
            id = scanner.nextLine().toUpperCase();
        }
    
        System.out.print("Enter Staff Name: ");
        String name = scanner.nextLine();
        while (name.isEmpty()) {
            System.out.print("Staff Name cannot be empty. Enter Staff Name: ");
            name = scanner.nextLine();
        }
    
        // Validate Role
        String role;
        while (true) {
            System.out.print("Enter Role (Doctor/Pharmacist): ");
            role = scanner.nextLine().trim();
            if (role.equalsIgnoreCase("Doctor") || role.equalsIgnoreCase("Pharmacist")) {
                role = role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase(); // Format properly
                break;
            } else {
                System.out.println("Invalid role. Please enter 'Doctor' or 'Pharmacist'.");
            }
        }
    
        // Validate Gender
        String gender;
        while (true) {
            System.out.print("Enter Gender (Male/Female): ");
            gender = scanner.nextLine().trim();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                gender = gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase(); // Format properly
                break;
            } else {
                System.out.println("Invalid gender. Please enter 'Male' or 'Female'.");
            }
        }
    
        // Validate Age
        int age = -1;
        while (true) {
            System.out.print("Enter Age: ");
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                if (age >= 18 && age <= 100) {
                    break;
                } else {
                    System.out.println("Invalid age. Please enter an age between 18 and 100.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for age.");
                scanner.next(); // Clear invalid input
            }
        }
    
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        while (password.isEmpty()) {
            System.out.print("Password cannot be empty. Enter Password: ");
            password = scanner.nextLine();
        }
    
        // Create and add the new staff member
        Staff newStaff = new Staff(id, name, role, gender, age, 1, password);
        if (staffController.addStaff(newStaff)) {
            System.out.println("Staff added successfully.");
        } else {
            System.out.println("Failed to add staff.");
        }
    }
    
    

    /**
     * Prompts the user to enter new details for an existing staff member and updates their information.
     */
    private void updateStaff() {
        String id;
        while (true) {
            System.out.print("Enter Staff ID to update: ");
            id = scanner.nextLine();
            if (id.isEmpty()) {
                System.out.println("Staff ID cannot be empty. Please try again.");
            } else if (!staffController.isStaffIdExists(id)) {
                System.out.println("Staff ID not found in the system. Please enter a valid Staff ID.");
            } else {
                break; // Valid ID
            }
        }
    
        System.out.print("Enter New Staff Name: ");
        String name = scanner.nextLine();
        while (name.isEmpty()) {
            System.out.print("Staff Name cannot be empty. Enter New Staff Name: ");
            name = scanner.nextLine();
        }
    
        // Validate Role
        String role;
        while (true) {
            System.out.print("Enter New Role (Doctor/Pharmacist): ");
            role = scanner.nextLine().trim();
            if (role.equalsIgnoreCase("Doctor") || role.equalsIgnoreCase("Pharmacist")) {
                role = role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase(); // Format properly
                break;
            } else {
                System.out.println("Invalid role. Please enter 'Doctor' or 'Pharmacist'.");
            }
        }
    
        // Validate Gender
        String gender;
        while (true) {
            System.out.print("Enter New Gender (Male/Female): ");
            gender = scanner.nextLine().trim();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                gender = gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase(); // Format properly
                break;
            } else {
                System.out.println("Invalid gender. Please enter 'Male' or 'Female'.");
            }
        }
    
        // Validate Age
        int age = -1;
        while (true) {
            System.out.print("Enter New Age: ");
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                if (age >= 18 && age <= 100) {
                    break;
                } else {
                    System.out.println("Invalid age. Please enter an age between 18 and 100.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for age.");
                scanner.next(); // Clear invalid input
            }
        }
    
        System.out.print("Enter New Password: ");
        String password = scanner.nextLine();
        while (password.isEmpty()) {
            System.out.print("Password cannot be empty. Enter New Password: ");
            password = scanner.nextLine();
        }
    
        // Update staff details
        Staff updatedStaff = new Staff(id, name, role, gender, age, 1, password);
        if (staffController.updateStaff(updatedStaff)) {
            System.out.println("Staff updated successfully.");
        } else {
            System.out.println("Failed to update staff.");
        }
    }
    
    

    /**
     * Prompts the user to enter a staff ID and removes the corresponding staff member from the system.
     */
    private void removeStaff() {
        System.out.print("Enter Staff ID to remove: ");
        String id = scanner.nextLine();
        if (staffController.removeStaff(id)) {
            System.out.println("Staff removed successfully.");
        } else {
            System.out.println("Failed to remove staff.");
        }
    }

    /**
     * Prompts the user to enter filters for displaying staff and calls the controller to display the filtered staff list.
     */
    private void displayStaff() {
        String role = null;
        String gender = null;
        Integer age = null;
    
        while (true) {
            System.out.print("Enter Role to filter, Doctor / Pharmacist (or press Enter to skip): ");
            role = scanner.nextLine().trim();
            if (role.isEmpty()) {
                role = null;
                break;
            } else if (role.equalsIgnoreCase("Doctor") || role.equalsIgnoreCase("Pharmacist")) {
                break;
            } else {
                System.out.println("Invalid role. Please enter 'Doctor', 'Pharmacist', or press Enter to skip.");
            }
        }
    
        while (true) {
            System.out.print("Enter Gender to filter, Male / Female (or press Enter to skip): ");
            gender = scanner.nextLine().trim();
            if (gender.isEmpty()) {
                gender = null;
                break;
            } else if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                break;
            } else {
                System.out.println("Invalid gender. Please enter 'Male', 'Female', or press Enter to skip.");
            }
        }

        while (true) {
            System.out.print("Enter Age to filter (or press Enter to skip): ");
            String ageInput = scanner.nextLine().trim();
            if (ageInput.isEmpty()) {
                age = null; 
                break; 
            } else {
                try {
                    age = Integer.parseInt(ageInput);
                    break; 
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age. Please enter a valid number or press Enter to skip.");
                }
            }
        }
    
        // Call the controller to display staff with the provided filters
        staffController.displayStaff(role, gender, age);
    }
=======
>>>>>>> Stashed changes

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