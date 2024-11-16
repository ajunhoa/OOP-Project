import controller.MedicalRecordController;
import controller.UserController;
import java.util.Map;
import java.util.Scanner;
import model.DataInitializer;
import model.Doctor;
import model.MedicalRecord;
import model.Patient;
import model.User;
import view.AdministratorView;
import view.DoctorView;
import view.PatientView;
import view.PharmacistView;    

public class Main {
    public static void main(String[] args) {
        String patientFilePath = "assets/updatedpatientlist.csv";
        String staffFilePath = "assets/updatedstafflist.csv";
        String medicalFilePath = "assets/medicalrecords.csv";

        Map<String, User> userMap = DataInitializer.loadPatientDetails(patientFilePath);
        userMap.putAll(DataInitializer.loadStaffDetails(staffFilePath)); 

        Map<String, MedicalRecord> medicalRecordMap = DataInitializer.loadMedicalRecords(medicalFilePath);
        DataInitializer.linkMedicalRecordsToPatients(userMap, medicalRecordMap);

        MedicalRecordController medicalRecordController = new MedicalRecordController(medicalRecordMap, userMap, medicalFilePath, patientFilePath);

        // for (Map.Entry<String, User> entry : userMap.entrySet()) {
        //     if (entry.getValue() instanceof Patient) {
        //         Patient patient = (Patient) entry.getValue();
        //         if (patient.getMedicalRecord() != null) {
        //             System.out.println("Patient: " + patient.getName() + " has a medical record linked.");
        //         } else {
        //             System.out.println("Patient: " + patient.getName() + " has no medical record linked.");
        //         }
        //     }
        // }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the Hospital Management System");
            System.out.print("Enter your User ID: ");
            String userId = scanner.nextLine().trim().toUpperCase();

            System.out.print("Enter your Password: ");
            String password = scanner.nextLine().trim();

            User user = userMap.get(userId);

            if (user != null && user.validatePassword(password)) {
                System.out.println("Welcome, " + user.getName());

                String filePath = user.getRole().equals("Patient") ? patientFilePath : staffFilePath;
                UserController.promptPasswordChange(user, filePath, scanner);

                switch (user.getRole()) {
                    case "Doctor":
                        Doctor doctor = new Doctor(user.getId(), user.getName(), user.getRole());
                        DoctorView doctorView = new DoctorView(doctor, medicalRecordController);
                        doctorView.showMenu(userId);
                        break;

                    case "Pharmacist":
                        PharmacistView pharmacistView = new PharmacistView(scanner);
                        pharmacistView.showMenu();
                        break;

                    case "Administrator":
                        AdministratorView administratorView = new AdministratorView(scanner);
                        administratorView.showMenu(); 
                        break;
                    case "Patient":
                        Patient patient = (Patient) user;
                        PatientView patientView = new PatientView(patient, scanner, medicalRecordController);
                        patientView.showMenu();
                        break;

                    default:
                        System.out.println("Role not recognized.");
                }
            } else {
                System.out.println("Invalid User ID or Password.");
            }
        }
    }
}
