import controller.UserController;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.Doctor;
import model.Patient;
import model.Staff;
import model.User;
import view.DoctorView;
import view.PatientView;

public class Main {
    public static void main(String[] args) {
        String patientFilePath = "assets/updatedpatientlist.csv";
        String staffFilePath = "assets/updatedstafflist.csv";
        
        // Load both patient and staff data
        Map<String, User> userMap = new HashMap<>();
        userMap.putAll(loadUserDetails(patientFilePath, "Patient"));
        userMap.putAll(loadUserDetails(staffFilePath, "Staff"));

        try (Scanner scanner = new Scanner(System.in)) {    
            System.out.println("Welcome to the Hospital Management System");
            System.out.print("Enter your User ID: ");
            String userId = scanner.nextLine().trim().toUpperCase();

            System.out.print("Enter your Password: ");
            String password = scanner.nextLine().trim();

            User user = userMap.get(userId);

            if (user != null && user.validatePassword(password)) {
                System.out.println("Welcome, " + user.getName());
                
                String filePath = user.getRole().equals("Patient") ? patientFilePath : staffFilePath;
                UserController.promptPasswordChange(user, filePath, scanner);  // Pass scanner here

                switch (user.getRole()) {   
                    case "Doctor":
                        Doctor doctor = new Doctor(user.getId(), user.getName(), user.getRole());
                        DoctorView doctorView = new DoctorView(doctor);
                        doctorView.displayDoctorMenu(userId);
                        break;
                    case "Pharmacist":
                        System.out.println("Accessing Pharmacist's functionalities...");
                        break;
                    case "Administrator":
                        System.out.println("Accessing Administrator's functionalities...");
                        break;
                    case "Patient":
                        // Removed unnecessary code that was commented out
                        Patient patient = new Patient(user.getId(), user.getName(), user.getDateOfBirth(), user.getGender(), user.getBloodType(), user.getContactInfo(), user.isNewUser() ? 1 : 0, user.getPassword(), user.getContactNumber());
                        PatientView patientView = new PatientView(patient, scanner);  // Pass scanner here
                        patientView.handleUserChoice();
                        break;
                    default:
                        System.out.println("Role not recognized.");
                }
            } else {
                System.out.println("Invalid User ID or Password.");
            }
        }
    }

    private static Map<String, User> loadUserDetails(String filePath, String defaultRole) {
        Map<String, User> userMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header line if present

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] values = line.split(",");

                // Handle Patient format (9 columns)
                if (values.length == 9 && defaultRole.equals("Patient")) {
                    try {
                        String id = values[0].trim();
                        String name = values[1].trim();
                        String dateOfBirth = values[2].trim();
                        String gender = values[3].trim();
                        String bloodType = values[4].trim();
                        String contactInfo = values[5].trim();
                        int newUser = Integer.parseInt(values[6].trim());
                        String password = values[7].trim();
                        int contactNumber = Integer.parseInt(values[8].trim());

                        Patient patient = new Patient(id, name, dateOfBirth, gender, bloodType, contactInfo, newUser, password, contactNumber);
                        userMap.put(id, patient);
                        System.out.println("Loaded Patient: " + patient.toString());
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing numeric values in patient line: " + line);
                    }
                }
                // Handle Staff format (7 columns)
                else if (values.length == 7 && defaultRole.equals("Staff")) {
                    try {
                        String id = values[0].trim();
                        String name = values[1].trim();
                        String role = values[2].trim();
                        String gender = values[3].trim();
                        int age = Integer.parseInt(values[4].trim());
                        int newUser = Integer.parseInt(values[5].trim());
                        String password = values[6].trim();

                        User staff = new Staff(id, name, role, gender, age, newUser, password);
                        userMap.put(id, staff);
                        System.out.println("Loaded Staff: " + staff.toString());
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing numeric values in staff line: " + line);
                    }
                } else {    
                    System.out.println("Skipping line, not enough columns: " + line);
                }
            }   

            System.out.println("Loaded user IDs from " + filePath + ": " + userMap.keySet());
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        return userMap;
    }

}
