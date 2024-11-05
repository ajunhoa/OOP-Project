package model;

public class Pharmacist extends User{
    private String gender;
    private int age;
    
    public Pharmacist(String userID, String name, String gender, int age) {
            super(userID, name);
            this.gender = gender;
            this.age = age;
        }
}
