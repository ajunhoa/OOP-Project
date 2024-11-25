package model;

import controller.StaffController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * The AppointmentSlot class manages appointment scheduling within the hospital management system.
 * It provides functionalities to add appointments, view personal schedules, manage appointment requests,
 * and update appointment statuses.
 */
public class AppointmentSlot {
    
    /** The file path for storing appointment details. */
    private static final String appointment_filepath = "assets/appointment.csv";
    
    /** Scanner for reading user input. */
    private Scanner scanner;

    /**
     * Constructs an AppointmentSlot instance and initializes the scanner.
     */
    public AppointmentSlot() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Generates the next available appointment ID based on existing appointment IDs in the file.
     *
     * @return The next appointment ID in the format "APPT{number}".
     */
    private String getNextAppointmentID() {
        int maxId = 0; 
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
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
        return "APPT" + (maxId + 1); 
    }

    /**
     * Adds a new appointment for the specified doctor.
     *
     * @param doctorID The ID of the doctor for whom the appointment is being added.
     */
    public void addAppointment(String doctorID) {
        String appointmentID = getNextAppointmentID();
        String patientID = ""; 
        String date, time;
        String status = "Available"; 
    
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Date (DD/MM/YYYY): ");
        date = scanner.nextLine().trim();
        
        // Validate the date format
        if (!isValidDate(date)) {
            System.out.println("Invalid date format. Please enter the date as DD/MM/YYYY.");
            return; // Exit the method if the date format is invalid
        }
    
        System.out.print("Enter Time (HH:MM AM/PM): ");
        time = scanner.nextLine().trim();
        time = standardizeTime(time);
        if (time.isEmpty()) {
            System.out.println("Invalid time format. Please enter time as HH:MM AM/PM.");
            return; // Exit the method if the time format is invalid
        }

        
        if (isAppointmentConflict(doctorID, date, time)) {
            System.out.println("Cannot schedule appointment. There is already an appointment for this doctor on " + date + " at " + time + ".");
            return; 
        }
    
        updateCSV(appointmentID, doctorID, patientID, date, time, status);
    }
    
