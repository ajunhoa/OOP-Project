package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataInitializer {

    public static Map<String, User> loadPatientDetails(String patientFilePath) {
        Map<String, User> userMap = new HashMap<>();

        try (BufferedReader patientReader = new BufferedReader(new FileReader(patientFilePath))) {
            String patientLine;
            patientReader.readLine();

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
                        int newUser = Integer.parseInt(patientValues[6].trim());
                        String password = patientValues[7].trim();
                        int contactNumber = Integer.parseInt(patientValues[8].trim());

                        Patient patient = new Patient(id, name, dateOfBirth, gender, bloodType, contactInfo, newUser, password, contactNumber);
                        userMap.put(id, patient);
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the patient file: " + e.getMessage());
        }

        return userMap;
    }
    public static Map<String, User> loadStaffDetails(String staffFilePath) {
        Map<String, User> userMap = new HashMap<>();

        try (BufferedReader staffReader = new BufferedReader(new FileReader(staffFilePath))) {
            String staffLine;
            staffReader.readLine();

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
                        int newUser = Integer.parseInt(staffValues[5].trim());
                        String password = staffValues[6].trim();

                        User staff = new Staff(id, name, role, gender, age, newUser, password);
                        userMap.put(id, staff);
                    } catch (NumberFormatException e) {
                    }   
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the staff file: " + e.getMessage());
        }

        return userMap;
    }

    public static Map<String, MedicalRecord> loadMedicalRecords(String medicalFilePath) {
        Map<String, MedicalRecord> medicalRecordMap = new HashMap<>();
    
        try (BufferedReader medicalReader = new BufferedReader(new FileReader(medicalFilePath))) {
            String line;
            medicalReader.readLine(); 
    
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
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the medical records file: " + e.getMessage());
        }
    
        return medicalRecordMap;
    }
    
    public static void linkMedicalRecordsToPatients(Map<String, User> userMap, Map<String, MedicalRecord> medicalRecordMap) {
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            User user = entry.getValue();
    
            if (user instanceof Patient) {
                Patient patient = (Patient) user;
    
                MedicalRecord medicalRecord = medicalRecordMap.get(patient.getId());
    
                if (medicalRecord != null) {
                    // System.out.println("Linking Medical Record to Patient ID: " + patient.getId());
                    patient.setMedicalRecord(medicalRecord); 
                } else {
                    // System.out.println("No Medical Record found for Patient ID: " + patient.getId());
                }
            }
        }
    }
    
    
}
