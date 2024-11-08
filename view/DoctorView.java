package view;

import model.Doctor;
import java.util.Scanner;

public class DoctorView {
    private Doctor doctor;
    private Scanner scanner;

    public DoctorView(Doctor doctor) {
        this.doctor = doctor;
        this.scanner = new Scanner(System.in);
    }

    public void displayDoctorMenu() {
        boolean exit = false;
        while (!exit) {
            this.displayMenu();
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
                    // viewUpcomingAppointments();  // Uncomment if Appointment class exists
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
        doctor.viewPatientRecord(patientID);  // Call method directly on doctor
    }

    private void setDoctorAvailability() {
        System.out.print("Enter Date (e.g., 2023-09-19): ");
        String date = scanner.nextLine();
        System.out.print("Enter Time (e.g., 10:00 AM): ");
        String time = scanner.nextLine();
        doctor.setAvailability(date, time);  // Call method directly on doctor
    }

    private void manageAppointmentRequests() {
        System.out.print("Enter Appointment ID: ");
        String appointmentID = scanner.nextLine();
        System.out.print("Accept appointment? (yes/no): ");
        boolean accept = scanner.nextLine().equalsIgnoreCase("yes");
        doctor.manageAppointment(appointmentID, accept);  // Call method directly on doctor
    }

    // Uncomment if Appointment class exists
    // private void viewUpcomingAppointments() {
    //     System.out.println("Upcoming appointments:");
    //     for (Appointment appointment : doctor.getUpcomingAppointments()) {
    //         System.out.println(appointment);
    //     }
    // }
         public void displayMenu() {
        System.out.println("\n=== Doctor Menu ===");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Set Availability for Appointments");
        System.out.println("3. Accept or Decline Appointment Requests");
        System.out.println("4. View Upcoming Appointments");
        System.out.println("5. Logout");
    }
       
}
