package model;
public class Staff extends User {

    public Staff(String id, String name, String role, String gender, int age, int newUser, String password) {
        super(id, name, role, gender, age, "", "", "", password, newUser, 0);
    }

    @Override
    public String toString() {
        return "Staff ID: " + getId() + ", Name: " + getName() + ", Role: " + getRole() + 
               ", Gender: " + getGender() + ", Age: " + getAge() + ", New User: " + (isNewUser() ? "Yes" : "No");
    }
}
