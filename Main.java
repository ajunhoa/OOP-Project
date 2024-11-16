import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);

        // Creating a sample patient
        Patient patient = new Patient("P001", "John Doe", "PID123", "O+");
        
        // Creating a sample doctor
        Doctor doctor = new Doctor("D001", "Dr. Smith", "DID456");

        // User selects a role to login (for this example, we’ll work with a patient)
        System.out.println("Welcome to the Hospital Management System");
        System.out.println("Select your role: ");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Pharmacist");
        System.out.println("4. Administrator");
        int role = scanner.nextInt();

<<<<<<< Updated upstream
        // Switch based on the selected role
        switch (role) {
            case 1:
                // Patient role selected
                PatientView patientView = new PatientView(patient);  // Create PatientView with Patient object
                patientView.handleUserChoice();  // Call the method to handle patient menu
                break;
            case 2:
                // Doctor Menu (similar logic for doctor can be implemented)
                break;
            case 3:
                // Pharmacist Menu (similar logic for pharmacist can be implemented)
                break;
            case 4:
                // Administrator Menu (similar logic for administrator can be implemented)
                break;
            default:
                System.out.println("Invalid role selected.");
=======
        MedicalRecordController medicalRecordController = new MedicalRecordController(medicalRecordMap, userMap, medicalFilePath, patientFilePath);

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
                UserController.promptPasswordChange(user, filePath, scanner);

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
                System.out.println("Invalid User ID or Password.");
            }
>>>>>>> Stashed changes
        }

        scanner.close();
    }
}