    /**
     * Validates the date format (DD/MM/YYYY).
     *
     * @param date The input date string.
     * @return true if the date format is valid, false otherwise.
     */
    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // Set lenient to false to strictly parse the date
        try {
            Date parsedDate = sdf.parse(date); // This will throw ParseException if the date is invalid
            return true; 
        } catch (ParseException e) {
            return false; 
        }
    }
    
    /**
     * Standardizes the time input to a consistent format (HH:MM AM/PM).
     *
     * @param time The input time string.
     * @return The standardized time string.
     */
    private String standardizeTime(String time) {
        try {
            // Split the input into time and period (AM/PM)
            String[] parts = time.split(" ");
            if (parts.length != 2) { // Ensure both time and period are provided
                return ""; // Invalid format
            }
    
            String timePart = parts[0]; // This should be "HH:MM"
            String period = parts[1].toUpperCase(); // This should be "AM" or "PM"
    
            if (!period.equals("AM") && !period.equals("PM")) { // Check for valid period
                return ""; // Invalid period
            }
    
            String[] timeComponents = timePart.split(":");
            if (timeComponents.length != 2) { // Ensure both hour and minute are provided
                return ""; // Invalid time format
            }
    
            int hour = Integer.parseInt(timeComponents[0]);
            int minute = Integer.parseInt(timeComponents[1]);
    
            // Check valid hour and minute ranges
            if (hour < 1 || hour > 12 || minute < 0 || minute > 59) {
                return ""; // Invalid hour or minute
            }
    
            // Return the standardized time
            return String.format("%02d:%02d %s", hour, minute, period);
        } catch (Exception e) {
            return ""; // Return an empty string if there are parsing errors
        }
    
    
    
    }

    
    
    /**
     * Checks if there is an existing appointment for the specified doctor at the given date and time.
     *
     * @param doctorID The ID of the doctor to check for conflicts.
     * @param date The date of the appointment to check.
     * @param time The time of the appointment to check.
     * @return true if there is a conflict, false otherwise.
     */
    private boolean isAppointmentConflict(String doctorID, String date, String time) {
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    String appointmentDoctorID = values[1];
                    String appointmentDate = values[3];
                    String appointmentTime = values[4];
                    String status = values[5];
    
                    // Check if the doctor ID, date, and time match and the status is not cancelled
                    if (appointmentDoctorID.equals(doctorID) && appointmentDate.equals(date) && appointmentTime.equals(time) && !status.equalsIgnoreCase("Cancelled")) {
                        return true; // Conflict found
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
        return false; // No conflict found
    }

    /**
     * Updates the appointment CSV file with the new appointment details.
     *
     * @param appointmentID The ID of the appointment.
     * @param doctorID The ID of the doctor.
     * @param patientID The ID of the patient.
     * @param date The date of the appointment.
     * @param time The time of the appointment.
     * @param status The status of the appointment.
     */
    private void updateCSV(String appointmentID, String doctorID, String patientID, String date, String time, String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointment_filepath, true))) {
            writer.write(appointmentID + "," + doctorID + "," + patientID + "," + date + "," + time + "," + status);
            writer.newLine();
            System.out.println("Appointment added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the appointment file: " + e.getMessage());
        }
    }

    /**
     * Sets the availability for a specific doctor by adding an appointment.
     *
     * @param doctorID The ID of the doctor to set availability for.
     */
    public void setAvailability(String doctorID) {
        addAppointment(doctorID); 
    }

    /**
     * Views the personal schedule for a specific doctor.
     *
     * @param doctorID The ID of the doctor whose schedule is to be viewed.
     */
    public void viewPersonalSchedule(String doctorID) {
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
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

    /**
     * Views upcoming appointments for a specific doctor that are confirmed.
     *
     * @param doctorID The ID of the doctor whose upcoming appointments are to be viewed.
     */
    public void viewUpcomingAppointment(String doctorID) { 
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
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

    /**
     * Manages appointment requests for a specific doctor, allowing them to accept or decline appointments.
     *
     * @param doctorID The ID of the doctor managing appointment requests.
     */
    public void manageAppointmentRequests(String doctorID) {
        List<String[]> pendingAppointments = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[1].equals(doctorID) && values[5].equalsIgnoreCase("Pending")) {
                    pendingAppointments.add(values);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    
        if (pendingAppointments.isEmpty()) {
            System.out.println("No pending appointment requests for Doctor ID: " + doctorID);
            return;
        }
    
        for (String[] appointment : pendingAppointments) {
            boolean validChoice = false; 
            while (!validChoice) { 
                System.out.println("Appointment ID: " + appointment[0] +
                        ", Patient ID: " + appointment[2] +
                        ", Date: " + appointment[3] +
                        ", Time: " + appointment[4]);
    
                System.out.print("Do you want to Accept (A) or Decline (D) this appointment? ");
                String choice = scanner.nextLine().trim().toUpperCase();
    
                if (choice.equals("A")) {
                    updateAppointmentStatus(appointment[0], "Confirmed");
                    System.out.println("Appointment " + appointment[0] + " has been confirmed.");
                    validChoice = true; 
                } else if (choice.equals("D")) {
                    updateAppointmentStatus(appointment[0], "Cancelled");
                    System.out.println("Appointment " + appointment[0] + " has been cancelled.");
                    validChoice = true; 
                } else {
                    System.out.println("Invalid choice. Please enter A or D.");
                }
            }
        }
    }

    /**
     * Updates the status of a specific appointment identified by its ID.
     *
     * @param appointmentID The ID of the appointment to update.
     * @param newStatus The new status to set for the appointment.
     */
    public void updateAppointmentStatus(String appointmentID, String newStatus) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[0].equals(appointmentID)) {
                    values[5] = newStatus; 
                    line = String.join(",", values);
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }

        // Write the updated lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointment_filepath))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            System.out.println("Appointment status updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the appointment file: " + e.getMessage());
        }
    }

    /**
     * Views all appointments stored in the appointment file.
     */
    public void viewAllAppointments() {
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
            System.out.println("All Appointments:");
    
            boolean hasAppointments = false; 
            int lineCount = 0;
    
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] values = line.split(",");
    
                if (values.length >= 6) { 
                    String appointmentID = values[0];
                    String doctorID = values[1];
                    String patientID = values[2];
                    String date = values[3];
                    String time = values[4];
                    String status = values[5];
    
                    System.out.println("Appointment ID: " + appointmentID +
                                       ", Doctor ID: " + doctorID +
                                       ", Patient ID: " + patientID +
                                       ", Date: " + date +
                                       ", Time: " + time +
                                       ", Status: " + status);
                    hasAppointments = true;
                } else {
                    System.out.println("Skipping line " + lineCount + " due to insufficient columns.");
                }
            }
    
            if (!hasAppointments) {
                System.out.println("No appointments found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    }

    /**
     * Views available appointment slots for doctors.
     */
    public void viewAvailableAppointmentSlots() {
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
            System.out.println("Available Appointment Slots:");
    
            boolean hasAvailableSlots = false; 
            StaffController staffController = new StaffController(); 
    
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
    
                if (values.length >= 6) { 
                    String appointmentID = values[0];
                    String doctorID = values[1];
                    String patientID = values[2];
                    String date = values[3];
                    String time = values[4];
                    String status = values[5];
    
                    if (status.equalsIgnoreCase("Available") || status.equalsIgnoreCase("Cancelled")) {
                        String doctorName = staffController.getDoctorNameByID(doctorID); 
    
                        System.out.println("Appointment ID: " + appointmentID +
                                           ", Doctor Name: " + doctorName + 
                                           ", Date: " + date +
                                           ", Time: " + time);
                        hasAvailableSlots = true;
                    }
                }
            }
    
            if (!hasAvailableSlots) {
                System.out.println("No available appointment slots found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    }

    /**
     * Views scheduled appointments for a specific patient.
     *
     * @param patientID The ID of the patient whose scheduled appointments are to be viewed.
     */
    public void viewScheduledAppointments(String patientID) {
        List<String[]> scheduledAppointments = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
            System.out.println("Scheduled Appointments for Patient ID: " 
            + patientID);
            
            int displayCount = 1; 
            StaffController staffController = new StaffController(); 

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[2].equalsIgnoreCase(patientID) && 
                    (values[5].equalsIgnoreCase("Pending") || values[5].equalsIgnoreCase("Confirmed"))) {
                    scheduledAppointments.add(values);
                    String doctorName = staffController.getDoctorNameByID(values[1]); 
                    System.out.println(displayCount + 
                                       ": Appointment ID: " + values[0] +
                                       ", Doctor Name: " + doctorName + 
                                       ", Date: " + values[3] +
                                       ", Time: " + values[4] +
                                       ", Status: " + values[5]);
                    displayCount++;
                }
            }

            if (scheduledAppointments.isEmpty()) {
                System.out.println("No scheduled appointments found for Patient ID: " + patientID);
            }

        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    }

    /**
     * Counts the number of pending appointments for a specific doctor.
     *
     * @param doctorID The ID of the doctor whose pending appointments are to be counted.
     * @return The number of pending appointments for the specified doctor.
     */
    public int countPendingAppointments(String doctorID) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[1].equalsIgnoreCase(doctorID) && values[5].equalsIgnoreCase("Pending")) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
        return count;
    }
}