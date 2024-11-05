import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.Doctor;
import model.Patient;
import view.DoctorView;

class Staff {
    private String id;
    private String name;
    private String role;
    private String gender;
    private int age;
    private String dateOfBirth;
    private String bloodType;
    private String contactInfo; // Fixed variable name here
    private String password; // New field for password
    private int newUser; // New field for new user indicator

    public Staff(String id, String name, String role, String gender, int age, String dateOfBirth, String bloodType, String contactInfo, String password, int newUser) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo; // Fixed variable name here
        this.password = password;
        this.newUser = newUser;
    }

    // Getters
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getBloodType() { return bloodType; } // Added this getter

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Role: " + role + ", Gender: " + gender +
               ", Age: " + age + ", Date of Birth: " + dateOfBirth + ", Blood Type: " + bloodType +
               ", Contact Info: " + contactInfo + ", New User: " + newUser;
    }
}

public class Main {
    public static void main(String[] args) {
        Map<String, Staff> staffMap = loadStaffDetails("assets/finalise_list.csv");

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the Hospital Management System");
            System.out.print("Enter your Staff ID: ");
            String staffId = scanner.nextLine().trim().toUpperCase();

            System.out.print("Enter your Password: ");
            String password = scanner.nextLine().trim();

            // Fetch the staff member based on ID
            Staff staff = staffMap.get(staffId);

            if (staff != null && staff.getPassword().equals(password)) {
                System.out.println("Welcome, " + staff.getName());
                System.out.println(staff);

                // Role-based logic
                switch (staff.getRole()) {
                    case "Doctor":
                        // Doctor-specific actions here
                        Doctor doctor = new Doctor(staff.getId(), staff.getName(), staff.getRole());
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
                        Patient patient = new Patient(staff.getId(), staff.getName(), staff.getId(), staff.getBloodType());
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
    private static Map<String, Staff> loadStaffDetails(String filePath) {
        Map<String, Staff> staffMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header

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

                    // Create and add Staff object to the map
                    Staff staff = new Staff(id, name, role, gender, age, dateOfBirth, bloodType, contactInfo, password, newUser);
                    staffMap.put(id, staff);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing numeric values in line: " + line);
                }
            }

            // Debugging: Print all IDs loaded into the staffMap
            System.out.println("Loaded staff IDs: " + staffMap.keySet());
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        return staffMap;
    }
}
