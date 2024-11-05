package view;

import hospitalmanagementsystem.HospitalManagementSystem;
import controller.UserController;
import controller.StaffController;

import model.Staff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * The AdministratorView class provides the user interface specifically
 * for administrators. It allows administrators to manage their password, view staff, medicine inventory, and appointments.
 * @version 1.1
 */

public class AdministratorView {

    private static final String FILE_PATH = "path/to/Staff_List.csv";
    private static final String MEDICINE_FILE_PATH = "path/to/Medicine_List.csv";

    // Initialize StaffController with the loaded staff list
    private static StaffController staffController = new StaffController(new ArrayList<>());

    public static void displayUserMenu() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("=====================================");
            System.out.println("Administrator Portal");
            System.out.println("1. Change password");
            System.out.println("2. View staff list");
            System.out.println("3. View medicine inventory");
            System.out.println("4. View appointments");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        UserController.changePassword();
                        continue;
                    case 2:
                        viewStaffList();
                        continue;
                    case 3:
                        viewInventory();
                        continue;
                    case 4:
                        viewAppointment();
                        continue;
                    case 5:
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

    public static void viewStaffList() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter role to filter (or press Enter to skip): ");
        String role = scanner.nextLine().trim();
        if (role.isEmpty()) role = null;

        System.out.println("Enter gender to filter (or press Enter to skip): ");
        String gender = scanner.nextLine().trim();
        if (gender.isEmpty()) gender = null;

        Integer minAge = null, maxAge = null;
        try {
            System.out.println("Enter minimum age to filter (or press Enter to skip): ");
            String minAgeInput = scanner.nextLine().trim();
            if (!minAgeInput.isEmpty()) minAge = Integer.parseInt(minAgeInput);

            System.out.println("Enter maximum age to filter (or press Enter to skip): ");
            String maxAgeInput = scanner.nextLine().trim();
            if (!maxAgeInput.isEmpty()) maxAge = Integer.parseInt(maxAgeInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid age entered. Skipping age filter.");
        }

        List<Staff> staffList = staffController.getStaffList();
        List<Staff> filteredList = filterStaffList(staffList, role, gender, minAge, maxAge);

        if (filteredList.isEmpty()) {
            System.out.println("No staff found with the specified criteria.");
        } else {
            System.out.println("Filtered Staff List:");
            for (Staff staff : filteredList) {
                System.out.println(staff);
            }
        }
    }

    private static List<Staff> loadStaffList() {
        List<Staff> staffList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] fields = line.split(",");
                String staffId = fields[0];
                String name = fields[1];
                String role = fields[2];
                String gender = fields[3];
                int age = Integer.parseInt(fields[4]);

                staffList.add(new Staff(staffId, name, role, gender, age));
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        return staffList;
    }

    private static List<Staff> filterStaffList(List<Staff> staffList, String role, String gender, Integer minAge, Integer maxAge) {
        List<Staff> filteredList = new ArrayList<>();
        for (Staff staff : staffList) {
            boolean matches = true;
            if (role != null && !staff.getRole().equalsIgnoreCase(role)) matches = false;
            if (gender != null && !staff.getGender().equalsIgnoreCase(gender)) matches = false;
            if (minAge != null && staff.getAge() < minAge) matches = false;
            if (maxAge != null && staff.getAge() > maxAge) matches = false;
            if (matches) filteredList.add(staff);
        }
        return filteredList;
    }

    public static void viewInventory() {
        // Unmodified, as it works independently of StaffController.
    }

    public static void viewAppointment(List<AppointmentSlot> appointmentSlots) {
        // Unmodified, as it works independently of StaffController.
    }
}
