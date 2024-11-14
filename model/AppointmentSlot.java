package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AppointmentSlot {
    
    private static final String appointment_filepath = "assets/appointment.csv";

    // Method to get the next available Appointment ID
    private String getNextAppointmentID() {
        int maxId = 0; // Start with 0
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header if present
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0) {
                    try {
                        int currentId = Integer.parseInt(values[0].trim().replace("APPT", ""));
                        if (currentId > maxId) {
                            maxId = currentId;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing Appointment ID: " + values[0]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
        return "APPT" + (maxId + 1); // Increment and return the next ID
    }

    public void addAppointment(String doctorID) {
        String appointmentID = getNextAppointmentID();
        String patientID = ""; 
        String date, time;
        String status = "Available"; // Default status

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Date (DD/MM/YYYY): ");
        date = scanner.nextLine().trim();
        
        System.out.print("Enter Time (HH:MM AM/PM): ");
        time = scanner.nextLine().trim();

        // Call the method to add the appointment
        writeToCSV(appointmentID, doctorID, patientID, date, time, status);
    }

    private void writeToCSV(String appointmentID, String doctorID, String patientID, String date, String time, String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointment_filepath, true))) {
            // Write the new appointment to the CSV file
            writer.write(appointmentID + "," + doctorID + "," + patientID + "," + date + "," + time + "," + status);
            writer.newLine();
            System.out.println("Appointment added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the appointment file: " + e.getMessage());
        }
    }

    public void setAvailability(String doctorID) {
        addAppointment(doctorID); 
    }

    public void viewPersonalSchedule(String doctorID) {

        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); 
            System.out.println("Upcoming Appointments for Doctor ID: " + doctorID);
    
            boolean hasAppointments = false; 
            int lineCount = 0; 
    
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] values = line.split(",");
    
                if (values.length >= 6) { // Ensure there are enough columns
                    String appointmentID = values[0];
                    String appointmentDoctorID = values[1];
                    String patientID = values[2];
                    String date = values[3];
                    String time = values[4];
                    String status = values[5];
    
                    // Match doctorID only, skipping date-time check
                    if (appointmentDoctorID.equals(doctorID)) {
                        System.out.println("Appointment ID: " + appointmentID +
                                ", Patient ID: " + patientID +
                                ", Date: " + date +
                                ", Time: " + time +
                                ", Status: " + status);
                        hasAppointments = true;
                    }
                } else {
                    System.out.println("Skipping line " + lineCount + " due to insufficient columns.");
                }
            }
    
            if (!hasAppointments) {
                System.out.println("No appointments found for Doctor ID: " + doctorID);
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    }

    public void viewUpcomingAppointment(String doctorID) { 
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); 
            System.out.println("Upcoming Appointments for Doctor ID: " + doctorID);
    
            boolean hasAppointments = false; 
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] values = line.split(",");
    
                if (values.length >= 6) { 
                    String appointmentID = values[0];
                    String appointmentDoctorID = values[1];
                    String patientID = values[2];
                    String date = values[3];
                    String time = values[4];
                    String status = values[5];
 
                    if (appointmentDoctorID.equals(doctorID) && status.equalsIgnoreCase("Confirmed")) {
                        System.out.println("Appointment ID: " + appointmentID +
                                ", Patient ID: " + patientID +
                                ", Date: " + date +
                                ", Time: " + time +
                                ", Status: " + status);
                        hasAppointments = true;
                    }
                } else {
                    System.out.println("Skipping line " + lineCount + " due to insufficient columns.");
                }
            }
    
            if (!hasAppointments) {
                System.out.println("No available appointments found for Doctor ID: " + doctorID);
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    }
    
    
}