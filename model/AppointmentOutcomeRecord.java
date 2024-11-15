package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import controller.StaffController;

public class AppointmentOutcomeRecord {
    private static final String outcomeFilePath = "assets/appointment_outcome.csv";
    private static final String appointmentFilePath = "assets/appointment.csv"; 
    private Scanner scanner;
    private AppointmentSlot appointmentSlot; 

    public AppointmentOutcomeRecord() {
        this.scanner = new Scanner(System.in);
        this.appointmentSlot = new AppointmentSlot();
    }

    // Method to add a new appointment outcome record
    public void addAppointmentOutcome(String appointmentID, String patientID, String doctorID, String dateOfAppointment, String typeOfServices, String medicinePrescribed, String consultationNotes, String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outcomeFilePath, true))) {

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

        String[] appointmentDetails = getAppointmentDetails(appointmentID);
        if (appointmentDetails == null) {
            System.out.println("Appointment ID not found. Please enter a valid Appointment ID.");
            return;
        }

        String patientID = appointmentDetails[0]; 
        String dateOfAppointment = appointmentDetails[1]; 
        String apptStatus = appointmentDetails[2];

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

        
        String status = "Pending";
        addAppointmentOutcome(appointmentID.toUpperCase(), patientID.toUpperCase(), doctorID.toUpperCase(), dateOfAppointment, typeOfServices, medicinePrescribed, consultationNotes, status);

        appointmentSlot.updateAppointmentStatus(appointmentID, "Completed");
        System.out.println("Appointment ID: " + appointmentID + " marked as 'Completed' in appointment.csv.");
    }

    private String[] getAppointmentDetails(String appointmentID) {
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[0].equalsIgnoreCase(appointmentID)) {
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
            br.readLine();
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

    public void updateMedicineStatus() {
        List<String[]> pendingAppointments = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(outcomeFilePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8 && values[7].equalsIgnoreCase("Pending")) {
                    pendingAppointments.add(values);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment outcome file: " + e.getMessage());
        }
    
        // If there are no pending appointments
        if (pendingAppointments.isEmpty()) {
            System.out.println("No pending appointments found.");
            return;
        }
    
        // Display pending appointments
        System.out.println("Pending Appointment Outcomes:");
        for (int i = 0; i < pendingAppointments.size(); i++) {
            String[] appointment = pendingAppointments.get(i);
            System.out.println((i + 1) + ". Appointment ID: " + appointment[0] +
                               ", Patient ID: " + appointment[1] +
                               ", Doctor ID: " + appointment[2] +
                               ", Date of Appointment: " + appointment[3] +
                               ", Type of Services: " + appointment[4] +
                               ", Medicine Prescribed: " + appointment[5] +
                               ", Consultation Notes: " + appointment[6]);
        }
    
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Appointment ID that you have dispensed medicine to: ");
        String selectedAppointmentID = scanner.nextLine().trim().toUpperCase();
    
        if (updateAppointmentStatus(selectedAppointmentID, "Dispensed")) {
            System.out.println("Appointment " + selectedAppointmentID + " status updated to 'Dispensed'.");
        } else {
            System.out.println("Failed to update the status. Please ensure the Appointment ID is correct.");
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
        br.readLine(); 
        System.out.println("Past Appointment Outcome Records for Patient ID: " + patientID);

        boolean hasRecords = false; 
        StaffController staffController = new StaffController(); 

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length >= 8 && values[1].equalsIgnoreCase(patientID)) {
                String doctorName = staffController.getDoctorNameByID(values[2]);
                System.out.println("Appointment ID: " + values[0] +
                                   ", Doctor Name: " + doctorName + 
                                   ", Date of Appointment: " + values[3] +
                                   ", Type of Services: " + values[4] +
                                   ", Medicine Prescribed: " + values[5] +
                                   ", Consultation Notes: " + values[6]);
                hasRecords = true; 
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
