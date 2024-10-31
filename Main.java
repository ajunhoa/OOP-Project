import model.Doctor;
import controller.DoctorController;
import view.DoctorView;

public class Main {
    public static void main(String[] args) {
        Doctor doctor = new Doctor("D001", "Dr. Smith", "DOC123");
        DoctorController doctorController = new DoctorController(doctor);
        DoctorView doctorView = new DoctorView(doctorController);

        doctorView.displayDoctorMenu();
    }
}
