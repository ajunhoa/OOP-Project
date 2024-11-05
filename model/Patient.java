package model;

public class Patient extends User{
    

    // Constructor to initialize all attributes, including those from the User class
    public Patient(String userID, String name) {
        super(userID, name);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}


