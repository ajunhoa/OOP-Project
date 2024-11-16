package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The DataInitializer class is responsible for loading patient and staff details,
 * as well as medical records from CSV files into their respective data structures.
 * It also provides functionality to link medical records to patients.
 */
public class DataInitializer {

    /**
     * Loads patient details from a specified CSV file and returns a map of patients.
     *
     * @param patientFilePath The file path to the CSV file containing patient details.
     * @return A map where the key is the patient ID and the value is the corresponding User object representing the patient.
     */
    public static Map<String, User> loadPatientDetails(String patientFilePath) {
        Map<String, User> userMap = new HashMap<>();

        try (BufferedReader patientReader = new BufferedReader(new FileReader(patientFilePath))) {
            String patientLine;
            patientReader.readLine(); // Skip header line

            while ((patientLine = patientReader.readLine()) != null) {
                if (patientLine.trim().isEmpty()) continue;

                String[] patientValues = patientLine.split(",");
                if (patientValues.length == 9) {
                    try {
                        String id = patientValues[0].trim();
                        String name = patientValues[1].trim();
                        String dateOfBirth = patientValues[2].trim();
                        String gender = patientValues[3].trim();
                        String bloodType = patientValues[4].trim();
                        String contactInfo = patientValues[5].trim();
                        int newUser  = Integer.parseInt(patientValues[6].trim());
                        String password = patientValues[7].trim();
                        int contactNumber = Integer.parseInt(patientValues[8].trim());

                        Patient patient = new Patient(id, name, dateOfBirth, gender, bloodType, contactInfo, newUser , password, contactNumber);
                        userMap.put(id, patient);
                    } catch (NumberFormatException e) {
                        // Handle parsing error
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the patient file: " + e.getMessage());
        }

        return userMap;
    }

    /**
     * Loads staff details from a specified CSV file and returns a map of staff members.
     *
     * @param staffFilePath The file path to the CSV file containing staff details.
     * @return A map where the key is the staff ID and the value is the corresponding User object representing the staff member.
     */
    public static Map<String, User> loadStaffDetails(String staffFilePath) {
        Map<String, User> userMap = new HashMap<>();

        try (BufferedReader staffReader = new BufferedReader(new FileReader(staffFilePath))) {
            String staffLine;
            staffReader.readLine(); // Skip header line

            while ((staffLine = staffReader.readLine()) != null) {
                if (staffLine.trim().isEmpty()) continue;

                String[] staffValues = staffLine.split(",");
                if (staffValues.length == 7) {
                    try {
                        String id = staffValues[0].trim();
                        String name = staffValues[1].trim();
                        String role = staffValues[2].trim();
                        String gender = staffValues[3].trim();
                        int age = Integer.parseInt(staffValues[4].trim());
                        int newUser  = Integer.parseInt(staffValues[5].trim());
                        String password = staffValues[6].trim();

                        User staff = new Staff(id, name, role, gender, age, newUser , password);
                        userMap.put(id, staff);
                    } catch (NumberFormatException e) {
                        // Handle parsing error
                    }   
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the staff file: " + e.getMessage());
        }

        return userMap;
    }

    /**
     * Loads medical records from a specified CSV file and returns a map of medical records.
     *
     * @param medicalFilePath The file path to the CSV file containing medical records.
     * @return A map where the key is the patient ID and the value is the corresponding MedicalRecord object.
     */
    public static Map<String, MedicalRecord> loadMedicalRecords(String medicalFilePath) {
        Map<String, MedicalRecord> medicalRecordMap = new HashMap<>();
    
        try (BufferedReader medicalReader = new BufferedReader(new FileReader(medicalFilePath))) {
            String line;
            medicalReader.readLine(); // Skip header line
    
            // Load medical record data
            while ((line = medicalReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
    
                String[] values = line.split(",");
                if (values.length == 3) { 
                    try {
                        String patientId = values[0].trim(); 
                        String pastDiagnoses = values[1].trim(); 
                        String treatment = values[2].trim(); 
    
                        MedicalRecord medicalRecord = new MedicalRecord(patientId, pastDiagnoses, treatment);
                        medicalRecordMap.put(patientId, medicalRecord);
                    } catch (Exception e) {
                        // Handle parsing error
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the medical records file: " + e.getMessage());
        }
    
        return medicalRecordMap;
    }
    
    /**
     * Links medical records to their corresponding patients in the user map.
     *
     * @param userMap A map of users where the key is the user ID and the value is the User object.
     * @param medicalRecordMap A map of medical records where the key is the patient ID and the value is the MedicalRecord object.
     */
    public static void linkMedicalRecordsToPatients(Map<String, User> userMap, Map<String, MedicalRecord> medicalRecordMap) {
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            User user = entry.getValue();
    
            if (user instanceof Patient) {
                Patient patient = (Patient) user;
    
                MedicalRecord medicalRecord = medicalRecordMap.get(patient.getId());
    
                if (medicalRecord != null) {
                    patient.setMedicalRecord(medicalRecord); 
                }
            }
        }
    }
}