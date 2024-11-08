import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import controller.UserController;
import model.Doctor;
import model.Patient;
import view.DoctorView;
import view.PatientView;
import model.User;


public class Main {
    public static void main(String[] args) {
        String filePath = "assets/finalise_list.csv";
        Map<String, User> userMap = loadUserDetails(filePath);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the Hospital Management System");
            System.out.print("Enter your User ID: ");
            String userId = scanner.nextLine().trim().toUpperCase();

            System.out.print("Enter your Password: ");
            String password = scanner.nextLine().trim();

            // Fetch the user member based on ID
            User user = userMap.get(userId);

            if (user != null && user.getPassword().equals(password)) {
                System.out.println("Welcome, " + user.getName());
                System.out.println(user);
                UserController.promptPasswordChange(user, filePath);


                // Role-based logic
                switch (user.getRole()) {
                    case "Doctor":
                        // Doctor-specific actions here
                        Doctor doctor = new Doctor(user.getId(), user.getName(), user.getRole());
                        DoctorView doctorView = new DoctorView(doctor);
                        doctorView.displayDoctorMenu();
                        break;
                    case "Pharmacist":
                        System.out.println("Accessing Pharmacist's functionalities...");
                        // Pharmacist-specific actions here
                        break;
                    case "Administrator":
                        System.out.println("Accessing Administrator's functionalities...");
                        // Administrator-specific actions here
                        break;
                    case "Patient":
                        Patient patient = new Patient(user.getId(), user.getName(), user.getId(), user.getBloodType());
                        PatientView patientView = new PatientView(patient);
                        patientView.handleUserChoice();
                        break;
                    default:
                        System.out.println("Role not recognized.");
                }
            } else {
                System.out.println("Invalid Staff ID or Password.");
            }
        }
    }

    // Method to load staff details from CSV
    private static Map<String, User> loadUserDetails(String filePath) {
        Map<String, User> userMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                // Debugging: Print each line read from the CSV
                System.out.println("Processing line: " + line);

                // Split the line into values
                String[] values = line.split(",");
                if (values.length < 10) {
                    System.out.println("Skipping line, not enough columns: " + line);
                    continue;
                }

                try {
                    // Parse fields with defaults for missing or empty values
                    String id = values[0].trim();
                    String name = values[1].trim();
                    String role = values[2].trim();
                    String gender = values[3].trim();
                    int age = !values[4].isEmpty() ? Integer.parseInt(values[4].trim()) : 0;
                    String dateOfBirth = values[5].trim();
                    String bloodType = values[6].trim();
                    String contactInfo = values[7].trim();
                    String password = values[8].trim();
                    int newUser = !values[9].isEmpty() ? Integer.parseInt(values[9].trim()) : 0;

                    // Create and add User object to the map
                    User user = new User(id, name, role, gender, age, dateOfBirth, bloodType, contactInfo, password, newUser);
                    userMap.put(id, user);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing numeric values in line: " + line);
                }
            }

            // Debugging: Print all IDs loaded into the userMap
            System.out.println("Loaded user IDs: " + userMap.keySet());
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        return userMap;
    }
}
