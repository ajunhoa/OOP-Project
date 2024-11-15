package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import controller.StaffController;

public class AppointmentOutcomeRecord {
    private static final String outcomeFilePath = "assets/appointment_outcome.csv";
    private static final String appointmentFilePath = "assets/appointment.csv"; // Path to appointment.csv
    private Scanner scanner;
    private AppointmentSlot appointmentSlot; // Instance of AppointmentSlot for updating appointments

    public AppointmentOutcomeRecord() {
        this.scanner = new Scanner(System.in);
        this.appointmentSlot = new AppointmentSlot(); // Create an instance here
    }

    // Method to add a new appointment outcome record
    public void addAppointmentOutcome(String appointmentID, String patientID, String doctorID, String dateOfAppointment, String typeOfServices, String medicinePrescribed, String consultationNotes, String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outcomeFilePath, true))) {
            // Write the new outcome record to the CSV file
            writer.write(appointmentID + "," + patientID + "," + doctorID + "," + dateOfAppointment + "," + typeOfServices + "," + medicinePrescribed + "," + consultationNotes + "," + status);
            writer.newLine();
            System.out.println("Appointment outcome recorded successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the appointment outcome file: " + e.getMessage());
        }
    }

    public void recordAppointmentOutcome(String doctorID) {
        System.out.print("Enter Appointment ID: ");
        String appointmentID = scanner.nextLine();

        // Check for existing Appointment ID and retrieve Patient ID and Date of Appointment
        String[] appointmentDetails = getAppointmentDetails(appointmentID);
        if (appointmentDetails == null) {
            System.out.println("Appointment ID not found. Please enter a valid Appointment ID.");
            return;
        }

        String patientID = appointmentDetails[0]; // Patient ID
        String dateOfAppointment = appointmentDetails[1]; // Date of Appointment
        String apptStatus = appointmentDetails[2]; // Status

        // Check if the appointment status is "Confirmed"
        if (!apptStatus.equalsIgnoreCase("Confirmed")) {
            System.out.println("Cannot record appointment outcome. Appointment is not confirmed.");
            return;
        }

        System.out.print("Enter Type of Services: ");
        String typeOfServices = scanner.nextLine();
        System.out.print("Enter Medicine Prescribed: ");
        String medicinePrescribed = scanner.nextLine();
        System.out.print("Enter Consultation Notes: ");
        String consultationNotes = scanner.nextLine();

        // Add the appointment outcome to appointment_outcome.csv
        String status = "Pending"; // Default status for outcomes
        addAppointmentOutcome(appointmentID.toUpperCase(), patientID.toUpperCase(), doctorID.toUpperCase(), dateOfAppointment, typeOfServices, medicinePrescribed, consultationNotes, status);

        // Use AppointmentSlot to update the appointment.csv
        appointmentSlot.updateAppointmentStatus(appointmentID, "Completed");
        System.out.println("Appointment ID: " + appointmentID + " marked as 'Completed' in appointment.csv.");
    }

    // Method to retrieve appointment details based on Appointment ID
    private String[] getAppointmentDetails(String appointmentID) {
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
            br.readLine(); // Skip header if present
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[0].equalsIgnoreCase(appointmentID)) {
                    // Return Patient ID, Date of Appointment, and Status
                    return new String[]{values[2], values[3], values[5]};
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
        return null;
    }

    public void viewAppointmentOutcomeRecord() {
        try (BufferedReader br = new BufferedReader(new FileReader(outcomeFilePath))) {
            String line;
            br.readLine(); // Skip header if present
            System.out.println("Appointment Outcome Records:");

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8) {
                    System.out.println("Appointment ID: " + values[0] +
                            ", Patient ID: " + values[1] +
                            ", Doctor ID: " + values[2] +
                            ", Date of Appointment: " + values[3] +
                            ", Type of Services: " + values[4] +
                            ", Medicine Prescribed: " + values[5] +
                            ", Consultation Notes: " + values[6] +
                            ", Status: " + values[7]);
                } else {
                    System.out.println("Skipping line due to insufficient columns: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment outcome file: " + e.getMessage());
        }
    }


private boolean updateAppointmentStatus(String appointmentID, String newStatus) {
    List<String> lines = new ArrayList<>();
    boolean isUpdated = false;

    try (BufferedReader br = new BufferedReader(new FileReader(outcomeFilePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length >= 8 && values[0].equalsIgnoreCase(appointmentID)) {
                values[7] = newStatus; 
                line = String.join(",", values);
                isUpdated = true; 
            }
            lines.add(line);
        }
    } catch (IOException e) {
        System.out.println("Error reading the appointment outcome file: " + e.getMessage());
        return false;
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outcomeFilePath))) {
        for (String l : lines) {
            writer.write(l);
            writer.newLine();
        }
        return isUpdated;
    } catch (IOException e) {
        System.out.println("Error writing to the appointment outcome file: " + e.getMessage());
        return false;
    }
}

public void viewCompletedOutcomeRecord() {
    try (BufferedReader br = new BufferedReader(new FileReader(outcomeFilePath))) {
        String line;
        br.readLine(); // Skip header if present
        System.out.println("Completed Appointment Outcome Records:");

        boolean hasCompletedRecords = false; 
        int lineCount = 0;

        while ((line = br.readLine()) != null) {
            lineCount++;
            String[] values = line.split(",");

            if (values.length >= 8 && values[7].equalsIgnoreCase("Completed")) { 
                System.out.println("Appointment ID: " + values[0] +
                                   ", Patient ID: " + values[1] +
                                   ", Doctor ID: " + values[2] +
                                   ", Date of Appointment: " + values[3] +
                                   ", Type of Services: " + values[4] +
                                   ", Medicine Prescribed: " + values[5] +
                                   ", Consultation Notes: " + values[6] +
                                   ", Status: " + values[7]);
                hasCompletedRecords = true;
            }
        }

        if (!hasCompletedRecords) {
            System.out.println("No completed appointment outcome records found.");
        }
    } catch (IOException e) {
        System.out.println("Error reading the appointment outcome file: " + e.getMessage());
    }
}

public void viewPastAppointmentOutcomeRecords(String patientID) {
    try (BufferedReader br = new BufferedReader(new FileReader(outcomeFilePath))) {
        String line;
        br.readLine(); // Skip header if present
        System.out.println("Past Appointment Outcome Records for Patient ID: " + patientID);

        boolean hasRecords = false; // Flag to check if records are found
        StaffController staffController = new StaffController(); // Create an instance of StaffController

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length >= 8 && values[1].equalsIgnoreCase(patientID)) {
                String doctorName = staffController.getDoctorNameByID(values[2]); // Get doctor name using doctor ID
                // Print the details of the appointment outcome record
                System.out.println("Appointment ID: " + values[0] +
                                   ", Doctor Name: " + doctorName + // Display doctor name
                                   ", Date of Appointment: " + values[3] +
                                   ", Type of Services: " + values[4] +
                                   ", Medicine Prescribed: " + values[5] +
                                   ", Consultation Notes: " + values[6] +
                                   ", Status: " + values[7]);
                hasRecords = true; // Set flag to true if records are found
            }
        }

        if (!hasRecords) {
            System.out.println("No past appointment outcome records found for Patient ID: " + patientID);
        }

    } catch (IOException e) {
        System.out.println("Error reading the appointment outcome file: " + e.getMessage());
    }
}


}
