import java.util.Scanner;

public class Main {
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
        }

        scanner.close();
    }
}
