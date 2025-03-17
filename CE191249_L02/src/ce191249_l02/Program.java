/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce191249_l02;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * L02 - Title: Create a Java console program to manage students.
 *
 * @author Le Thien Tri - CE191249 - Date: 16/03/2025 Program class handles the
 * student management system functionality. It provides features for creating,
 * updating, deleting, and searching student records. Each student can register
 * for up to 3 courses per semester.
 */
public class Program {

    // Student information fields
    private String studentName;        // Current student's name
    private String studentCourse;      // Current student's course
    private String studentSemester;    // Current student's semester
    private String stepOneInputID;     // Temporary storage for ID input
    private String stepThreeInputSemester; // Temporary storage for semester input
    private String studentID;          // Current student's ID

    // Control flow variables
    private boolean miniLoop = true;   // Controls inner menu loops
    private boolean loop = true;       // Controls main program loop
    private String choice;             // Stores user menu choices
    private String input;              // Stores general user input
    private boolean useOption = true;  // Controls which menu to display

    // System objects
    private Scanner sc = new Scanner(System.in);  // Input scanner
    private StudentInfo student;                  // Temporary student object
    private ArrayList<StudentInfo> studentStorage = new ArrayList<>();  // Main storage for student records

    /**
     * Displays the initial welcome menu with main options
     */
    public void firstMenu() {
        System.out.println("        WELCOME TO STUDENT MANAGEMENT");
        System.out.println("1. Create");
        System.out.println("2. Find and Sort");
        System.out.println("3. Update/Delete");
        System.out.println("4. Report");
        System.out.println("5. Exit");
    }

    /**
     * Displays the subsequent menu after first use
     */
    public void secondMenu() {
        System.out.println("        STUDENT MANAGEMENT OPTION");
        System.out.println("1. Create");
        System.out.println("2. Find and Sort");
        System.out.println("3. Update/Delete");
        System.out.println("4. Report");
        System.out.println("5. Exit");
    }

