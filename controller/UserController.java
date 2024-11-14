package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.User;

public class UserController {

    public static void promptPasswordChange(User user, String filePath, Scanner scanner) {
        if (user.isNewUser()) {
            System.out.println("Please change your password for first-time login");

            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine(); // Use Scanner instead of BufferedReader

            user.changePassword(newPassword);
            user.setNewUser(0);

            boolean success = updateCSVFile(filePath, user);

            if (success) {
                System.out.println("Password changed successfully.");
            } else {
                System.out.println("Failed to update the password in the CSV file.");
            }
        }
    }

    public static boolean updateCSVFile(String filePath, User user) {
        List<String> lines = new ArrayList<>();
        String userId = user.getId();
        String newPassword = user.getPassword();
        int newUserFlag = user.isNewUser() ? 1 : 0;
        boolean isUpdated = false;
    
        System.out.println("Debug: Attempting to update user with ID: " + userId);
    
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
    
                System.out.println("Debug: Processing line: " + line);
    
                // Check for correct number of columns in both Staff and Patient CSV files
                if (values.length >= 7 && values[0].trim().equalsIgnoreCase(userId)) {
                    System.out.println("Debug: Found matching user ID: " + userId);
    
                    // Update the columns for newUser and password
                    if (filePath.contains("staff")) {
                        // For staff: newUser is at index 5, password at index 6
                        values[5] = String.valueOf(newUserFlag);  // Update newUser
                        values[6] = newPassword;  // Update password
                    } else if (filePath.contains("patient")) {
                        // For patient: newUser is at index 6, password at index 7
                        values[6] = String.valueOf(newUserFlag);  // Update newUser
                        values[7] = newPassword;  // Update password
                    }
    
                    // Join the values back into a CSV format line
                    line = String.join(",", values);
                    isUpdated = true;
                    System.out.println("Debug: Updated line: " + line);
                }
    
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return false;
        }
    
        // Write all lines back to the CSV file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            System.out.println("Debug: Successfully wrote to the CSV file.");
            return isUpdated;
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
            return false;
        }
    }
    
}
