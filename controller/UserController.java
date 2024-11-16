package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.User;

/**
 * The UserController class provides functionalities for managing user accounts,
 * including prompting for password changes and updating user information in CSV files.
 */
public class UserController {

    /**
     * Prompts the user to change their password if they are a new user.
     *
     * @param user The User object representing the user whose password is to be changed.
     * @param filePath The file path of the CSV file where user information is stored.
     * @param scanner A Scanner object for reading user input.
     */
    public static void promptPasswordChange(User user, String filePath, Scanner scanner) {
        if (user.isNewUser ()) {
            System.out.println("Please change your password for first-time login");

            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine(); 

            user.changePassword(newPassword);
            user.setNewUser (0);

            boolean success = updateCSVFile(filePath, user);

            if (success) {
                System.out.println("Password changed successfully.");
            } else {
                System.out.println("Failed to update the password in the CSV file.");
            }
        }
    }

    /**
     * Updates the user information in the specified CSV file.
     *
     * @param filePath The file path of the CSV file to be updated.
     * @param user The User object containing the updated information.
     * @return true if the update was successful, false otherwise.
     */
    public static boolean updateCSVFile(String filePath, User user) {
        List<String> lines = new ArrayList<>();
        String userId = user.getId();
        String newPassword = user.getPassword();
        int newUserFlag = user.isNewUser () ? 1 : 0;
        boolean isUpdated = false;
    
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 7 && values[0].trim().equalsIgnoreCase(userId)) {
                    if (filePath.contains("staff")) {
                        values[5] = String.valueOf(newUserFlag);  
                        values[6] = newPassword;  
                    } else if (filePath.contains("patient")) {
                        values[6] = String.valueOf(newUserFlag);  
                        values[7] = newPassword; 
                    }
    
                    line = String.join(",", values);
                    isUpdated = true;
                }
    
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return false;
        }
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            return isUpdated;
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
            return false;
        }
    }
}