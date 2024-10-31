package view;

import controller.DoctorController;
import model.Doctor;
// import model.Patient;
// import model.Appointment;
import java.util.Scanner;

public class DoctorView {
    private DoctorController doctorController;
    private Scanner scanner;

    public DoctorView(DoctorController doctorController) {
        this.doctorController = doctorController;
        this.scanner = new Scanner(System.in);
    }

    public void displayDoctorMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Doctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Set Availability for Appointments");
            System.out.println("3. Accept or Decline Appointment Requests");
            System.out.println("4. View Upcoming Appointments");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewPatientRecord();
                    break;
                case 2:
                    setDoctorAvailability();
                    break;
                case 3:
                    manageAppointmentRequests();
                    break;
                case 4:
                    // viewUpcomingAppointments();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewPatientRecord() {
        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();
        // Patient patient = new Patient(patientID, "John Doe");  // Assume we have this Patient for now
        // doctorController.viewPatientRecord(patient);
    }

    private void setDoctorAvailability() {
        System.out.print("Enter Date (e.g., 2023-09-19): ");
        String date = scanner.nextLine();
        System.out.print("Enter Time (e.g., 10:00 AM): ");
        String time = scanner.nextLine();
        doctorController.setAvailability(date, time);
    }

    private void manageAppointmentRequests() {
        System.out.println("Managing appointments (mocked)...");  
    }

    // private void viewUpcomingAppointments() {
    //     System.out.println("Upcoming appointments:");
    //     for (Appointment appointment : doctorController.getUpcomingAppointments()) {
    //         System.out.println(appointment);
    //     }
    // }
}
