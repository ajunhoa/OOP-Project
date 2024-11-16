package controller;
import model.Staff;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StaffController {

    private final String staffFilePath = "assets/updatedstafflist.csv";
    private Map<String, String> doctorMap;

    public StaffController() {
        doctorMap = new HashMap<>();
        loadStaffDetails();
    }

    private void loadStaffDetails() {
        try (BufferedReader br = new BufferedReader(new FileReader(staffFilePath))) {
            String line;
            br.readLine(); 
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

    public String getDoctorNameByID(String doctorID) {
        return doctorMap.getOrDefault(doctorID, "Unknown Doctor");
    }
    
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
   
    private boolean isValidRole(String role) {
        return role.equalsIgnoreCase("Doctor") || role.equalsIgnoreCase("Pharmacist");
    }
    
    private boolean isStaffIdExists(String staffId) {
        try (BufferedReader br = new BufferedReader(new FileReader(staffFilePath))) {
            String line;
            br.readLine();
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

    public boolean updateStaff(Staff updatedStaff) {
        List<String> lines = new ArrayList<>();
        boolean isUpdated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(staffFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 7 && values[0].equalsIgnoreCase(updatedStaff.getId())) {
                    line = updatedStaff.getId() + "," + updatedStaff.getName() + "," + updatedStaff.getRole() + "," +
                           updatedStaff.getGender() + "," + updatedStaff.getAge() + "," + updatedStaff.isNewUser () + "," +
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

    public boolean removeStaff(String staffId) {
        List<String> lines = new ArrayList<>();
        boolean isRemoved = false;

        try (BufferedReader br = new BufferedReader(new FileReader(staffFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 1 && !values[0].equalsIgnoreCase(staffId)) {
                    lines.add(line);
                } else {
                    isRemoved = true;
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


    public List<Staff> filterStaff(String role, String gender, Integer age) {
        List<Staff> staffList = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(staffFilePath))) {
            String line;
            br.readLine();
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
}