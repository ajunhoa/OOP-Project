package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AppointmentOutcomeRecord {
    private static final String outcomeFilePath = "assets/appointment_outcome.csv";
    private static final String appointmentFilePath = "assets/appointment.csv"; // Path to appointment.csv
    private Scanner scanner;

    public AppointmentOutcomeRecord() {
        this.scanner = new Scanner(System.in);
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
        
        // Check if the appointment status is "Completed"
        if (!apptStatus.equalsIgnoreCase("Completed")) {
            System.out.println("Cannot record appointment outcome. Appointment is not completed.");
            return;
        }

        System.out.print("Enter Type of Services: ");
        String typeOfServices = scanner.nextLine();
        System.out.print("Enter Medicine Prescribed: ");
        String medicinePrescribed = scanner.nextLine();
        System.out.print("Enter Consultation Notes: ");
        String consultationNotes = scanner.nextLine();

        // Pending for Pharmacist to dispense medication
        String status = "Pending";

        // Call the method to add the appointment outcome
        addAppointmentOutcome(appointmentID.toUpperCase(), patientID.toUpperCase(), doctorID.toUpperCase(), dateOfAppointment, typeOfServices, medicinePrescribed, consultationNotes, status);
    }

    // Method to retrieve appointment details based on Appointment ID
    private String[] getAppointmentDetails(String appointmentID) {
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
            br.readLine(); // Skip header if present
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[0].equalsIgnoreCase(appointmentID)) {
                    // Return Patient ID and Date of Appointment
                    return new String[]{values[2], values[3], values[5]}; 
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
        return null;
    }

    public void viewAppointmentOutcomes() {
        try (BufferedReader br = new BufferedReader(new FileReader(outcomeFilePath))) {
            String line;
            System.out.println("Appointment Outcome Records:");
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println("Appointment ID: " + values[0] +
                                   ", Patient ID: " + values[1] +
                                   ", Doctor ID: " + values[2] +
                                   ", Date of Appointment: " + values[3] +
                                   ", Type of Services: " + values[4] +
                                   ", Medicine Prescribed: " + values[5] +
                                   ", Consultation Notes: " + values[6] +
                                   ", Status: " + values[7]);
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment outcome file: " + e.getMessage());
        }
    }
}