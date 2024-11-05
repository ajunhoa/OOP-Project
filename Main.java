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
        System.out.println("Enter your name:");
        String name = scanner.nextLine().trim();

        // Check if the name exists in the staff map (case insensitive)
        boolean found = false;

        for (String staffName : staffMap.keySet()) {
            if (staffName.equalsIgnoreCase(name)) {
                found = true;
                Staff staff = staffMap.get(staffName);
                System.out.println("Welcome, " + staffName);
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
                break;
            }
        }

        if (!found) {
            System.out.println("Name not found in staff list.");
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
                String[] values = line.split(",");
                if (values.length < 8) continue; // Ensure there are enough columns

                String id = values[0].trim();
                String name = values[1].trim();
                String role = values[2].trim();
                String gender = values[3].trim();
                int age = 0;
                
                // Handle age parsing with error handling
                try {
                    age = values[4].isEmpty() ? 0 : Integer.parseInt(values[4].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age format for " + name);
                    continue; // Skip this entry if age is invalid
                }

                String dateOfBirth = values[5].trim(); // New field
                String bloodType = values[6].trim();   // New field
                String contactInfo = values[7].trim(); // New field

                // Create a Staff object and add it to the map
                Staff staff = new Staff(id, name, role, gender, age, dateOfBirth, bloodType, contactInfo);
                staffMap.put(name, staff);
            }

            // Debugging: Print all names loaded into the staffMap
            System.out.println("Loaded staff names: " + staffMap.keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return staffMap;
    }
}
