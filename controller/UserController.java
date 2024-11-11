package controller;

import java.io.*;
import java.util.Scanner;

import hospitalmanagementsystem.HospitalManagementSystem;
import model.Patient;

/**
 * The controller for managing user accounts within the Hospital Management System (HMS).
 * This class facilitates user authentication and allows for updating user account details like password.
 * @author
 * @version 1.0
 * @since 2024-11-01
 */
public class UserController {

    public static void changePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();

        // Validate the current password
        if (!currentPassword.equals(HospitalManagementSystem.currentUser.getPassword())) {
            System.out.println("Incorrect password!");
            return;
        }

        // Prompt for new password
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return;
        }

        // Set new password in the current user object
        HospitalManagementSystem.currentUser.setPassword(newPassword);

        // Determine the CSV file path based on the user type
        String path = HospitalManagementSystem.currentUser instanceof Patient
                ? "assets/patients.csv"
                : "assets/staff.csv";

        // Update the password in the CSV file
        try {
            File inputFile = new File(path);
            File tempFile = new File("assets/temp_" + path);

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));

            // Read header line and write it to the new file
            String headerLine = reader.readLine();
            writer.println(headerLine); // Assuming headers are already defined in the CSV file

            String line;
            String userId = HospitalManagementSystem.currentUser.getUserID();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String csvUserId = fields[1]; // Assuming UserID is in the second column

                if (csvUserId.equals(userId)) {
                    // Update line with new password for the matched user
                    writer.println(fields[0] + "," + fields[1] + "," + fields[2] + "," + newPassword);
                } else {
                    // Write the line as is for other users
                    writer.println(line);
                }
            }

            writer.close();
            reader.close();

            // Replace old file with the updated temp file
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }

            System.out.println("Password changed successfully!");

        } catch (IOException e) {
            System.out.println("An error has occurred: " + e.getMessage());
        }
    }
}


