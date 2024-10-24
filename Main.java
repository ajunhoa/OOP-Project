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
                // Patient Menu
                boolean exit = false;
                while (!exit) {
                    patient.displayMenu(); // Display the menu options for the patient
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            System.out.println("Medical Record:");
                            System.out.println(patient.viewMedicalRecord());
                            break;
                        case 2:
                            System.out.println("Update Contact Information");
                            System.out.print("Enter new contact number: ");
                            String contactNumber = scanner.next();
                            System.out.print("Enter new email address: ");
                            String email = scanner.next();
                            patient.updateContactInfo(contactNumber, email);
                            System.out.println("Contact information updated.");
                            break;
                        case 3:
                            System.out.println("Viewing available appointment slots...");
                            doctor.showAvailableSlots();
                            break;
                        case 4:
                            System.out.println("Scheduling an appointment...");
                            doctor.showAvailableSlots();
                            System.out.println("Enter your Preferred Slot: ");
                            String chosenSlot = scanner.next();
                            patient.scheduleAppointment(doctor, "2021-09-01", chosenSlot);
                            if(patient.scheduleAppointment(doctor, "2021-09-01", chosenSlot)) {
                                System.out.println("Appointment scheduled successfully.");
                            } else {
                                System.out.println("Appointment could not be scheduled.");
                            }
                            break;
                        case 5:
                            System.out.println("Rescheduling an appointment...");
                            System.out.println("Enter your current appointment time:  ");
                            String currentSlot = scanner.next();
                            System.out.print("Enter your new appointment time: ");
                            String newSlot = scanner.next();
                            boolean rescheduled = patient.scheduleAppointment(doctor, "2021-09-01", newSlot);
                            if (rescheduled) {
                                System.out.println("Appointment rescheduled successfully.");
                            } else {
                                System.out.println("Appointment could not be rescheduled.");
                            }
                            break;
                        case 6:
                            System.out.println("Cancelling an appointment...");
                            // Logic to cancel appointment
                            break;
                        case 7:
                            System.out.println("Viewing past appointment records...");
                            // Logic to view past records
                            break;
                        case 8:
                            exit = true;
                            System.out.println("Logging out...");
                            break;
                        default:
                            System.out.println("Invalid choice, please try again.");
                    }
                }
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
