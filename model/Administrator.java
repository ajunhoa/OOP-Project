package model;

public class Administrator extends User{
    private String gender;
    private int age;

    public Administrator(String userID, String name, String gender, int age) {
        super(userID, name);
        this.gender = gender;
        this.age = age;
    }
}
