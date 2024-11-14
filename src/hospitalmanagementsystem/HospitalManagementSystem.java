package hospitalmanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

import model.Administrator;
import model.UserConcrete;
import model.Doctor;
import model.Patient;
import model.Pharmacist;
import view.AdministratorView;
import view.DoctorView;
import view.PharmacistView;

public class HospitalManagementSystem {

    /** Collection of all users in the system. */
    public static HashSet<UserConcrete> users = new HashSet<UserConcrete>();

    /** Represents the currently logged-in user. */
    public static UserConcrete currentUser;

    public static void main(String[] args) {
        HospitalManagementSystem.initialize();
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("=====================================");
            System.out.println("Welcome to the Hospital Management System!");
            System.out.println("1. Login");
            System.out.println("2. Close application");
            System.out.print("Enter choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        boolean isLoggedIn = HospitalManagementSystem.login();
                        if (isLoggedIn) {
                            // Redirect to the respective user menu based on user type
                            if (HospitalManagementSystem.currentUser instanceof Administrator) {
                                AdministratorView.displayUserMenu();
                            } else if (HospitalManagementSystem.currentUser instanceof Doctor) {
                                DoctorView.displayUserMenu();
                            } else if (HospitalManagementSystem.currentUser instanceof Pharmacist) {
                                PharmacistView.displayUserMenu();
                            } else if (HospitalManagementSystem.currentUser instanceof Patient) {
                                // PatientView.displayUserMenu();
                            } else {
                                System.out.println("Unknown user type.");
                            }
                        }
                        continue;
                    case 2:
                        scanner.close();
                        return;
                    default:
                        System.out.println("=====================================");
                        System.out.println("Invalid choice!");
                        continue;
                }
            } catch (Exception e) {
                System.out.println("=====================================");
                System.out.println("An error has occurred: " + e);
            }
        } while (true);
    }

    /**
     * Initializes the system by loading user data from the StaffList and PatientList CSV files.
     * Adds staff (doctors, administrators, pharmacists) and patients to the system.
     */
    public static void initialize() {
        HospitalManagementSystem.addStaff("assets/StaffList.csv");
        HospitalManagementSystem.addPatient("assets/PatientList.csv");
    }

    /**
     * Reads and adds staff members from a CSV file to the system.
     * Only adds users with roles of Administrator, Doctor, or Pharmacist.
     *
     * @param filePath The path to the CSV file containing the staff data.
     */
    public static void addStaff(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.useDelimiter(",");
            scanner.nextLine(); // Skip the first line (header)
            while (scanner.hasNextLine()) {
                String[] userInformation = scanner.nextLine().split(",");
                String userID = userInformation[0].trim();
                String name = userInformation[1].trim();
                String role = userInformation[2].trim();
                String gender = userInformation[3].trim();
                int age = Integer.parseInt(userInformation[4].trim());
                String password = userInformation.length > 5 ? userInformation[5].trim() : "password"; // default password

                // Instantiate user based on role
                UserConcrete user;
                switch (role.toLowerCase()) {
                    case "administrator":
                        user = new Administrator(userID, name, gender, age);
                        break;
                    case "doctor":
                        user = new Doctor(userID, name, gender, age);
                        break;
                    case "pharmacist":
                        user = new Pharmacist(userID, name, gender, age);
                        break;
                    default:
                        System.out.println("Unknown role type in staff list: " + role);
                        continue; // Skip unknown roles
                }

                // Add the user to the system's user collection
                HospitalManagementSystem.users.add(user);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error has occurred\n" + e.getMessage());
        }
    }

    /**
     * Reads and adds patients from a CSV file to the system.
     * Only adds users with the role of Patient.
     *
     * @param filePath The path to the CSV file containing the patient data.
     */
    public static void addPatient(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.useDelimiter(",");
            scanner.nextLine(); // Skip the first line (header)
            while (scanner.hasNextLine()) {
                String[] userInformation = scanner.nextLine().split(",");
                String userID = userInformation[0].trim();
                String name = userInformation[1].trim();
                //String gender = userInformation[3].trim();
                String password = userInformation.length > 6 ? userInformation[6].trim() : "password"; // default password

                // Instantiate the user as a Patient
                UserConcrete user = new Patient(userID, name);

                // Add the user to the system's user collection
                HospitalManagementSystem.users.add(user);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error has occurred\n" + e.getMessage());
        }
    }

    /**
     * Handles user login by prompting for user ID and password and validating them
     * against the user list.
     *
     * @return A boolean indicating if the login was successful.
     */
    public static boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        String userID = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.println("=====================================");

        for (UserConcrete user : HospitalManagementSystem.users) {
            if (user.getUserID().equalsIgnoreCase(userID) &&
                    user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                HospitalManagementSystem.currentUser = user;
                System.out.println("Welcome " + HospitalManagementSystem.currentUser.getName() + "!");
                return true;
            }
        }
        System.out.println("Login failed!");
        return false;
    }
}
