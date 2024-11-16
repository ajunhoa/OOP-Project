# Hospital Management System

## Overview
The Hospital Management System is a Java-based application designed to manage various aspects of a hospital's operations. It provides functionalities for managing patient records, staff information, appointment scheduling, and more. The system aims to streamline the processes within a hospital, ensuring efficient management of resources and patient care.

## Features
- **User  Management**: Supports different user roles including patients, doctors, pharmacists, and administrators.
- **Patient Records**: Allows for the creation, retrieval, and update of patient medical records.
- **Appointment Scheduling**: Enables users to schedule, reschedule, and cancel appointments.
- **Medical Records Management**: Links medical records to patients and allows for viewing and updating of records.
- **Role-Based Access**: Different views and functionalities based on user roles to ensure data security and integrity.

## Technologies Used
- **Java**: The primary programming language used for development.
- **Javadoc**: For generating documentation from the source code.
- **CSV Files**: For data storage and retrieval of patient and staff information.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/ajunhoa/OOP-Project.git

2. Navigate to the project directory:
```bash
cd OOP-Project
```
3. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).

## Usage
1. Run the Main class to start the application.
2. Follow the prompts to log in using your User ID and Password.
3. Navigate through the menus based on your role to access various functionalities.

## Project Structure
```
OOP-Project/
│
├── model/
│   ├── AppointmentOutcomeRecord.java
│   ├── AppointmentSlot.java
│   ├── Doctor.java
│   ├── MedicalRecord.java
│   ├── Medicine.java
│   ├── Patient.java
│   ├── Staff.java
│   └── User.java
│
├── controller/
│   ├── MedicalRecordController.java
│   └── UserController.java
│
├── view/
│   ├── AdministratorView.java
│   ├── DoctorView.java
│   ├── PatientView.java
│   └── PharmacistView.java
│
├── Javadocs/
│   └── (generated documentation)
│
├── assets/
│   ├── updatedpatientlist.csv
│   ├── updatedstafflist.csv
│   └── medicalrecords.csv
│
└── Main.java
```

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgements
Thanks to all contributors and libraries that helped in building this project.
Special thanks to the educational resources and documentation from Oracle for Java.
