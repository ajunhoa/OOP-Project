package model;


public class Doctor extends User {
    private String doctorID;
    private String role;

    public Doctor(String userID, String name, String role) {
        super(userID, name);
        this.doctorID = userID;
        this.role = role;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getRole() {
        return role;
    }
}
