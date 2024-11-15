import controller.MedicalRecordController;
import controller.UserController;
import java.util.Map;
import java.util.Scanner;
import model.DataInitializer;
import model.Doctor;
import model.MedicalRecord;
import model.Patient;
import model.User;
import view.DoctorView;
import view.PatientView;
import view.PharmacistView;

public class Main {
    public static void main(String[] args) {
        String patientFilePath = "assets/updatedpatientlist.csv";
        String staffFilePath = "assets/updatedstafflist.csv";
        String medicalFilePath = "assets/medicalrecords.csv"; // Path to medical records
        
        // Load patient and staff data
        Map<String, User> userMap = DataInitializer.loadPatientDetails(patientFilePath);
        userMap.putAll(DataInitializer.loadStaffDetails(staffFilePath));  // Load staff separately

        // Load medical records and link them to patients
        Map<String, MedicalRecord> medicalRecordMap = DataInitializer.loadMedicalRecords(medicalFilePath);
        DataInitializer.linkMedicalRecordsToPatients(userMap, medicalRecordMap); // Ensure the medical records are linked to the patients

        // After linking medical records, print for verification
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            if (entry.getValue() instanceof Patient) {
                Patient patient = (Patient) entry.getValue();
                if (patient.getMedicalRecord() != null) {
                    System.out.println("Patient: " + patient.getName() + " has a medical record linked.");
                } else {
                    System.out.println("Patient: " + patient.getName() + " has no medical record linked.");
                }
            }
        }

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
                        PharmacistView pharmacistView = new PharmacistView(scanner);
                        pharmacistView.displayPharmacistMenu(); 
                        break;
                    case "Administrator":
                        System.out.println("Accessing Administrator's functionalities...");
                        break;
                    case "Patient":
                        // Use the existing Patient object from the userMap
                        Patient patient = (Patient) user;
                    
                        // Initialize MedicalRecordController with all required parameters
                        MedicalRecordController medicalRecordController = new MedicalRecordController(
                            medicalRecordMap,         // Map of medical records
                            userMap,                  // Map of users
                            medicalFilePath,          // Path to medical records file
                            patientFilePath           // Path to user/patient list file
                        );
                    
                        // Debugging: Check if the medical record is linked
                        System.out.println("Patient Before View: " + patient);
                        System.out.println("Medical Record Linked: " + (patient.getMedicalRecord() != null ? "Yes" : "No"));
                    
                        // Pass the existing Patient object to the PatientView, along with MedicalRecordController
                        PatientView patientView = new PatientView(patient, scanner, medicalRecordController);
                        patientView.handleUserChoice(); // Display menu and handle actions
                        break;
                    
                    default:    
                        System.out.println("Role not recognized.");
                }
            } else {    
                System.out.println("Invalid User ID or Password.");
            }
        }
    }
}
