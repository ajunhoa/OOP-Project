//package controllers;
//
//import models.User;
//import model.Administrator;
//import models.Doctor;
//import model.Pharmacist;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class StaffController {
//
//    /** List of all staff members (Administrators, Doctors, Pharmacists) in the system. */
//    private ArrayList<User> staff;
//
//    /**
//     * Constructor to initialize the staff list.
//     * @param initialStaffList The initial list of staff members loaded from the CSV.
//     */
//    public StaffController(ArrayList<User> initialStaffList) {
//        this.staff = initialStaffList;
//    }
//
//    /**
//     * Allows an administrator to update a staff member's information in the list.
//     * If a staff member with the same ID exists, it will be updated; otherwise, it will be added.
//     * @param updatedStaff The updated staff member information (Administrator, Doctor, or Pharmacist).
//     */
//    public void updateStaffList(User updatedStaff) {
//        for (int i = 0; i < staff.size(); i++) {
//            if (staff.get(i).getUserID().equals(updatedStaff.getUserID())) {
//                staff.set(i, updatedStaff); // Replace with updated staff
//                System.out.println("Staff member updated successfully.");
//                return;
//            }
//        }
//        // If not found, add new staff member
//        staff.add(updatedStaff);
//        System.out.println("Staff member added successfully.");
//    }
//
//    /**
//     * Returns the current list of staff members (Administrators, Doctors, Pharmacists).
//     * @return ArrayList of staff members.
//     */
//    public ArrayList<User> getStaffList() {
//        return staff;
//    }
//
//    /**
//     * Finds a staff member by their ID.
//     * @param staffId The ID of the staff member to search for.
//     * @return The User object if found, or null if not found.
//     */
//    public User findStaffById(String staffId) {
//        for (User s : staff) {
//            if (s.getUserID().equals(staffId)) {
//                return s;
//            }
//        }
//        System.out.println("Staff member not found.");
//        return null;
//    }
//}
//

package controller;

import model.Staff;

import java.util.ArrayList;
import java.util.List;

public class StaffController {
    private List<Staff> staffList;

    public StaffController() {
        this.staffList = new ArrayList<>();
    }

    public void addStaff(Staff staff) {
        staffList.add(staff);
        System.out.println(staff.getName() + " has been added.");
    }

    public void updateStaff(String id, String name, String role, String gender, int age) {
        for (Staff staff : staffList) {
            if (staff.getId().equals(id)) {
                staff.setName(name);
                staff.setRole(role);
                staff.setGender(gender);
                staff.setAge(age);
                System.out.println("Staff with ID: " + id + " has been updated.");
                return;
            }
        }
        System.out.println("Staff with ID: " + id + " not found.");
    }

    public void removeStaff(String id) {
        staffList.removeIf(staff -> staff.getId().equals(id));
        System.out.println("Staff with ID: " + id + " has been removed.");
    }

    public void displayStaffList() {
        for (Staff staff : staffList) {
            System.out.println(staff);
        }
    }

}
