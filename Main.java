public class Main {
    public static void main(String[] args) {
        // Creating a sample patient
        Patient patient = new Patient("P001", "John Doe", "PID123", "O+");
        
        // Creating a sample doctor
        Doctor doctor = new Doctor("D001", "Dr. Smith", "DID456");
        
        // Simulating an appointment booking
        patient.scheduleAppointment(doctor, "2024-10-16", "10:00 AM");
        
        // Displaying doctor and patient menus
        doctor.displayMenu();
        patient.displayMenu();
    }
}
