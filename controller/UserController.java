package controller;

import model.User;
import java.io.*;

public class UserController {

    public static void promptPasswordChange(User user, String filePath) {
        if (user.isNewUser()) {
            System.out.println("Please change your password for first time log in");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                System.out.print("Enter new password: ");
                String newPassword = reader.readLine();

                user.changePassword(newPassword);
                user.setNewUser( 0);

                updateCSVFile(filePath, user);

                System.out.println("Password changed successfully.");
            } catch (IOException e) {
                System.out.println("Error reading input for new password.");
            }
        }
    }

    private static void updateCSVFile(String filePath, User updatedUser) {
        File file = new File(filePath);
        StringBuilder newData = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            newData.append(line).append("\n");

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(updatedUser.getId())) {
                    values[8] = updatedUser.getPassword(); //  Password is in the 9th column
                    values[9] = updatedUser.isNewUser() ? "1" : "0"; // New User is in the 10th column
                    newData.append(String.join(",", values)).append("\n");
                } else {
                    newData.append(line).append("\n");
                }
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(newData.toString());
            }

        } catch (IOException e) {
            System.out.println("Error updating the CSV file: " + e.getMessage());
        }
    }
}
