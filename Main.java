import controller.MedicalRecordController;
import controller.UserController;
import java.util.Map;
import java.util.Scanner;
import model.DataInitializer;
import model.Doctor;
import model.MedicalRecord;
import model.Patient;
import model.User;
import view.AdministratorView;
import view.DoctorView;
import view.PatientView;
import view.PharmacistView;    

/**
 * The Main class serves as the entry point for the Hospital Management System application.
 * It initializes the system by loading patient and staff details from CSV files, linking medical
 * records to patients, and presenting a user interface based on the user's role.
 */
public class Main {
    /**
     * The main method that runs the Hospital Management System.
     * It performs the following tasks:
     * <ul>
     *     <li>Loads patient and staff details from CSV files.</li>
     *     <li>Links medical records to patients.</li>
     *     <li>Prompts the user for their User ID and Password.</li>
     *     <li>Validates the user's credentials and determines their role.</li>
     *     <li>Displays the appropriate menu based on the user's role (Doctor, Pharmacist, Administrator, or Patient).</li>
     * </ul>
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        String patientFilePath = "assets/updatedpatientlist.csv";
        String staffFilePath = "assets/updatedstafflist.csv";
        String medicalFilePath = "assets/medicalrecords.csv";
    
        // Load user and medical record data
        Map<String, User> userMap = DataInitializer.loadPatientDetails(patientFilePath);
        userMap.putAll(DataInitializer.loadStaffDetails(staffFilePath));
    
        Map<String, MedicalRecord> medicalRecordMap = DataInitializer.loadMedicalRecords(medicalFilePath);
        DataInitializer.linkMedicalRecordsToPatients(userMap, medicalRecordMap);
    
        // Initialize the MedicalRecordController
        MedicalRecordController medicalRecordController = new MedicalRecordController(medicalRecordMap, userMap, medicalFilePath, patientFilePath);
    
        try (Scanner scanner = new Scanner(System.in)) {
            boolean loggedIn = false;
            int attempts = 0;
            final int maxAttempts = 3;
            User user = null;
    
            // Prompt for User ID first
            System.out.println("\nWelcome to the Hospital Management System");
            System.out.print("Enter your User ID: ");
            String userId = scanner.nextLine().trim().toUpperCase();
    
            // Check if the User ID exists
            user = userMap.get(userId);
    
            if (user == null) {
                System.out.println("Invalid User ID. Exiting program.");
                System.exit(0);
            }
    
            // Password entry loop
            while (!loggedIn && attempts < maxAttempts) {
                attempts++;
                System.out.print("Enter your Password: ");
                String password = scanner.nextLine().trim();
    
                if (user.validatePassword(password)) {
                    System.out.println("Welcome, " + user.getName());
                    loggedIn = true;
    
                    // Prompt for password change if necessary
                    String filePath = user.getRole().equals("Patient") ? patientFilePath : staffFilePath;
                    UserController.promptPasswordChange(user, filePath, scanner);
    
                    // Role-based menu handling
                    switch (user.getRole()) {
                        case "Doctor":
                            Doctor doctor = new Doctor(user.getId(), user.getName(), user.getRole());
                            DoctorView doctorView = new DoctorView(doctor, medicalRecordController);
                            doctorView.showMenu(userId);
                            break;
    
                        case "Pharmacist":
                            PharmacistView pharmacistView = new PharmacistView(scanner);
                            pharmacistView.showMenu();
                            break;
    
                        case "Administrator":
                            AdministratorView administratorView = new AdministratorView(scanner);
                            administratorView.showMenu();
                            break;
    
                        case "Patient":
                            Patient patient = (Patient) user;
                            PatientView patientView = new PatientView(patient, scanner, medicalRecordController);
                            patientView.showMenu();
                            break;
    
                        default:
                            System.out.println("Role not recognized.");
                    }
                } else {
                    System.out.println("Invalid Password. Please try again.");
                    if (attempts == maxAttempts) {
                        System.out.println("Too many failed attempts. Exiting the program.");
                    }
                }
            }
    
            if (!loggedIn) {
                System.exit(0); // Exit the program after max attempts
            }
        }
    }
    
}
