import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, Staff> staffMap = loadStaffDetails("finalise_list.csv");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Hospital Management System");
        System.out.print("Enter your staff ID: ");
        String staffId = scanner.nextLine().trim().toUpperCase(); // Trim and convert to uppercase
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim(); // Trim the password

        // Check if the staff ID exists and verify the password
        Staff staff = staffMap.get(staffId);
        if (staff != null && staff.getPassword().equals(password)) {
            System.out.println("Welcome, " + staff.getName());
            System.out.println(staff);

            // Switch based on the role
            switch (staff.getRole()) {
                case "Doctor":
                    System.out.println("Doctor menu here...");
                    // Doctor logic here
                    break;
                case "Pharmacist":
                    System.out.println("Pharmacist menu here...");
                    // Pharmacist logic here
                    break;
                case "Administrator":
                    System.out.println("Administrator menu here...");
                    // Administrator logic here
                    break;
                case "Patient":
                    // Cast Staff to Patient and pass it to PatientView
                    Patient patient = new Patient(staff.getId(), staff.getName(), staffId, staff.getBloodType());
                    PatientView patientView = new PatientView(patient);
                    patientView.handleUserChoice(); // Show the patient menu and handle actions
                    break;
                default:
                    System.out.println("Role not recognized.");
            }
        } else {
            System.out.println("Invalid staff ID or password.");
        }

        scanner.close();
    }

    // Method to load staff details from CSV
    private static Map<String, Staff> loadStaffDetails(String filePath) {
        Map<String, Staff> staffMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header line

            while ((line = br.readLine()) != null) {
                // Split the line into values
                String[] values = line.split(",");

                if (values.length < 10) continue;

                // Extract data from the CSV line
                String id = values[0].trim();
                String name = values[1].trim();
                String role = values[2].trim();
                String gender = values[3].trim();
                int age = Integer.parseInt(values[4].trim());
                String dateOfBirth = values[5].trim();
                String bloodType = values[6].trim();
                String contactInfo = values[7].trim();
                String password = values[8].trim();
                int newUser = Integer.parseInt(values[9].trim());

                // Create a Staff object and add it to the map
                Staff staff = new Staff(id, name, role, gender, age, dateOfBirth, bloodType, contactInfo, password, newUser);
                staffMap.put(id, staff);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return staffMap;
    }
}
