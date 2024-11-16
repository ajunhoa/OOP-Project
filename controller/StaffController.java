package controller;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import model.Staff;



/**
 * The StaffController class manages the staff records in the hospital management system.
 * It provides functionalities to load staff details, add, update, remove, and filter staff,
 * as well as displaying staff information.
 */
public class StaffController {
    private Scanner scanner;

    /** The file path for the staff records CSV file. */
    private final String staffFilePath = "assets/updatedstafflist.csv";
    
    /** A map that stores doctor IDs and their corresponding names. */
    private Map<String, String> doctorMap;

    /**
     * Constructs a StaffController and initializes the doctor map by loading staff details.
     */
    public StaffController() {
        doctorMap = new HashMap<>();
        loadStaffDetails();
        scanner = new Scanner(System.in);
    }

    /**
     * Loads staff details from the specified CSV file and populates the doctorMap.
     */
    private void loadStaffDetails() {
        try (BufferedReader br = new BufferedReader(new FileReader(staffFilePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 7 && values[2].equalsIgnoreCase("Doctor")) {
                    String doctorID = values[0].trim();
                    String doctorName = values[1].trim();
                    doctorMap.put(doctorID, doctorName); 
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the staff file: " + e.getMessage());
        }
    }

    /**
     * Retrieves the name of the doctor associated with the specified doctor ID.
     *
     * @param doctorID The ID of the doctor whose name is to be retrieved.
     * @return The name of the doctor, or "Unknown Doctor" if not found.
     */
    public String getDoctorNameByID(String doctorID) {
        return doctorMap.getOrDefault(doctorID, "Unknown Doctor");
    }
    
    /**
     * Adds a new staff member to the records.
     *
     * @param newStaff The Staff object representing the new staff member.
     * @return true if the staff member was added successfully, false otherwise.
     */
    public boolean addStaff(Staff newStaff) {
        if (isStaffIdExists(newStaff.getId())) {
            System.out.println("Staff ID already exists. Please use a different ID.");
            return false; 
        }
    
        if (!isValidRole(newStaff.getRole())) {
            System.out.println("Invalid role. Please use 'Doctor' or 'Pharmacist'.");
            return false; 
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(staffFilePath, true))) {
            writer.write(newStaff.getId() + "," + newStaff.getName() + "," + newStaff.getRole() + "," +
                         newStaff.getGender() + "," + newStaff.getAge() + "," + (newStaff.isNewUser () ? 1 : 0) + "," +
                         newStaff.getPassword());
            writer.newLine();
            return true; 
        } catch (IOException e) {
            System.out.println("Error writing to the staff file: " + e.getMessage());
            return false;
        }
    }
   
    /**
     * Validates if the given role is either "Doctor" or "Pharmacist".
     *
     * @param role The role to validate.
     * @return true if the role is valid, false otherwise.
     */
    private boolean isValidRole(String role) {
        return role.equalsIgnoreCase("Doctor") || role.equalsIgnoreCase("Pharmacist");
    }
    
    /**
     * Checks if a staff ID already exists in the records.
     *
     * @param staffId The staff ID to check.
     * @return true if the staff ID exists, false otherwise.
     */
    public boolean isStaffIdExists(String staffId) {
        try (BufferedReader br = new BufferedReader(new FileReader(staffFilePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 1 && values[0].trim().equalsIgnoreCase(staffId)) {
                    return true; 
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the staff file: " + e.getMessage());
        }
        return false;
    }

    /**
     * Updates the information of an existing staff member.
     *
     * @param updatedStaff The Staff object containing the updated information.
     * @return true if the staff member was updated successfully, false otherwise.
     */
    public boolean updateStaff(Staff updatedStaff) {
        List<String> lines = new ArrayList<>();
        boolean isUpdated = false;
    
        if (!updatedStaff.getRole().equalsIgnoreCase("Doctor") && 
            !updatedStaff.getRole().equalsIgnoreCase("Pharmacist")) {
            System.out.println("Invalid role. Only 'Doctor' or 'Pharmacist' can be updated.");
            return false;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(staffFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 7 && values[0].equalsIgnoreCase(updatedStaff.getId())) {
                    // Check if the current role is Administrator
                    if (values[2].equalsIgnoreCase("Administrator")) {
                        System.out.println("Cannot update Administrator role.");
                        return false; 
                    }
                    line = updatedStaff.getId() + "," + updatedStaff.getName() + "," + updatedStaff.getRole() + "," +
                           updatedStaff.getGender() + "," + updatedStaff.getAge() + "," + (updatedStaff.isNewUser () ? 1 : 0) + "," +
                           updatedStaff.getPassword();
                    isUpdated = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the staff file: " + e.getMessage());
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(staffFilePath))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            return isUpdated;
        } catch (IOException e) {
            System.out.println("Error writing to the staff file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Removes a staff member from the records.
     *
     * @param staffId The ID of the staff member to remove.
     * @return true if the staff member was removed successfully, false otherwise.
     */
    public boolean removeStaff(String staffId) {
        List<String> lines = new ArrayList<>();
        boolean isRemoved = false;
    
        try (BufferedReader br = new BufferedReader(new FileReader(staffFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 1) {
                    if (values[0].equalsIgnoreCase(staffId)) {
                        if (values[2].equalsIgnoreCase("Administrator")) {
                            System.out.println("Cannot remove Administrator role.");
                            return false;
                        }
                        isRemoved = true;
                        continue;
                    }
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the staff file: " + e.getMessage());
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(staffFilePath))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            return isRemoved; 
        } catch (IOException e) {
            System.out.println("Error writing to the staff file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Filters staff members based on the specified criteria.
     *
     * @param role The role to filter by (can be null).
     * @param gender The gender to filter by (can be null).
     * @param age The age to filter by (can be null).
     * @return A list of staff members that match the specified criteria.
     */
    public List<Staff> filterStaff(String role, String gender, Integer age) {
        List<Staff> staffList = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(staffFilePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 7) {
                    Staff staff = new Staff(values[0].trim(), values[1].trim(), values[2].trim(),
                                            values[3].trim(), Integer.parseInt(values[4].trim()),
                                            Integer.parseInt(values[5].trim()), values[6].trim());
                    staffList.add(staff);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the staff file: " + e.getMessage());
        }
    
        staffList = staffList.stream()
                .filter(staff -> (role == null || staff.getRole().equalsIgnoreCase(role)) &&
                                 (gender == null || staff.getGender().equalsIgnoreCase(gender)) &&
                                 (age == null || staff.getAge() == age))
                .collect(Collectors.toList());
    
        Collections.sort(staffList, Comparator.comparing(Staff::getName));
    
        return staffList;
    }

    /**
     * Displays the staff members based on the specified filtering criteria.
     *
     * @param role The role to filter by (can be null).
     * @param gender The gender to filter by (can be null).
     * @param age The age to filter by (can be null).
     */
    public void displayStaff(String role, String gender, Integer age) {
        List<Staff> staffList = filterStaff(role, gender, age); 
    
        if (staffList.isEmpty()) {
            System.out.println("No staff members found.");
        } else {
            System.out.println("\n=== Staff List ===");
            for (Staff staff : staffList) {
                System.out.println(staff);
            }
        }
    }
    
    /**
     * Prompts the user to enter details for a new staff member and adds them to the system.
     */
    public void addStaff() {
        System.out.print("Enter Staff ID: ");
        String id = scanner.nextLine().toUpperCase();
        System.out.print("Enter Staff Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Role (Doctor/Pharmacist): ");
        String role = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        Staff newStaff = new Staff(id, name, role, gender, age, 1 , password); 
        if (addStaff(newStaff)) {
            System.out.println("Staff added successfully.");
        } else {
            System.out.println("Failed to add staff .");
        }
    }

    /**
     * Prompts the user to enter new details for an existing staff member and updates their information.
     */
    public void updateStaff() {
        System.out.print("Enter Staff ID to update: ");
        String id = scanner.nextLine();
        System.out.print("Enter New Staff Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Role (Doctor/Pharmacist): ");
        String role = scanner.nextLine();
        System.out.print("Enter New Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter New Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter New Password: ");
        String password = scanner.nextLine();

        Staff updatedStaff = new Staff(id.toUpperCase(), name, role, gender, age, 1, password);
        if (updateStaff(updatedStaff)) {
            System.out.println("Staff updated successfully.");
        } else {
            System.out.println("Failed to update staff.");
        }
    }

    
    /**
     * Prompts the user to enter a staff ID and removes the corresponding staff member from the system.
     */
    public void removeStaff() {
        System.out.print("Enter Staff ID to remove: ");
        String id = scanner.nextLine();
        if (removeStaff(id)) {
            System.out.println("Staff removed successfully.");
        } else {
            System.out.println("Failed to remove staff.");
        }
    }

    /**
     * Prompts the user to enter filters for displaying staff and calls the controller to display the filtered staff list.
     */
    public void displayStaff() {
        String role = null;
        String gender = null;
        Integer age = null;
    
        while (true) {
            System.out.print("Enter Role to filter, Doctor / Pharmacist (or press Enter to skip): ");
            role = scanner.nextLine().trim();
            if (role.isEmpty()) {
                role = null;
                break;
            } else if (role.equalsIgnoreCase("Doctor") || role.equalsIgnoreCase("Pharmacist")) {
                break;
            } else {
                System.out.println("Invalid role. Please enter 'Doctor', 'Pharmacist', or press Enter to skip.");
            }
        }
    
        while (true) {
            System.out.print("Enter Gender to filter, Male / Female (or press Enter to skip): ");
            gender = scanner.nextLine().trim();
            if (gender.isEmpty()) {
                gender = null;
                break;
            } else if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                break;
            } else {
                System.out.println("Invalid gender. Please enter 'Male', 'Female', or press Enter to skip.");
            }
        }

        while (true) {
            System.out.print("Enter Age to filter (or press Enter to skip): ");
            String ageInput = scanner.nextLine().trim();
            if (ageInput.isEmpty()) {
                age = null; 
                break; 
            } else {
                try {
                    age = Integer.parseInt(ageInput);
                    break; 
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age. Please enter a valid number or press Enter to skip.");
                }
            }
        }
    
        // Call the controller to display staff with the provided filters
        displayStaff(role, gender, age);
    }

}