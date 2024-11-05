package view;


//to change 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import hospitalmanagementsystem.HospitalManagementSystem;
import controller.UserController;
import controller.MedicineController;
//import campsystem.model.Camp;

/**
 * The PharmacistView class provides the interface for student interactions within
 * the CAMs.
 * It enables students to change their password, view available and registered
 * camps,
 * register for camps, and manage enquiries and suggestions related to the
 * camps.
 * 
 * @author Aidan Ling
 * @version 1.0
 * @since 2023-11-01
 */
public class PharmacistView {

    /**
     * Displays the main menu for student users. The menu provides options like
     * changing the password, viewing available camps, viewing registered camps,
     * and logging out of the system.
     */
    public static void displayUserMenu() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("=====================================");
            System.out.println("Pharmacist Portal");
            System.out.println("1. Change password");
            System.out.println("2. View Appointment Outcome Record");
            System.out.println("3. View Inventory");
            //may not need logout
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            try {

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        UserController.changePassword();
                        continue;
                    case 2:
                        PharmacistView.viewAppointmentOutcomeRecord();
                        continue;
                    case 3:
                        PharmacistView.viewInventory();
                        continue;
                    case 4:
                        HospitalManagementSystem.currentUser = null;
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("=====================================");
                System.out.println("Invalid input!");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("=====================================");
                System.out.println("An error has occurred: " + e);
            }
        } while (true);
    }

    /**
     * Shows a list of all camps that a student can register for. The camps are
     * filtered
     * based on the student's faculty, open vacancies, registration deadlines, and
     * visibility.
     * Students can register as an attendee or as a camp committee member, and they
     * can
     * also send enquiries about the camps.
     */
    public static void viewAppointmentOutcomeRecord() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("=====================================");
            ArrayList<AppointmentOutcomeRecord> availableAppointmentOutcomeRecord = new ArrayList<>();
            for (AppointmentOutcomeRecord appointmentoutcomerecord : AppointmentOutcomeRecordSystem.appointmentoutcomerecords) {
                if ((!appointmentoutcomerecord.getDate().isAfter(LocalDateTime.now())) &&
                        appointmentoutcomerecord.getServiceType() != null && !appointmentoutcomerecord.getServiceType().isEmpty() &&
                        appointmentoutcomerecord.getMedicine() != null && !appointmentoutcomerecord.getMedicine().isEmpty() &&
                        appointmentoutcomerecord.getConsultationNotes() != null && !appointmentoutcomerecord.getConsultationNotes().isEmpty()) {
                    AppointmentOutcomeRecord.add(appointmentoutcomerecord);
                }
            }

            if (AppointmentOutcomeRecord.isEmpty()) {
                System.out.println("Empty!");
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            int counter = 1;
            System.out.println("Appointment Outcome Record:");
            System.out.format("%-10s%-20s%-20s%-20s%-20s\n",
                    "Index",
                    "Date",
                    "Service Type",
                    "Medicine",
                    "Consultation Notes"
            );

            for (AppointmentOutcomeRecord appointmentoutcomerecord : availableAppointmentOutcomeRecord) {
                System.out.format("%-10s%-20s%-20s%-20s%-20s\n",
                        counter++,
                        appointmentoutcomerecord.getDate().format(formatter),
                        appointmentoutcomerecord.getServiceType(),
                        appointmentoutcomerecord.getMedicine(),
                        appointmentoutcomerecord.getConsultationNotes());
            }

            System.out.println("=====================================");
            System.out.print("Enter appointment outcome record index (0 to return): ");
            try {
                int index = scanner.nextInt();
                scanner.nextLine();
                if (index == 0) {
                    return;
                }
                if (index < 1 || index > availableAppointmentOutcomeRecord.size()) {
                    System.out.println("Invalid index!");
                    continue;
                }
                AppointmentOutcomeRecord selectedAppointmentOutcomeRecord = availableAppointmentOutcomeRecord.get(index - 1);
                System.out.println("=====================================");
                System.out.println("Appointment Details:");
                System.out.println("Date: " + selectedAppointmentOutcomeRecord.getDate().format(formatter));
                System.out.println("Service Type: " + selectedAppointmentOutcomeRecord.getServiceType());
                System.out.println("Medicine: " + selectedAppointmentOutcomeRecord.getMedicine());
                System.out.println("Consultation Notes: " + selectedAppointmentOutcomeRecord.getConsultationNotes());
                System.out.println("1. Update prescription status");
                System.out.println("2. Back to menu");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        MedicineController.updateMedicineStatus(selectedAppointmentOutcomeRecord);
                        System.out.println("Updated Medicine Status. ");
                        continue;
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("=====================================");
                System.out.println("Invalid input!");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("=====================================");
                System.out.println("An error has occurred: " + e);
            }
        } while (true);
    }


    public static void viewInventory() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("=====================================");
                ArrayList<Medicine> availableMedicine = new ArrayList<>();

                if (InventorySystem.medicine != null) {
                    for (Medicine medicine : InventorySystem.medicine) {
                        if (
                                medicine.getMedicineName() != null && !medicine.getMedicineName().isEmpty() &&
                                        medicine.getInitialStock() != null && medicine.getInitialStock() > 0 &&
                                        medicine.getLowStockLevelAlert() != null && medicine.getLowStockLevelAlert() > 0 &&
                                        medicine.getMedicineStatus() != null && !medicine.getMedicineStatus().isEmpty()
                        ) {
                            availableMedicine.add(medicine);
                        }
                    }
                }

                if (availableMedicine.isEmpty()) {
                    System.out.println("Empty!");
                    break; // Exit the loop if no medicines are available
                } else {
                    System.out.println("Available Medicines:");
                    for (Medicine medicine : availableMedicine) {
                        System.out.println("Name: " + medicine.getMedicineName());
                        System.out.println("Initial Stock: " + medicine.getInitialStock());
                        System.out.println("Low Stock Alert Level: " + medicine.getLowStockLevelAlert());
                        System.out.println("Status: " + medicine.getMedicineStatus());
                        System.out.println("================================");
                    }

                    System.out.print("Enter 1 to submit replenishment request or 0 to exit: ");
                    try {
                        int input = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline
                        if (input == 1) {
                            // Submit replenishment request
                            // Implement the logic here
                        } else if (input == 0) {
                            System.out.println("Exiting inventory view.");
                            break; // Exit the loop
                        } else {
                            System.out.println("Invalid input! Please try again.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a number.");
                        scanner.nextLine(); // Consume the invalid input
                    }
                }
            }
        }
    }
}
