import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Staff {
    private String id;
    private String name;
    private String role;
    private String gender;
    private int age;
    private String dateOfBirth;
    private String bloodType;
    private String contactInfo;

    public Staff(String id, String name, String role, String gender, int age, String dateOfBirth, String bloodType, String contactInfo) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Role: " + role + ", Gender: " + gender +
               ", Age: " + age + ", Date of Birth: " + dateOfBirth + ", Blood Type: " + bloodType +
               ", Contact Info: " + contactInfo;
    }
}

public class Main {
    public static void main(String[] args) {
        Map<String, Staff> staffMap = loadStaffDetails("finalise_list.csv");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Hospital Management System");
        System.out.println("Enter your staff ID:");
        String staffId = scanner.nextLine().trim().toUpperCase(); // Trim and convert to uppercase

        // Check if the staff ID exists in the staff map
        Staff staff = staffMap.get(staffId);

        if (staff != null) {
            System.out.println("Welcome, " + staff.getName());
            System.out.println(staff);

            // Switch based on the role
            switch (staff.getRole()) {
                case "Doctor":
                    // Doctor logic here
                    break;
                case "Pharmacist":
                    // Pharmacist logic here
                    break;
                case "Administrator":
                    // Administrator logic here
                    break;
                case "Patient":
                    // Patient logic here
                    break;
                default:
                    System.out.println("Role not recognized.");
            }
        } else {
            System.out.println("Staff ID not found in staff list.");
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
                // Debugging: Print each line read from the CSV
                System.out.println("Processing line: " + line);

                // Split the line into values
                String[] values = line.split(",");

                // Ensure there are enough columns to create a staff object
                if (values.length < 2) {
                    System.out.println("Skipping line, not enough columns: " + line);
                    continue; // Skip this iteration
                }

                // Use a try-catch block to handle potential parsing errors
                try {
                    String id = values.length > 0 ? values[0].trim() : "";
                    String name = values.length > 1 ? values[1].trim() : "";
                    String role = values.length > 2 ? values[2].trim() : "";
                    String gender = values.length > 3 ? values[3].trim() : "";
                    int age = values.length > 4 && !values[4].isEmpty() ? Integer.parseInt(values[4].trim()) : 0; // Default to 0 if age is empty
                    String dateOfBirth = values.length > 5 ? values[5].trim() : "";
                    String bloodType = values.length > 6 ? values[6].trim() : "";
                    String contactInfo = values.length > 7 ? values[7].trim() : "";

                    // Create a Staff object and add it to the map with ID as key
                    Staff staff = new Staff(id, name, role, gender, age, dateOfBirth, bloodType, contactInfo);
                    staffMap.put(id, staff); // Store with ID as the key for direct lookup
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing age for line: " + line);
                }
            }

            // Debugging: Print all IDs loaded into the staffMap
            System.out.println("Loaded staff IDs: " + staffMap.keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return staffMap;
    }
}