    /**
     * Main program loop that handles menu navigation and user choices
     */
    public void run() {
        // Display appropriate menu based on first use
        if (useOption == true) {
            firstMenu();
        }
        while (loop) {
            if (useOption == false) {
                secondMenu();
            }
            useOption = false;

            // Get and validate user choice
            System.out.print("        Please choosen:");
            choice = sc.nextLine().trim();
            if (choice.isEmpty()) {
                System.out.println("Choice can't be empty.");
                continue;
            }
            // Validate input format
            if (checkInput(choice, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue;
            }
            if (checkInput(choice, "Can't be enter space.", "[0-9a-zA-Z]+")) {
                continue;
            }
            if (checkInput(choice, "Can't be enter character.", "[0-9 ]+")) {
                continue;
            }
            if (checkInput(choice, "Please enter again (1-5).", "[1-5]")) {
                continue;
            }

            // Process user choice
            switch (choice) {
                case "1":
                    createStudentInfo();
                    break;
                case "2":
                    findStudent();
                    break;
                case "3":
                    updateDelete();
                    break;
                case "4":
                    report();
                    break;
                case "5":
                    loop = false;
                    break;
            }
        }
    }

    /**
     * Creates a new student record or adds a course registration for existing
     * student
     */
    public void createStudentInfo() {
        inputStudentID();
        if (studentName == null) {  // Only input name for new student IDs
            inputStudentName();
        }
        inputStudentSemester();
        inputStudentCourse();
        studentStorage.add(new StudentInfo(studentID, studentName, studentSemester, studentCourse));
    }

    /**
     * Handles student name input with validation - Name must contain only
     * letters and spaces - Cannot be empty
     */
    public void inputStudentName() {
        miniLoop = true;
        while (miniLoop) {
            System.out.print("Enter Name: ");
            input = sc.nextLine().trim();
            // Validate input
            if (input.isEmpty()) {
                System.out.println("Choice can't be empty.");
                continue;
            }
            if (checkInput(input, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue;
            }
            if (checkInput(input, "Can't be enter number.", "[a-zA-Z ]+")) {
                continue;
            }
            studentName = input;
            miniLoop = false;
        }
    }

    /**
     * Handles student ID input with validation - ID must follow format CExxxxxx
     * where x is a digit - Checks for existing IDs and retrieves student name
     * if found
     */
    public void inputStudentID() {
        // Special handling for first student
        if (studentStorage.isEmpty()) {
            miniLoop = true;
            while (miniLoop) {
                System.out.print("Enter ID: ");
                input = sc.nextLine().trim().toUpperCase();
                stepOneInputID = input;
                // Validate input format
                if (input.isEmpty()) {
                    System.out.println("Choice can't be empty.");
                    continue;
                }
                if (checkInput(input, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                    continue;
                }
                if (checkInput(input, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                    continue;
                }
                if (checkInput(input, "Student ID example form (CExxxxxx), Please enter again.", "^(CE)[0-9]{6,6}")) {
                    continue;
                }
                studentID = input;
                studentName = null;  // Reset studentName for new ID
                miniLoop = false;
            }
            return;
        }

        // Normal ID input process
        miniLoop = true;
        while (miniLoop) {
            System.out.print("Enter ID: ");
            input = sc.nextLine().trim().toUpperCase();
            stepOneInputID = input;
            // Validate input format
            if (input.isEmpty()) {
                System.out.println("Choice can't be empty.");
                continue;
            }
            if (checkInput(input, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue;
            }
            if (checkInput(input, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                continue;
            }
            if (checkInput(input, "Student ID example form (CExxxxxx), Please enter again.", "^(CE)[0-9]{6,6}")) {
                continue;
            }

            // Check for existing ID
            boolean idExists = false;
            for (StudentInfo studentInfo : studentStorage) {
                if (studentInfo.getStudentID().equals(input)) {
                    System.out.println("This ID existed so skip step enter name:");
                    studentID = input;
                    studentName = studentInfo.getStudentName();
                    idExists = true;
                    break;
                }
            }

            if (!idExists) {
                studentID = input;
                studentName = null;  // Reset studentName for new ID
            }
            miniLoop = false;
        }
    }

    /**
     * Handles semester input with validation - Semester must follow format
     * SPxx, SUxx, or FAxx where x is a digit - Checks if student has already
     * registered for 3 courses in the semester
     */
    public void inputStudentSemester() {
        // Special handling for first student
        if (studentStorage.isEmpty()) {
            miniLoop = true;
            while (miniLoop) {
                System.out.print("Enter semester: ");
                input = sc.nextLine().trim().toUpperCase();
                stepThreeInputSemester = input;
                // Validate input format
                if (input.isEmpty()) {
                    System.out.println("Choice can't be empty.");
                    continue;
                }
                if (checkInput(input, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                    continue;
                }
                if (checkInput(input, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                    continue;
                }
                if (checkInput(input, "Student semester example form (SPxx|SUxx|FAxx), Please enter again.", "^(SP)[0-9]{2,2}|^(SU)[0-9]{2,2}|^(FA)[0-9]{2,2}")) {
                    continue;
                }

                studentSemester = input;
                miniLoop = false;
                return;
            }
        }

        // Normal semester input process
        miniLoop = true;
        while (miniLoop) {
            System.out.print("Enter semester: ");
            input = sc.nextLine().trim().toUpperCase();
            stepThreeInputSemester = input;
            // Validate input format
            if (input.isEmpty()) {
                System.out.println("Choice can't be empty.");
                continue;
            }
            if (checkInput(input, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue;
            }
            if (checkInput(input, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                continue;
            }
            if (checkInput(input, "Student semester example form (SPxx|SUxx|FAxx), Please enter again.", "^(SP)[0-9]{2,2}|^(SU)[0-9]{2,2}|^(FA)[0-9]{2,2}")) {
                continue;
            }

            // Check number of courses student has in this semester
            Set<String> studentCourses = new HashSet<>();
            for (StudentInfo studentInfo : studentStorage) {
                if (studentInfo.getStudentID().equals(stepOneInputID) && studentInfo.getStudentSemester().equals(input)) {
                    studentCourses.add(studentInfo.getStudentCourse());
                }
            }
            // Prevent registration if student already has 3 courses
            if (studentCourses.size() >= 3) {
                System.out.println("You have already registered all 3 courses in this semester. Please choose another semester.");
                continue;
            }
            studentSemester = input;
            miniLoop = false;
        }
    }

    /**
     * Handles course input with validation - Course must be either Java, .Net,
     * or C/C++ - Checks if student has already registered for this course in
     * the semester
     */
    public void inputStudentCourse() {
        // Special handling for first student
        if (studentStorage.isEmpty()) {
            miniLoop = true;
            while (miniLoop) {
                System.out.print("Enter course: ");
                input = sc.nextLine().trim().toUpperCase();
                // Validate input format
                if (input.isEmpty()) {
                    System.out.println("Choice can't be empty.");
                    continue;
                }
                if (checkInput(input, "Can't be enter special character except ., /, +.", "[a-zA-Z0-9./+ ]+")) {
                    continue;
                }
                if (checkInput(input, "Can't be enter number.", "[a-zA-Z./+ ]+")) {
                    continue;
                }
                if (checkInput(input, "Student Course can be enter only three form (JAVA|.NET|C/C++), Please enter again.", "^(JAVA)|^([.])(NET)$|^(C[/])(C)([+]{2,2})$")) {
                    continue;
                }

                studentCourse = input;
                miniLoop = false;
                return;
            }
        }

        // Normal course input process
        miniLoop = true;
        while (miniLoop) {
            System.out.print("Enter course: ");
            input = sc.nextLine().trim().toUpperCase();
            // Validate input format
            if (input.isEmpty()) {
                System.out.println("Choice can't be empty.");
                continue;
            }
            if (checkInput(input, "Can't be enter special character except ., /, +.", "[a-zA-Z0-9./+ ]+")) {
                continue;
            }
            if (checkInput(input, "Can't be enter number.", "[a-zA-Z./+ ]+")) {
                continue;
            }
            if (checkInput(input, "Student Course can be enter only three form (JAVA|.NET|C/C++), Please enter again.", "^(JAVA)|^([.])(NET)$|^(C[/])(C)([+]{2,2})$")) {
                continue;
            }

            // Check if student already registered for this course in the semester
            boolean alreadyRegistered = false;
            for (StudentInfo studentInfo : studentStorage) {
                if (studentInfo.getStudentID().equals(stepOneInputID) && studentInfo.getStudentSemester().equals(stepThreeInputSemester) && studentInfo.getStudentCourse().equals(input)) {
                    System.out.println("Error, this course already exist. Please enter again.");
                    alreadyRegistered = true;
                    break;
                }
            }

            if (alreadyRegistered) {
                continue;
            }

            studentCourse = input;
            miniLoop = false;
        }
    }

    /**
     * Generates a formatted report of all student records Displays in table
     * format with columns for ID, Name, Semester, and Course
     */
    public void report() {
        if (studentStorage.isEmpty()) {
            System.out.println("Have no student to report");
            return;
        }
        // Print report header
        System.out.println("                       REPORT TABLE                        ");
        System.out.println("+-----+------------+------------------+----------+--------+");
        System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
        System.out.println("+-----+------------+------------------+----------+--------+");
        // Print each record
        for (int i = 0; i < studentStorage.size(); i++) {
            System.out.printf("|%5d", i + 1);
            System.out.println(studentStorage.get(i).getReport());
        }
        System.out.println("+-----+------------+------------------+----------+--------+");
    }

    /**
     * Main search functionality that provides different search options - Search
     * by ID - Search by Name - Search by Course - Search by Semester
     */
    public void findStudent() {
        if (studentStorage.isEmpty()) {
            System.out.println("No student data available.");
            return;
        }

        miniLoop = true;
        while (miniLoop) {
            // Display search menu
            System.out.println("\n----- Search Menu -----");
            System.out.println("1. Search by ID");
            System.out.println("2. Search by Name");
            System.out.println("3. Search by Course");
            System.out.println("4. Search by Semester");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            // Get and validate user choice
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Choice can't be empty.");
                continue;
            }
            if (checkInput(input, "Please enter a number between 1-5.", "[1-5]")) {
                continue;
            }

            // Process search choice
            switch (input) {
                case "1":
                    searchById();
                    break;
                case "2":
                    searchByName();
                    break;
                case "3":
                    searchByCourse();
                    break;
                case "4":
                    searchBySemester();
                    break;
                case "5":
                    miniLoop = false;
                    break;
            }
        }
    }

    /**
     * Searches for students by ID and displays all matching records Shows all
     * courses and semesters registered under the ID
     */
    private void searchById() {
        miniLoop = true;
        while (miniLoop) {
            System.out.print("Enter Student ID: ");
            String searchId = sc.nextLine().trim().toUpperCase();
            boolean found = false;
            if (searchId.isEmpty()) {
                System.out.println("Choice can't be empty.");
                continue;
            }
            if (checkInput(searchId, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue;
            }
            if (checkInput(searchId, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                continue;
            }
            if (checkInput(searchId, "Student ID example form (CExxxxxx), Please enter again.", "^(CE)[0-9]{6,6}")) {
                continue;
            }
            // Create temporary list for search results
            List<StudentInfo> searchResults = new ArrayList<>();
            for (StudentInfo student : studentStorage) {
                if (student.getStudentID().equalsIgnoreCase(searchId)) {
                    searchResults.add(student);
                    found = true;
                }
            }

            if (found) {
                // Display results in table format
                System.out.println("\n                    SEARCH RESULTS                    ");
                System.out.println("+-----+------------+------------------+----------+--------+");
                System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
                System.out.println("+-----+------------+------------------+----------+--------+");

                // Print each matching record
                for (int i = 0; i < searchResults.size(); i++) {
                    System.out.printf("|%5d", i + 1);
                    System.out.println(searchResults.get(i).getReport());
                }

                // Print summary
                System.out.println("+-----+------------+------------------+----------+--------+");
                miniLoop = false;
            } else {
                System.out.println("No student found with ID: " + searchId);
                miniLoop = false;
            }
        }
    }

    /**
     * Searches for students by name (case-insensitive, partial match)
     */
    private void searchByName() {
        miniLoop = true;
        while (miniLoop) {
            System.out.print("Enter Student Name: ");
            String searchName = sc.nextLine().trim();
            boolean found = false;
            if (searchName.isEmpty()) {
                System.out.println("Choice can't be empty.");
                continue;
            }
            if (checkInput(searchName, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue;
            }
            if (checkInput(searchName, "Can't be enter number.", "[a-zA-Z ]+")) {
                continue;
            }
            // Create temporary list for search results
            List<StudentInfo> searchResults = new ArrayList<>();
            for (StudentInfo student : studentStorage) {
                if (student.getStudentName().toLowerCase().contains(searchName.toLowerCase())) {
                    searchResults.add(student);
                    found = true;
                }
            }

            if (found) {
                // Display results in table format
                System.out.println("\n                    SEARCH RESULTS                    ");
                System.out.println("+-----+------------+------------------+----------+--------+");
                System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
                System.out.println("+-----+------------+------------------+----------+--------+");

                // Print each matching record
                for (int i = 0; i < searchResults.size(); i++) {
                    System.out.printf("|%5d", i + 1);
                    System.out.println(searchResults.get(i).getReport());
                }

                // Print summary
                System.out.println("+-----+------------+------------------+----------+--------+");
                miniLoop = false;
            } else {
                System.out.println("No student found with name containing: " + searchName);
                miniLoop = false;
            }
        }
    }

    /**
     * Searches for students by course
     */
    private void searchByCourse() {
        miniLoop = true;
        while (miniLoop) {
            System.out.print("Enter Course (JAVA, .NET, C/C++): ");
            String searchCourse = sc.nextLine().trim().toUpperCase();
            boolean found = false;
            if (searchCourse.isEmpty()) {
                System.out.println("Choice can't be empty.");
                continue;
            }
            if (checkInput(searchCourse, "Can't be enter special character except ., /, +.", "[a-zA-Z0-9./+ ]+")) {
                continue;
            }
            if (checkInput(searchCourse, "Can't be enter number.", "[a-zA-Z./+ ]+")) {
                continue;
            }
            if (checkInput(searchCourse, "Student Course can be enter only three form (JAVA|.NET|C/C++), Please enter again.", "^(JAVA)|^([.])(NET)$|^(C[/])(C)([+]{2,2})$")) {
                continue;
            }

            // Create temporary list for search results
            List<StudentInfo> searchResults = new ArrayList<>();
            for (StudentInfo student : studentStorage) {
                if (student.getStudentCourse().contains(searchCourse)) {
                    searchResults.add(student);
                    found = true;
                }
            }

            if (found) {
                // Display results in table format
                System.out.println("\n                    SEARCH RESULTS                    ");
                System.out.println("+-----+------------+------------------+----------+--------+");
                System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
                System.out.println("+-----+------------+------------------+----------+--------+");

                // Print each matching record
                for (int i = 0; i < searchResults.size(); i++) {
                    System.out.printf("|%5d", i + 1);
                    System.out.println(searchResults.get(i).getReport());
                }

                // Print summary
                System.out.println("+-----+------------+------------------+----------+--------+");
                miniLoop = false;
            } else {
                System.out.println("No student found in course: " + searchCourse);
                miniLoop = false;
            }
        }
    }

    /**
     * Searches for students by semester
     */
    private void searchBySemester() {
        miniLoop = true;
        while (miniLoop) {
            System.out.print("Enter Semester: ");
            String searchSemester = sc.nextLine().trim().toUpperCase();
            boolean found = false;
            if (searchSemester.isEmpty()) {
                System.out.println("Choice can't be empty.");
                continue;
            }
            if (checkInput(searchSemester, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue;
            }
            if (checkInput(searchSemester, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                continue;
            }
            if (checkInput(searchSemester, "Student semester example form (SPxx|SUxx|FAxx), Please enter again.", "^(SP)[0-9]{2,2}|^(SU)[0-9]{2,2}|^(FA)[0-9]{2,2}")) {
                continue;
            }

            // Create temporary list for search results
            List<StudentInfo> searchResults = new ArrayList<>();
            for (StudentInfo student : studentStorage) {
                if (student.getStudentSemester().equals(searchSemester)) {
                    searchResults.add(student);
                    found = true;
                }
            }

            if (found) {
                // Display results in table format
                System.out.println("\n                    SEARCH RESULTS                    ");
                System.out.println("+-----+------------+------------------+----------+--------+");
                System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
                System.out.println("+-----+------------+------------------+----------+--------+");

                // Print each matching record
                for (int i = 0; i < searchResults.size(); i++) {
                    System.out.printf("|%5d", i + 1);
                    System.out.println(searchResults.get(i).getReport());
                }

                // Print summary
                System.out.println("+-----+------------+------------------+----------+--------+");
                miniLoop = false;
            } else {
                System.out.println("No student found in semester: " + searchSemester);
                miniLoop = false;
            }
        }
    }

    /**
     * Provides menu for update and delete operations
     */
    public void updateDelete() {
        if (studentStorage.isEmpty()) {
            System.out.println("No student data available.");
            return;
        }

        miniLoop = true;
        while (miniLoop) {
            // Display update/delete menu
            System.out.println("\n----- Update/Delete Menu -----");
            System.out.println("1. Update Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            // Get and validate user choice
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Choice can't be empty.");
                continue;
            }
            if (checkInput(input, "Please enter a number between 1-3.", "[1-3]")) {
                continue;
            }

            // Process user choice
            switch (input) {
                case "1":
                    updateStudent();
                    break;
                case "2":
                    deleteStudent();
                    break;
                case "3":
                    miniLoop = false;
                    break;
            }
        }
    }

    /**
     * Updates existing student information with enhanced validation: - Name
     * update affects all records with the same ID - Semester update checks for
     * course limit in target semester - Course update validates against
     * existing courses and semester limits
     */
    private void updateStudent() {
        // Get and validate the student ID to update
        String updateId;
        while (true) {
            System.out.print("Enter Student ID to update: ");
            updateId = sc.nextLine().trim().toUpperCase();
            if (updateId.isEmpty()) {
                System.out.println("Student ID cannot be empty.");
                continue;
            }
            if (checkInput(updateId, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue;
            }
            if (checkInput(updateId, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                continue;
            }
            if (checkInput(updateId, "Student ID example form (CExxxxxx), Please enter again.", "^(CE)[0-9]{6,6}")) {
                continue;
            }
            break;
        }

        // Find all records with the given ID
        List<StudentInfo> updateList = new ArrayList<>();
        for (StudentInfo student : studentStorage) {
            if (student.getStudentID().equalsIgnoreCase(updateId)) {
                updateList.add(student);
            }
        }

        // Check if any records were found
        if (updateList.isEmpty()) {
            System.out.println("No student found with ID: " + updateId);
            return;
        }

        // Display current information in a table format
        System.out.println("\nCurrent Student Information:");
        System.out.println("+-----+------------+------------------+----------+--------+");
        System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
        System.out.println("+-----+------------+------------------+----------+--------+");
        for (int i = 0; i < updateList.size(); i++) {
            System.out.printf("|%5d", i + 1);
            System.out.println(updateList.get(i).getReport());
        }
        System.out.println("+-----+------------+------------------+----------+--------+");

        // Update menu loop
        while (true) {
            // Display update options
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Name");
            System.out.println("2. Semester");
            System.out.println("3. Course");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");

            // Validate user's choice
            String updateChoice = sc.nextLine().trim();
            if (updateChoice.isEmpty() || !updateChoice.matches("[1-4]")) {
                System.out.println("Please enter a number between 1-4.");
                continue;
            }

            switch (updateChoice) {
                case "1":
                    // Update name for all records with the same ID
                    while (true) {
                        System.out.print("Enter new name: ");
                        String newName = sc.nextLine().trim();
                        if (newName.isEmpty()) {
                            System.out.println("Name cannot be empty.");
                            continue;
                        }
                        if (checkInput(newName, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                            continue;
                        }
                        if (checkInput(newName, "Can't be enter number.", "[a-zA-Z ]+")) {
                            continue;
                        }
                        // Update all records with matching ID
                        for (StudentInfo student : studentStorage) {
                            if (student.getStudentID().equalsIgnoreCase(updateId)) {
                                student.setStudentName(newName);
                            }
                        }
                        System.out.println("Name updated successfully!");
                        break;
                    }
                    break;

                case "2":
                    // Update semester for a specific record
                    while (true) {
                        System.out.print("Enter record number to update semester (1-" + updateList.size() + "): ");
                        String recordInput = sc.nextLine().trim();
                        if (recordInput.isEmpty()) {
                            System.out.println("Record number cannot be empty.");
                            continue;
                        }
                        int recordNum;
                        try {
                            recordNum = Integer.parseInt(recordInput) - 1;
                            if (recordNum < 0 || recordNum >= updateList.size()) {
                                System.out.println("Invalid record number.");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number.");
                            continue;
                        }

                        // Get and validate new semester
                        System.out.print("Enter new semester (SPxx|SUxx|FAxx): ");
                        String newSemester = sc.nextLine().trim().toUpperCase();
                        if (newSemester.isEmpty()) {
                            System.out.println("Semester cannot be empty.");
                            continue;
                        }
                        if (!newSemester.matches("^(SP|SU|FA)[0-9]{2}$")) {
                            System.out.println("Invalid semester format. Use SPxx, SUxx, or FAxx.");
                            continue;
                        }

                        // Get target student
                        StudentInfo targetStudent = updateList.get(recordNum);
                        String currentSemester = targetStudent.getStudentSemester();
                        String currentCourse = targetStudent.getStudentCourse();

                        // If updating to the same semester, no need to check
                        if (currentSemester.equals(newSemester)) {
                            System.out.println("New semester is same as current semester. No update needed.");
                            continue;
                        }

                        // Check courses in the new semester for this student
                        Set<String> coursesInNewSemester = new HashSet<>();
                        for (StudentInfo student : studentStorage) {
                            if (student.getStudentID().equals(targetStudent.getStudentID())
                                    && student.getStudentSemester().equals(newSemester)) {
                                coursesInNewSemester.add(student.getStudentCourse());
                            }
                        }

                        // Check if the course already exists in the new semester
                        if (coursesInNewSemester.contains(currentCourse)) {
                            System.out.println("You already have this course in semester " + newSemester);
                            break;
                        }

                        // Check if the new semester already has 3 courses
                        if (coursesInNewSemester.size() >= 3) {
                            System.out.println("Cannot update to semester " + newSemester + " as it already has all 3 courses.");
                            continue;
                        }

                        // Update the semester since all checks passed
                        targetStudent.setStudentSemester(newSemester);
                        System.out.println("Semester updated successfully!");
                        break;
                    }
                    break;

                case "3":
                    // Update course for a specific record
                    while (true) {
                        // First, get and validate the semester to check
                        System.out.print("Enter semester to check (SPxx|SUxx|FAxx): ");
                        String checkSemester = sc.nextLine().trim().toUpperCase();
                        if (checkSemester.isEmpty()) {
                            System.out.println("Semester cannot be empty.");
                            continue;
                        }
                        if (!checkSemester.matches("^(SP|SU|FA)[0-9]{2}$")) {
                            System.out.println("Invalid semester format. Use SPxx, SUxx, or FAxx.");
                            continue;
                        }

                        // Check if the semester exists in the system
                        boolean semesterExists = false;
                        for (StudentInfo student : studentStorage) {
                            if (student.getStudentSemester().equals(checkSemester)) {
                                semesterExists = true;
                                break;
                            }
                        }

                        if (!semesterExists) {
                            System.out.println("This semester does not exist in the system.");
                            continue;
                        }

                        // Check if the semester has all 3 courses
                        Set<String> coursesInSemester = new HashSet<>();
                        for (StudentInfo student : studentStorage) {
                            if (student.getStudentSemester().equals(checkSemester)) {
                                coursesInSemester.add(student.getStudentCourse());
                            }
                        }

                        // If semester already has all 3 courses, return to menu
                        if (coursesInSemester.size() >= 3) {
                            System.out.println("This semester already has all 3 courses (Java, .Net, C/C++). Cannot update course.");
                            break;  // Return to menu
                        }

                        // Create a list of records for the specified semester
                        List<StudentInfo> semesterRecords = new ArrayList<>();
                        for (StudentInfo student : updateList) {
                            if (student.getStudentSemester().equals(checkSemester)) {
                                semesterRecords.add(student);
                            }
                        }

                        if (semesterRecords.isEmpty()) {
                            System.out.println("No courses found in semester " + checkSemester + " for this student.");
                            continue;
                        }

                        // Display records for the selected semester
                        System.out.println("\nCourses in semester " + checkSemester + ":");
                        System.out.println("+-----+------------+------------------+----------+--------+");
                        System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
                        System.out.println("+-----+------------+------------------+----------+--------+");
                        for (int i = 0; i < semesterRecords.size(); i++) {
                            System.out.printf("|%5d", i + 1);
                            System.out.println(semesterRecords.get(i).getReport());
                        }
                        System.out.println("+-----+------------+------------------+----------+--------+");

                        // If semester is not full, proceed with course update
                        System.out.print("Enter record number to update course (1-" + semesterRecords.size() + "): ");
                        String recordInput = sc.nextLine().trim();
                        if (recordInput.isEmpty()) {
                            System.out.println("Record number cannot be empty.");
                            continue;
                        }
                        int recordNum;
                        try {
                            recordNum = Integer.parseInt(recordInput) - 1;
                            if (recordNum < 0 || recordNum >= semesterRecords.size()) {
                                System.out.println("Invalid record number.");
                                continue;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number.");
                            continue;
                        }

                        // Get and validate new course
                        System.out.print("Enter new course (Java|.Net|C/C++): ");
                        String newCourse = sc.nextLine().trim().toUpperCase();
                        if (newCourse.isEmpty()) {
                            System.out.println("Course cannot be empty.");
                            continue;
                        }
                        if (!newCourse.matches("^(JAVA)|^([.])(NET)$|^(C[/])(C)([+]{2,2})$")) {
                            System.out.println("Invalid course. Choose Java, .Net, or C/C++");
                            continue;
                        }

                        // Get target student
                        StudentInfo targetStudent = semesterRecords.get(recordNum);

                        // Check if the new course already exists for this student in the semester
                        boolean courseExists = false;
                        for (StudentInfo student : studentStorage) {
                            if (student.getStudentSemester().equals(checkSemester) && student.getStudentID().equals(targetStudent.getStudentID()) && student.getStudentCourse().equals(newCourse)) {
                                courseExists = true;
                                break;
                            }
                        }

                        // Prevent update if course already exists for this student in the semester
                        if (courseExists) {
                            System.out.println("You have already registered for this course in this semester.");
                            continue;
                        }

                        // Update the course since all checks passed
                        targetStudent.setStudentCourse(newCourse);
                        System.out.println("Course updated successfully!");
                        break;
                    }
                    break;

                case "4":
                    return;
            }

            // Display updated information in table format
            System.out.println("\nUpdated Student Information:");
            System.out.println("+-----+------------+------------------+----------+--------+");
            System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
            System.out.println("+-----+------------+------------------+----------+--------+");
            for (int i = 0; i < updateList.size(); i++) {
                System.out.printf("|%5d", i + 1);
                System.out.println(updateList.get(i).getReport());
            }
            System.out.println("+-----+------------+------------------+----------+--------+");
        }
    }

    /**
     * Deletes a specific student record based on ID, Semester, and Course. All
     * three parameters must match exactly for the record to be deleted.
     */
    private void deleteStudent() {
        // Get deletion criteria from user
        String deleteId;
        String deleteSemester;
        String deleteCourse;

        // Get and validate Student ID
        while (true) {
            System.out.print("Enter Student ID: ");
            deleteId = sc.nextLine().trim().toUpperCase(); // Read user input, trim spaces, and convert to uppercase

            if (deleteId.isEmpty()) { // Ensure input is not empty
                System.out.println("Student ID cannot be empty.");
                continue;
            }

            // Check for special characters
            if (checkInput(deleteId, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue;
            }

            // Check for spaces
            if (checkInput(deleteId, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                continue;
            }

            // Validate Student ID format (e.g., CExxxxxx)
            if (checkInput(deleteId, "Student ID example form (CExxxxxx), Please enter again.", "^(CE)[0-9]{6,6}")) {
                continue;
            }
            break;
        }

        // Get and validate Semester
        while (true) {
            System.out.print("Enter Semester: ");
            deleteSemester = sc.nextLine().trim().toUpperCase(); // Read, trim, and convert to uppercase

            if (deleteSemester.isEmpty()) { // Ensure input is not empty
                System.out.println("Semester cannot be empty.");
                continue;
            }

            // Check for special characters
            if (checkInput(deleteSemester, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue;
            }

            // Check for spaces
            if (checkInput(deleteSemester, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                continue;
            }

            // Validate semester format (e.g., SPxx, SUxx, FAxx)
            if (checkInput(deleteSemester, "Student semester example form (SPxx|SUxx|FAxx), Please enter again.",
                    "^(SP)[0-9]{2,2}|^(SU)[0-9]{2,2}|^(FA)[0-9]{2,2}")) {
                continue;
            }
            break;
        }
        // Get and validate Course
        while (true) {
            System.out.print("Enter Course: ");
            deleteCourse = sc.nextLine().trim().toUpperCase(); // Read, trim, and convert to uppercase
            if (deleteCourse.isEmpty()) { // Ensure input is not empty
                System.out.println("Course cannot be empty.");
                continue;
            }
            // Allow only letters, numbers, and specific special characters (., /, +)
            if (checkInput(deleteCourse, "Can't be enter special character except ., /, +.", "[a-zA-Z0-9./+ ]+")) {
                continue;
            }
            // Ensure no numbers are used alone in the course name
            if (checkInput(deleteCourse, "Can't be enter number.", "[a-zA-Z./+ ]+")) {
                continue;
            }
            // Validate allowed course formats (JAVA, .NET, C/C++)
            if (checkInput(deleteCourse, "Student Course can be enter only three form (JAVA|.NET|C/C++), Please enter again.", "^(JAVA)|^([.])(NET)$|^(C[/])(C)([+]{2,2})$")) {
                continue;
            }
            break;
        }

        // Find the specific record to delete
        StudentInfo toDelete = null;
        int deleteIndex = -1;

        // Search for exact match of ID, Semester, and Course
        for (int i = 0; i < studentStorage.size(); i++) {
            StudentInfo student = studentStorage.get(i);
            // Check if all three values (ID, Semester, Course) match
            if (student.getStudentID().equalsIgnoreCase(deleteId) && student.getStudentSemester().equals(deleteSemester) && student.getStudentCourse().equals(deleteCourse)) {
                toDelete = student; // Store the matching student record
                deleteIndex = i; // Store the index for deletion
                break;
            }
        }

        // If no matching record found, return
        if (toDelete == null) {
            System.out.println("No matching record found with the specified ID, Semester and Course.");
            return;
        }

        // Display the record to be deleted
        System.out.println("\nRecord to be deleted:");
        System.out.println("+------------+------------------+----------+--------+");
        System.out.println("| Student ID |   Student Name   | Semester | Course |");
        System.out.println("+------------+------------------+----------+--------+");
        System.out.println(toDelete.getReport()); // Print the formatted student details
        System.out.println("+------------+------------------+----------+--------+");

        // Confirm deletion with user
        while (true) {
            System.out.print("Are you sure you want to delete this record? (Y/N): ");
            String confirm = sc.nextLine().trim().toUpperCase(); // Read input and convert to uppercase

            if (confirm.equals("Y")) { // If user confirms, delete the record
                studentStorage.remove(deleteIndex);
                System.out.println("Record deleted successfully!");
                break;
            } else if (confirm.equals("N")) { // If user cancels, exit deletion
                System.out.println("Deletion cancelled.");
                break;
            } else { // If invalid input, prompt again
                System.out.println("Please enter Y or N.");
            }
        }
    }

    /**
     * Validates user input against a regular expression pattern
     *
     * @param input The user input to validate
     * @param msg Error message to display if validation fails
     * @param keyWord Regular expression pattern to match against
     * @return boolean indicating if input is invalid (true = invalid, false =
     * valid)
     */
    public boolean checkInput(String input, String msg, String keyWord) {
        try {
            // Check if input matches the required pattern
            if (!input.matches(keyWord)) {
                throw new Exception(msg);
            }
            return false;  // Input is valid
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;   // Input is invalid
        }
    }
}
