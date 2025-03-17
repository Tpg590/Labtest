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
        // Display the main menu title
        System.out.println("        WELCOME TO STUDENT MANAGEMENT");

// Display menu options for the user to choose from
        System.out.println("1. Create");       // Option to create a new student record
        System.out.println("2. Find and Sort"); // Option to find a student or sort records
        System.out.println("3. Update/Delete"); // Option to update or delete a student record
        System.out.println("4. Report");        // Option to generate a report of students
        System.out.println("5. Exit");          // Option to exit the program

    }

    /**
     * Displays the subsequent menu after first use
     */
    public void secondMenu() {
        // Display the main menu title
        System.out.println("        WELCOME TO STUDENT MANAGEMENT");

// Display menu options for the user to choose from
        System.out.println("1. Create");       // Option to create a new student record
        System.out.println("2. Find and Sort"); // Option to find a student or sort records
        System.out.println("3. Update/Delete"); // Option to update or delete a student record
        System.out.println("4. Report");        // Option to generate a report of students
        System.out.println("5. Exit");          // Option to exit the program

    }

    /**
     * Main program loop that handles menu navigation and user choices
     */
    public void run() {
        // Display appropriate menu based on first use
        if (useOption == true) { // If this is the first time running
            firstMenu(); // Show the first menu
        }
        // Main program loop
        while (loop) {
            if (useOption == false) { // If not the first use, show the second menu
                secondMenu();
            }
            useOption = false; // Reset the flag after the first use

            // Prompt user for input
            System.out.print("        Please choose: ");
            choice = sc.nextLine().trim(); // Read and trim user input

            // Validate user input
            if (choice.isEmpty()) { // Check if input is empty
                System.out.println("Choice can't be empty.");
                continue; // Restart the loop
            }

            // Ensure input does not contain special characters
            if (checkInput(choice, "Can't enter special characters.", "[a-zA-Z0-9 ]+")) {
                continue;
            }

            // Ensure input does not contain spaces
            if (checkInput(choice, "Can't enter spaces.", "[0-9a-zA-Z]+")) {
                continue;
            }

            // Ensure input is not a character (must be a number)
            if (checkInput(choice, "Can't enter letters.", "[0-9 ]+")) {
                continue;
            }

            // Ensure input is between 1-5
            if (checkInput(choice, "Please enter a valid option (1-5).", "[1-5]")) {
                continue;
            }

            // Process user choice using switch-case
            switch (choice) {
                case "1":
                    createStudentInfo(); // Call method to create a new student record
                    break;
                case "2":
                    findStudent(); // Call method to find and sort students
                    break;
                case "3":
                    updateDelete(); // Call method to update or delete student records
                    break;
                case "4":
                    report(); // Call method to generate a student report
                    break;
                case "5":
                    loop = false; // Exit the loop and end the program
                    break;
            }
        }
    }

    /**
     * Creates a new student record or adds a course registration for existing
     * student
     */
    public void createStudentInfo() {
        // Prompt user to input Student ID
        inputStudentID();

// Check if studentName is null (indicating a new student ID)
        if (studentName == null) {
            inputStudentName(); // If it's a new student ID, prompt user to input the student's name
        }

// Prompt user to input Semester
        inputStudentSemester();

// Prompt user to input Course
        inputStudentCourse();

// Create a new StudentInfo object with the collected details and add it to the storage list
        studentStorage.add(new StudentInfo(studentID, studentName, studentSemester, studentCourse));

    }

    /**
     * Handles student name input with validation - Name must contain only
     * letters and spaces - Cannot be empty
     */
    public void inputStudentName() {
        // Initialize miniLoop to true for input validation loop
        miniLoop = true;

// Start a loop to continuously prompt the user until valid input is received
        while (miniLoop) {
            System.out.print("Enter Name: ");
            input = sc.nextLine().trim(); // Read user input and remove leading/trailing spaces

            // Check if the input is empty
            if (input.isEmpty()) {
                System.out.println("Choice can't be empty."); // Warn the user if input is blank
                continue; // Restart the loop to get a valid input
            }

            // Validate that input does not contain special characters
            if (checkInput(input, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue; // Restart loop if validation fails
            }

            // Validate that input does not contain numbers
            if (checkInput(input, "Can't be enter number.", "[a-zA-Z ]+")) {
                continue; // Restart loop if validation fails
            }

            // If all validations pass, assign the input to studentName
            studentName = input;

            // Exit the loop since we got a valid name
            miniLoop = false;
        }

    }

    /**
     * Handles student ID input with validation - ID must follow format CExxxxxx
     * where x is a digit - Checks for existing IDs and retrieves student name
     * if found
     */
    public void inputStudentID() {
        // Special handling for the first student entry
        if (studentStorage.isEmpty()) {
            // Initialize miniLoop to true for input validation loop
            miniLoop = true;

            // Start a loop to continuously prompt the user until valid input is received
            while (miniLoop) {
                System.out.print("Enter ID: ");
                input = sc.nextLine().trim().toUpperCase(); // Read user input, remove spaces, and convert to uppercase
                stepOneInputID = input; // Store the input temporarily

                // Check if the input is empty
                if (input.isEmpty()) {
                    System.out.println("Choice can't be empty."); // Warn the user if input is blank
                    continue; // Restart the loop to get valid input
                }

                // Validate that input does not contain special characters (except letters and numbers)
                if (checkInput(input, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                    continue; // Restart loop if validation fails
                }

                // Validate that input does not contain spaces
                if (checkInput(input, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                    continue; // Restart loop if validation fails
                }

                // Validate that the input follows the format "CExxxxxx" (CE followed by 6 digits)
                if (checkInput(input, "Student ID example form (CExxxxxx), Please enter again.", "^(CE)[0-9]{6,6}")) {
                    continue; // Restart loop if validation fails
                }

                // If all validations pass, assign the input to studentID
                studentID = input;
                studentName = null; // Reset studentName for a new student ID

                // Exit the loop since a valid student ID is entered
                miniLoop = false;
            }

            // Return immediately since this is the first student entry
            return;
        }

        // Normal ID input process
        miniLoop = true; // Initialize loop control variable

        while (miniLoop) {
            System.out.print("Enter ID: ");
            input = sc.nextLine().trim().toUpperCase(); // Read input, trim spaces, convert to uppercase
            stepOneInputID = input; // Store the input temporarily

            // Validate input format
            // Check if input is empty
            if (input.isEmpty()) {
                System.out.println("Choice can't be empty."); // Display error message
                continue; // Restart loop to get valid input
            }

            // Ensure input contains only letters and numbers (no special characters)
            if (checkInput(input, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue; // Restart loop if validation fails
            }

            // Ensure input does not contain spaces
            if (checkInput(input, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                continue; // Restart loop if validation fails
            }

            // Ensure input follows the format "CExxxxxx" (CE followed by 6 digits)
            if (checkInput(input, "Student ID example form (CExxxxxx), Please enter again.", "^(CE)[0-9]{6,6}")) {
                continue; // Restart loop if validation fails
            }

            // Check for existing ID in the student storage
            boolean idExists = false; // Flag to track if the ID is found

            for (StudentInfo studentInfo : studentStorage) { // Loop through stored students
                if (studentInfo.getStudentID().equals(input)) { // Compare entered ID with stored ones
                    System.out.println("This ID existed so skip step enter name:"); // Notify user
                    studentID = input; // Assign ID
                    studentName = studentInfo.getStudentName(); // Retrieve existing name
                    idExists = true; // Mark ID as found
                    break; // Exit loop since ID is found
                }
            }

            // If ID is not found, assign input to studentID and reset studentName for new entry
            if (!idExists) {
                studentID = input;
                studentName = null; // Reset studentName for a new ID
            }

            // Exit the input loop since a valid ID has been entered
            miniLoop = false;
        }

    }

    /**
     * Handles semester input with validation - Semester must follow format
     * SPxx, SUxx, or FAxx where x is a digit - Checks if student has already
     * registered for 3 courses in the semester
     */
    public void inputStudentSemester() {
        // Special handling for the first student entry
        if (studentStorage.isEmpty()) { // Check if there are no existing students in storage
            miniLoop = true; // Initialize loop control variable

            while (miniLoop) {
                System.out.print("Enter semester: "); // Prompt user for semester input
                input = sc.nextLine().trim().toUpperCase(); // Read input, trim spaces, convert to uppercase
                stepThreeInputSemester = input; // Store input for later reference

                // Validate input format
                // Check if input is empty
                if (input.isEmpty()) {
                    System.out.println("Choice can't be empty."); // Display error message
                    continue; // Restart loop to get valid input
                }

                // Ensure input contains only letters and numbers (no special characters except space)
                if (checkInput(input, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                    continue; // Restart loop if validation fails
                }

                // Ensure input does not contain spaces
                if (checkInput(input, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                    continue; // Restart loop if validation fails
                }

                // Ensure input follows the required semester format (SPxx, SUxx, FAxx)
                if (checkInput(input, "Student semester example form (SPxx|SUxx|FAxx), Please enter again.",
                        "^(SP)[0-9]{2,2}|^(SU)[0-9]{2,2}|^(FA)[0-9]{2,2}")) {
                    continue; // Restart loop if validation fails
                }

                // If input is valid, assign it to studentSemester
                studentSemester = input;
                miniLoop = false; // Exit loop
                return; // Exit function since the first student has been handled
            }
        }

        // Normal semester input process
        miniLoop = true; // Initialize loop control variable

        while (miniLoop) {
            System.out.print("Enter semester: "); // Prompt user to enter semester
            input = sc.nextLine().trim().toUpperCase(); // Read input, trim spaces, and convert to uppercase
            stepThreeInputSemester = input; // Store input for reference

            // Validate input format
            // Check if input is empty
            if (input.isEmpty()) {
                System.out.println("Choice can't be empty."); // Display error message
                continue; // Restart loop to get valid input
            }

            // Ensure input contains only letters and numbers (no special characters except spaces)
            if (checkInput(input, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue; // Restart loop if validation fails
            }

            // Ensure input does not contain spaces (removes cases where spaces may cause errors)
            if (checkInput(input, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                continue; // Restart loop if validation fails
            }

            // Ensure input follows the required semester format (SPxx, SUxx, or FAxx, where xx is two digits)
            if (checkInput(input, "Student semester example form (SPxx|SUxx|FAxx), Please enter again.",
                    "^(SP)[0-9]{2,2}|^(SU)[0-9]{2,2}|^(FA)[0-9]{2,2}")) {
                continue; // Restart loop if validation fails
            }

            // Check the number of courses the student has already registered for this semester
            Set<String> studentCourses = new HashSet<>(); // Create a set to track unique courses

            for (StudentInfo studentInfo : studentStorage) { // Loop through stored student records
                // Check if student ID matches and semester matches
                if (studentInfo.getStudentID().equals(stepOneInputID) && studentInfo.getStudentSemester().equals(input)) {
                    studentCourses.add(studentInfo.getStudentCourse()); // Add course to set
                }
            }

            // Prevent registration if student has already registered for 3 courses in this semester
            if (studentCourses.size() >= 3) {
                System.out.println("You have already registered all 3 courses in this semester. Please choose another semester.");
                continue; // Restart loop to enter a different semester
            }

            // If all checks pass, assign input to studentSemester
            studentSemester = input;
            miniLoop = false; // Exit loop
        }

    }

    /**
     * Handles course input with validation - Course must be either Java, .Net,
     * or C/C++ - Checks if student has already registered for this course in
     * the semester
     */
    public void inputStudentCourse() {
        // Special handling for the first student (if student storage is empty)
        if (studentStorage.isEmpty()) {
            miniLoop = true; // Initialize loop control variable

            while (miniLoop) {
                System.out.print("Enter course: "); // Prompt the user to enter a course
                input = sc.nextLine().trim().toUpperCase(); // Read input, trim spaces, convert to uppercase

                // Validate input format
                // Check if input is empty
                if (input.isEmpty()) {
                    System.out.println("Choice can't be empty."); // Display error message
                    continue; // Restart loop to get valid input
                }

                // Ensure input only contains letters, numbers, and allowed special characters (., /, +)
                if (checkInput(input, "Can't be enter special character except ., /, +.", "[a-zA-Z0-9./+ ]+")) {
                    continue; // Restart loop if validation fails
                }

                // Ensure input does not contain numbers (only letters and allowed special characters)
                if (checkInput(input, "Can't be enter number.", "[a-zA-Z./+ ]+")) {
                    continue; // Restart loop if validation fails
                }

                // Ensure input matches one of the three allowed course formats (JAVA, .NET, or C/C++)
                if (checkInput(input, "Student Course can be enter only three form (JAVA|.NET|C/C++), Please enter again.",
                        "^(JAVA)|^([.])(NET)$|^(C[/])(C)([+]{2,2})$")) {
                    continue; // Restart loop if validation fails
                }

                // Assign valid input to studentCourse
                studentCourse = input;
                miniLoop = false; // Exit loop
                return; // Exit function (since this block is for the first student only)
            }
        }

// Normal course input process (for students who are not the first entry)
        miniLoop = true; // Initialize loop control variable

        while (miniLoop) {
            System.out.print("Enter course: "); // Prompt user to enter a course  
            input = sc.nextLine().trim().toUpperCase(); // Read input, trim spaces, convert to uppercase  

            // Validate input format
            // Check if input is empty  
            if (input.isEmpty()) {
                System.out.println("Choice can't be empty."); // Display error message  
                continue; // Restart loop to get valid input  
            }

            // Ensure input only contains letters, numbers, and allowed special characters (., /, +)  
            if (checkInput(input, "Can't be enter special character except ., /, +.", "[a-zA-Z0-9./+ ]+")) {
                continue; // Restart loop if validation fails  
            }

            // Ensure input does not contain numbers (only letters and allowed special characters)  
            if (checkInput(input, "Can't be enter number.", "[a-zA-Z./+ ]+")) {
                continue; // Restart loop if validation fails  
            }

            // Ensure input matches one of the three allowed course formats (JAVA, .NET, or C/C++)  
            if (checkInput(input, "Student Course can be enter only three form (JAVA|.NET|C/C++), Please enter again.",
                    "^(JAVA)|^([.])(NET)$|^(C[/])(C)([+]{2,2})$")) {
                continue; // Restart loop if validation fails  
            }

            // Check if student already registered for this course in the given semester  
            boolean alreadyRegistered = false; // Flag to track duplicate course registration  
            for (StudentInfo studentInfo : studentStorage) {
                // Check if student ID, semester, and course match an existing entry  
                if (studentInfo.getStudentID().equals(stepOneInputID)
                        && studentInfo.getStudentSemester().equals(stepThreeInputSemester)
                        && studentInfo.getStudentCourse().equals(input)) {
                    System.out.println("Error, this course already exists. Please enter again."); // Display error  
                    alreadyRegistered = true; // Set flag to true  
                    break; // Exit loop since duplicate is found  
                }
            }

            // If the course is already registered, restart the loop  
            if (alreadyRegistered) {
                continue;
            }

            // Assign valid input to studentCourse  
            studentCourse = input;
            miniLoop = false; // Exit loop  
        }

    }

    /**
     * Generates a formatted report of all student records Displays in table
     * format with columns for ID, Name, Semester, and Course
     */
    public void report() {
        // Check if there are any students in the storage
        if (studentStorage.isEmpty()) {
            System.out.println("Have no student to report"); // Inform user that there are no students
            return; // Exit the method
        }

// Print report header
        System.out.println("                       REPORT TABLE                        ");
        System.out.println("+-----+------------+------------------+----------+--------+");
        System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
        System.out.println("+-----+------------+------------------+----------+--------+");

// Loop through each student and print their report details
        for (int i = 0; i < studentStorage.size(); i++) {
            System.out.printf("|%5d", i + 1); // Print row number with proper formatting  
            System.out.println(studentStorage.get(i).getReport()); // Get and print the report data for the student
        }

// Print table footer
        System.out.println("+-----+------------+------------------+----------+--------+");

    }

    /**
     * Main search functionality that provides different search options - Search
     * by ID - Search by Name - Search by Course - Search by Semester
     */
    public void findStudent() {
        // Check if studentStorage is empty before allowing a search
        if (studentStorage.isEmpty()) {
            System.out.println("No student data available."); // Inform user that there are no students
            return; // Exit the method
        }

        miniLoop = true; // Set miniLoop to true to keep the search menu running
        while (miniLoop) {
            // Display search menu options
            System.out.println("\n----- Search Menu -----");
            System.out.println("1. Search by ID");         // Option to search students by ID
            System.out.println("2. Search by Name");       // Option to search students by Name
            System.out.println("3. Search by Course");     // Option to search students by Course
            System.out.println("4. Search by Semester");   // Option to search students by Semester
            System.out.println("5. Back to Main Menu");    // Option to return to the main menu
            System.out.print("Enter your choice: ");       // Prompt user for input

            // Get and validate user choice
            input = sc.nextLine().trim(); // Read and trim user input
            if (input.isEmpty()) {  // Check if input is empty
                System.out.println("Choice can't be empty."); // Show error message
                continue; // Restart the loop
            }
            if (checkInput(input, "Please enter a number between 1-5.", "[1-5]")) {
                continue; // Restart the loop if input is invalid
            }

            // Process search choice using switch statement
            switch (input) {
                case "1":
                    searchById(); // Call method to search by student ID
                    break;
                case "2":
                    searchByName(); // Call method to search by student Name
                    break;
                case "3":
                    searchByCourse(); // Call method to search by Course
                    break;
                case "4":
                    searchBySemester(); // Call method to search by Semester
                    break;
                case "5":
                    miniLoop = false; // Exit the loop and return to the main menu
                    break;
            }
        }

    }

    /**
     * Searches for students by ID and displays all matching records Shows all
     * courses and semesters registered under the ID
     */
    private void searchById() {
        miniLoop = true; // Initialize loop to allow repeated input if necessary
        while (miniLoop) {
            System.out.print("Enter Student ID: "); // Prompt user to enter a student ID
            String searchId = sc.nextLine().trim().toUpperCase(); // Read input, trim whitespace, and convert to uppercase
            boolean found = false; // Flag to check if the student ID exists

            // Validate input - ensure it is not empty
            if (searchId.isEmpty()) {
                System.out.println("Choice can't be empty."); // Display error message
                continue; // Restart loop
            }

            // Validate input format - prevent special characters
            if (checkInput(searchId, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue; // Restart loop if input contains invalid characters
            }

            // Validate input format - prevent spaces
            if (checkInput(searchId, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                continue; // Restart loop if input contains spaces
            }

            // Validate input format - ensure Student ID follows correct pattern (e.g., CExxxxxx)
            if (checkInput(searchId, "Student ID example form (CExxxxxx), Please enter again.", "^(CE)[0-9]{6,6}")) {
                continue; // Restart loop if format is incorrect
            }

            // Create a temporary list to store search results
            List<StudentInfo> searchResults = new ArrayList<>();

            // Iterate through student storage to find matches
            for (StudentInfo student : studentStorage) {
                if (student.getStudentID().equalsIgnoreCase(searchId)) { // Case-insensitive comparison
                    searchResults.add(student); // Add matching student to results list
                    found = true; // Set flag to indicate a match was found
                }
            }

            // If a matching student ID is found, display results
            if (found) {
                System.out.println("\n                    SEARCH RESULTS                    ");
                System.out.println("+-----+------------+------------------+----------+--------+");
                System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
                System.out.println("+-----+------------+------------------+----------+--------+");

                // Print each student record found in search results
                for (int i = 0; i < searchResults.size(); i++) {
                    System.out.printf("|%5d", i + 1);
                    System.out.println(searchResults.get(i).getReport()); // Calls getReport() to format student data
                }

                // Print summary table footer
                System.out.println("+-----+------------+------------------+----------+--------+");
                miniLoop = false; // Exit the loop since search is successful

            } else { // If no student found
                System.out.println("No student found with ID: " + searchId);
                miniLoop = false; // Exit the loop
            }
        }

// Sort the student storage by ID after the search operation
        sortByID();

    }

    /**
     * Searches for students by name (case-insensitive, partial match)
     */
    private void searchByName() {
        miniLoop = true; // Initialize loop to allow repeated input if necessary
        while (miniLoop) {
            System.out.print("Enter Student Name: "); // Prompt user to enter a student name
            String searchName = sc.nextLine().trim(); // Read input and trim whitespace
            boolean found = false; // Flag to check if the student name exists

            // Validate input - ensure it is not empty
            if (searchName.isEmpty()) {
                System.out.println("Choice can't be empty."); // Display error message
                continue; // Restart loop
            }

            // Validate input format - prevent special characters
            if (checkInput(searchName, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue; // Restart loop if input contains invalid characters
            }

            // Validate input format - prevent numbers
            if (checkInput(searchName, "Can't be enter number.", "[a-zA-Z ]+")) {
                continue; // Restart loop if input contains numbers
            }

            // Create a temporary list to store search results
            List<StudentInfo> searchResults = new ArrayList<>();

            // Iterate through student storage to find matching names (case-insensitive search)
            for (StudentInfo student : studentStorage) {
                if (student.getStudentName().toLowerCase().contains(searchName.toLowerCase())) {
                    searchResults.add(student); // Add matching student to results list
                    found = true; // Set flag to indicate a match was found
                }
            }

            // If a matching student name is found, display results
            if (found) {
                System.out.println("\n                    SEARCH RESULTS                    ");
                System.out.println("+-----+------------+------------------+----------+--------+");
                System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
                System.out.println("+-----+------------+------------------+----------+--------+");

                // Print each student record found in search results
                for (int i = 0; i < searchResults.size(); i++) {
                    System.out.printf("|%5d", i + 1);
                    System.out.println(searchResults.get(i).getReport()); // Calls getReport() to format student data
                }

                // Print summary table footer
                System.out.println("+-----+------------+------------------+----------+--------+");
                miniLoop = false; // Exit the loop since search is successful

            } else { // If no student found
                System.out.println("No student found with name containing: " + searchName);
                miniLoop = false; // Exit the loop
            }
        }

// Sort student records by first name after the search operation
        sortFirstName();

    }

    /**
     * Searches for students by course
     */
    private void searchByCourse() {
        // Initialize loop to allow repeated input if necessary
        miniLoop = true;
        while (miniLoop) {
            // Prompt user to enter a course name
            System.out.print("Enter Course (JAVA, .NET, C/C++): ");
            String searchCourse = sc.nextLine().trim().toUpperCase(); // Read input and convert to uppercase
            boolean found = false; // Flag to track if the course is found

            // Validate input - ensure it is not empty
            if (searchCourse.isEmpty()) {
                System.out.println("Choice can't be empty."); // Display error message
                continue; // Restart loop
            }

            // Validate input format - allow only letters, numbers, '.', '/', and '+'
            if (checkInput(searchCourse, "Can't be enter special character except ., /, +.", "[a-zA-Z0-9./+ ]+")) {
                continue; // Restart loop if invalid characters are found
            }

            // Validate input format - prevent numbers
            if (checkInput(searchCourse, "Can't be enter number.", "[a-zA-Z./+ ]+")) {
                continue; // Restart loop if numbers are found
            }

            // Validate input format - ensure it matches one of the three valid course names (JAVA, .NET, C/C++)
            if (checkInput(searchCourse, "Student Course can be enter only three form (JAVA|.NET|C/C++), Please enter again.",
                    "^(JAVA)|^([.])(NET)$|^(C[/])(C)([+]{2,2})$")) {
                continue; // Restart loop if input does not match valid formats
            }

            // Create a temporary list to store students registered for the specified course
            List<StudentInfo> searchResults = new ArrayList<>();

            // Iterate through student storage to find matching courses
            for (StudentInfo student : studentStorage) {
                if (student.getStudentCourse().contains(searchCourse)) { // Check if the course matches
                    searchResults.add(student); // Add matching student to results list
                    found = true; // Set flag to indicate a match was found
                }
            }

            // If a matching course is found, display results
            if (found) {
                System.out.println("\n                    SEARCH RESULTS                    ");
                System.out.println("+-----+------------+------------------+----------+--------+");
                System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
                System.out.println("+-----+------------+------------------+----------+--------+");

                // Print each student record found in search results
                for (int i = 0; i < searchResults.size(); i++) {
                    System.out.printf("|%5d", i + 1);
                    System.out.println(searchResults.get(i).getReport()); // Calls getReport() to format student data
                }

                // Print summary table footer
                System.out.println("+-----+------------+------------------+----------+--------+");
                miniLoop = false; // Exit the loop since search is successful

            } else { // If no student found in the specified course
                System.out.println("No student found in course: " + searchCourse);
                miniLoop = false; // Exit the loop
            }
        }

// Sort student records by course name after the search operation
        sortCourse();

    }

    /**
     * Searches for students by semester
     */
    private void searchBySemester() {
        // Initialize loop to allow repeated input if necessary
        miniLoop = true;
        while (miniLoop) {
            // Prompt user to enter the semester
            System.out.print("Enter Semester: ");
            String searchSemester = sc.nextLine().trim().toUpperCase(); // Read input and convert to uppercase
            boolean found = false; // Flag to track if a matching semester is found

            // Validate input - ensure it is not empty
            if (searchSemester.isEmpty()) {
                System.out.println("Choice can't be empty."); // Display error message
                continue; // Restart loop
            }

            // Validate input format - prevent special characters except letters and numbers
            if (checkInput(searchSemester, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) {
                continue; // Restart loop if invalid characters are found
            }

            // Validate input format - prevent spaces in semester code
            if (checkInput(searchSemester, "Can't be enter space.", "[a-zA-Z0-9]+")) {
                continue; // Restart loop if spaces are found
            }

            // Validate input format - ensure it follows the correct semester format (e.g., SP24, SU22, FA20)
            if (checkInput(searchSemester, "Student semester example form (SPxx|SUxx|FAxx), Please enter again.",
                    "^(SP)[0-9]{2,2}|^(SU)[0-9]{2,2}|^(FA)[0-9]{2,2}")) {
                continue; // Restart loop if input does not match valid formats
            }

            // Create a temporary list to store students registered in the specified semester
            List<StudentInfo> searchResults = new ArrayList<>();

            // Iterate through student storage to find matching semester entries
            for (StudentInfo student : studentStorage) {
                if (student.getStudentSemester().equals(searchSemester)) { // Check if semester matches
                    searchResults.add(student); // Add matching student to results list
                    found = true; // Set flag to indicate a match was found
                }
            }

            // If a matching semester is found, display results
            if (found) {
                System.out.println("\n                    SEARCH RESULTS                    ");
                System.out.println("+-----+------------+------------------+----------+--------+");
                System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
                System.out.println("+-----+------------+------------------+----------+--------+");

                // Print each student record found in search results
                for (int i = 0; i < searchResults.size(); i++) {
                    System.out.printf("|%5d", i + 1);
                    System.out.println(searchResults.get(i).getReport()); // Calls getReport() to format student data
                }

                // Print summary table footer
                System.out.println("+-----+------------+------------------+----------+--------+");
                miniLoop = false; // Exit the loop since search is successful

            } else { // If no student found for the specified semester
                System.out.println("No student found in semester: " + searchSemester);
                miniLoop = false; // Exit the loop
            }
        }

// Sort student records by semester after the search operation
        sortSemester();

    }

    /**
     * Provides menu for update and delete operations
     */
    public void updateDelete() {
        // Check if the student storage is empty before proceeding
        if (studentStorage.isEmpty()) {
            System.out.println("No student data available."); // Inform the user that no student records exist
            return; // Exit the method
        }

// Initialize loop to keep displaying the menu until the user chooses to exit
        miniLoop = true;
        while (miniLoop) {
            // Display Update/Delete menu options
            System.out.println("\n----- Update/Delete Menu -----");
            System.out.println("1. Update Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            // Read and trim user input to remove leading/trailing spaces
            input = sc.nextLine().trim();

            // Validate input - ensure the choice is not empty
            if (input.isEmpty()) {
                System.out.println("Choice can't be empty."); // Display error message
                continue; // Restart loop to prompt again
            }

            // Validate input - ensure the choice is between 1 and 3
            if (checkInput(input, "Please enter a number between 1-3.", "[1-3]")) {
                continue; // Restart loop if input is invalid
            }

            // Process user choice using a switch statement
            switch (input) {
                case "1":
                    updateStudent(); // Call method to update student information
                    break;
                case "2":
                    deleteStudent(); // Call method to delete a student record
                    break;
                case "3":
                    miniLoop = false; // Exit the loop and return to the main menu
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
            updateId = sc.nextLine().trim().toUpperCase(); // Convert input to uppercase for consistency

            // Check if the input is empty
            if (updateId.isEmpty()) {
                System.out.println("Student ID cannot be empty.");
                continue; // Restart loop to prompt user again
            }

            // Validate the student ID format
            if (checkInput(updateId, "Can't enter special characters.", "[a-zA-Z0-9 ]+")) {
                continue;
            }
            if (checkInput(updateId, "Spaces are not allowed in the ID.", "[a-zA-Z0-9]+")) {
                continue;
            }
            if (checkInput(updateId, "Student ID format: CExxxxxx (Example: CE123456). Please enter again.", "^(CE)[0-9]{6,6}")) {
                continue;
            }
            break; // Exit loop if validation passes
        }

// Find all student records with the given ID
        List<StudentInfo> updateList = new ArrayList<>();
        for (StudentInfo student : studentStorage) {
            if (student.getStudentID().equalsIgnoreCase(updateId)) { // Case-insensitive comparison
                updateList.add(student);
            }
        }

// Check if any matching records were found
        if (updateList.isEmpty()) {
            System.out.println("No student found with ID: " + updateId);
            return; // Exit if no student is found
        }

// Display current student information in a table format
        System.out.println("\nCurrent Student Information:");
        System.out.println("+-----+------------+------------------+----------+--------+");
        System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
        System.out.println("+-----+------------+------------------+----------+--------+");
        for (int i = 0; i < updateList.size(); i++) {
            System.out.printf("|%5d", i + 1);
            System.out.println(updateList.get(i).getReport());
        }
        System.out.println("+-----+------------+------------------+----------+--------+");

// Loop to manage the update menu
        while (true) {
            // Display update options
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Name");
            System.out.println("2. Semester");
            System.out.println("3. Course");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");

            // Read user input and validate
            String updateChoice = sc.nextLine().trim();
            if (updateChoice.isEmpty() || !updateChoice.matches("[1-4]")) {
                System.out.println("Please enter a number between 1-4.");
                continue; // Restart loop if input is invalid
            }

            // Process the update choice
            switch (updateChoice) {
                case "1":
                    // Update name logic here
                    break;
                case "2":
                    // Update semester logic here
                    break;
                case "3":
                    // Update course logic here
                    break;
                case "4":
                    return; // Exit update process and return to the previous menu
            }

            switch (updateChoice) {
                case "1":
                    // Loop to validate and update the student's name
                    while (true) {
                        System.out.print("Enter new name: ");
                        String newName = sc.nextLine().trim(); // Remove leading/trailing spaces

                        // Check if the input is empty
                        if (newName.isEmpty()) {
                            System.out.println("Name cannot be empty.");
                            continue; // Restart loop to prompt user again
                        }

                        // Validate that the name does not contain special characters or numbers
                        if (checkInput(newName, "Name cannot contain special characters.", "[a-zA-Z0-9 ]+")) {
                            continue;
                        }
                        if (checkInput(newName, "Name cannot contain numbers.", "[a-zA-Z ]+")) {
                            continue;
                        }

                        // Update the name for all student records with the given ID
                        for (StudentInfo student : studentStorage) {
                            if (student.getStudentID().equalsIgnoreCase(updateId)) { // Case-insensitive comparison
                                student.setStudentName(newName); // Update student name
                            }
                        }

                        System.out.println("Name updated successfully!");
                        break; // Exit the loop after successful update
                    }
                    break;

                case "2":
                    // Loop until a valid input is provided
                    while (true) {
                        // Prompt the user to enter the old semester to update
                        System.out.print("Enter semester to update (SPxx|SUxx|FAxx): ");
                        String oldSemester = sc.nextLine().trim().toUpperCase();

                        // Check if the input is empty
                        if (oldSemester.isEmpty()) {
                            System.out.println("Semester cannot be empty.");
                            continue; // Prompt the user to enter again
                        }

                        // Validate the format of the semester (e.g., SP23, SU24, FA25)
                        if (!oldSemester.matches("^(SP|SU|FA)[0-9]{2}$")) {
                            System.out.println("Invalid semester format. Use SPxx, SUxx, or FAxx.");
                            continue;
                        }

                        // Check if the old semester exists in the system
                        boolean oldSemesterExists = false;
                        for (StudentInfo student : studentStorage) {
                            if (student.getStudentSemester().equals(oldSemester)) {
                                oldSemesterExists = true;
                                break; // Exit loop if found
                            }
                        }

                        // If the semester does not exist, prompt the user to enter another semester
                        if (!oldSemesterExists) {
                            System.out.println("Semester " + oldSemester + " does not exist in the system. Please enter another semester.");
                            continue;
                        }

                        // Prompt the user to enter the new semester
                        System.out.print("Enter new semester (SPxx|SUxx|FAxx): ");
                        String newSemester = sc.nextLine().trim().toUpperCase();

                        // Check if the new semester input is empty
                        if (newSemester.isEmpty()) {
                            System.out.println("Semester cannot be empty.");
                            continue;
                        }

                        // Validate the format of the new semester
                        if (!newSemester.matches("^(SP|SU|FA)[0-9]{2}$")) {
                            System.out.println("Invalid semester format. Use SPxx, SUxx, or FAxx.");
                            continue;
                        }

                        // If the old and new semesters are the same, no update is needed
                        if (oldSemester.equals(newSemester)) {
                            System.out.println("New semester is the same as the current semester. No update needed.");
                            continue;
                        }

                        // Check if the new semester already exists in the system
                        boolean newSemesterExists = false;
                        for (StudentInfo student : studentStorage) {
                            if (student.getStudentSemester().equals(newSemester)) {
                                newSemesterExists = true;
                                break;
                            }
                        }

                        // If the new semester already exists, prevent the update
                        if (newSemesterExists) {
                            System.out.println("Cannot update to semester " + newSemester + " as it already exists in the system.");
                            continue;
                        }

                        // Update all student records from the old semester to the new semester
                        for (StudentInfo student : studentStorage) {
                            if (student.getStudentSemester().equals(oldSemester)) {
                                student.setStudentSemester(newSemester);
                            }
                        }
                        System.out.println("All students updated from semester " + oldSemester + " to semester " + newSemester + " successfully!");
                        break; // Exit the loop after a successful update
                    }

                case "3":
                    // Loop until a valid semester and course update is provided
                    while (true) {
                        // Prompt the user to enter the semester to check for course updates
                        System.out.print("Enter semester to check (SPxx|SUxx|FAxx): ");
                        String checkSemester = sc.nextLine().trim().toUpperCase(); // Read input, remove extra spaces, and convert to uppercase

                        // Check if the input is empty
                        if (checkSemester.isEmpty()) {
                            System.out.println("Semester cannot be empty."); // Notify the user
                            continue; // Restart the loop
                        }

                        // Validate the format of the semester (e.g., SP23, SU24, FA25)
                        if (!checkSemester.matches("^(SP|SU|FA)[0-9]{2}$")) {
                            System.out.println("Invalid semester format. Use SPxx, SUxx, or FAxx."); // Notify the user of incorrect format
                            continue; // Restart the loop
                        }

                        // Check if the semester exists in the system
                        boolean semesterExists = false; // Flag to track if semester is found
                        for (StudentInfo student : studentStorage) { // Iterate over the list of students
                            if (student.getStudentSemester().equals(checkSemester)) { // Check if semester matches
                                semesterExists = true; // Set flag to true
                                break; // Exit loop early if found
                            }
                        }

                        // If the semester does not exist, prompt the user to enter another semester
                        if (!semesterExists) {
                            System.out.println("This semester does not exist in the system."); // Notify the user
                            continue; // Restart the loop
                        }

                        // Check if the semester already contains all 3 courses (Java, .Net, C/C++)
                        Set<String> coursesInSemester = new HashSet<>(); // Create a set to store unique courses
                        for (StudentInfo student : studentStorage) { // Iterate through student records
                            if (student.getStudentSemester().equals(checkSemester)) { // If the semester matches
                                coursesInSemester.add(student.getStudentCourse()); // Add the course to the set
                            }
                        }

                        // If the semester already has all 3 courses, return to the menu
                        if (coursesInSemester.size() >= 3) {
                            System.out.println("This semester already has all 3 courses (Java, .Net, C/C++). Cannot update course."); // Notify user
                            break;  // Exit the loop and return to menu
                        }

                        // Create a list of records for the specified semester
                        List<StudentInfo> semesterRecords = new ArrayList<>(); // Initialize an empty list
                        for (StudentInfo student : updateList) { // Iterate through student records
                            if (student.getStudentSemester().equals(checkSemester)) { // If semester matches
                                semesterRecords.add(student); // Add student to the list
                            }
                        }

                        // If no records are found, prompt the user to enter another semester
                        if (semesterRecords.isEmpty()) {
                            System.out.println("No courses found in semester " + checkSemester + " for this student."); // Notify user
                            continue; // Restart the loop
                        }

                        // Display the student records for the selected semester
                        System.out.println("\nCourses in semester " + checkSemester + ":");
                        System.out.println("+-----+------------+------------------+----------+--------+");
                        System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
                        System.out.println("+-----+------------+------------------+----------+--------+");
                        for (int i = 0; i < semesterRecords.size(); i++) { // Iterate through the list of records
                            System.out.printf("|%5d", i + 1); // Print record number
                            System.out.println(semesterRecords.get(i).getReport()); // Print student details
                        }
                        System.out.println("+-----+------------+------------------+----------+--------+");

                        // Prompt the user to select a record to update the course
                        System.out.print("Enter record number to update course (1-" + semesterRecords.size() + "): ");
                        String recordInput = sc.nextLine().trim(); // Read input and remove extra spaces

                        // Validate record input
                        if (recordInput.isEmpty()) {
                            System.out.println("Record number cannot be empty."); // Notify user
                            continue; // Restart the loop
                        }

                        int recordNum; // Variable to store the record index
                        try {
                            recordNum = Integer.parseInt(recordInput) - 1; // Convert input to integer and adjust index
                            if (recordNum < 0 || recordNum >= semesterRecords.size()) { // Check if input is within range
                                System.out.println("Invalid record number."); // Notify user
                                continue; // Restart the loop
                            }
                        } catch (NumberFormatException e) { // Handle invalid number input
                            System.out.println("Please enter a valid number."); // Notify user
                            continue; // Restart the loop
                        }

                        // Prompt the user to enter a new course
                        System.out.print("Enter new course (Java|.Net|C/C++): ");
                        String newCourse = sc.nextLine().trim().toUpperCase(); // Read input and convert to uppercase

                        // Validate course input
                        if (newCourse.isEmpty()) {
                            System.out.println("Course cannot be empty."); // Notify user
                            continue; // Restart the loop
                        }
                        if (!newCourse.matches("^(JAVA)|^([.])(NET)$|^(C[/])(C)([+]{2,2})$")) { // Check if input is a valid course
                            System.out.println("Invalid course. Choose Java, .Net, or C/C++"); // Notify user
                            continue; // Restart the loop
                        }

                        // Get the target student record
                        StudentInfo targetStudent = semesterRecords.get(recordNum); // Retrieve student record

                        // Check if the new course is already registered for this student in the semester
                        boolean courseExists = false; // Flag to track if course is already registered
                        for (StudentInfo student : studentStorage) { // Iterate through student records
                            if (student.getStudentSemester().equals(checkSemester)
                                    && student.getStudentID().equals(targetStudent.getStudentID())
                                    && student.getStudentCourse().equals(newCourse)) { // Check if course already exists
                                courseExists = true; // Set flag to true
                                break; // Exit loop early
                            }
                        }

                        // If the course is already registered, prevent the update
                        if (courseExists) {
                            System.out.println("You have already registered for this course in this semester."); // Notify user
                            continue; // Restart the loop
                        }

                        // Update the course for the selected student record
                        targetStudent.setStudentCourse(newCourse); // Update the course
                        System.out.println("Course updated successfully!"); // Notify user of success
                        break; // Exit loop after a successful update
                    }
                    break; // Exit switch case

                case "4":
                    return; // return menu
            }

            // Display a header message indicating that the student information has been updated
            System.out.println("\nUpdated Student Information:");

// Print the table header with column names
            System.out.println("+-----+------------+------------------+----------+--------+");
            System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
            System.out.println("+-----+------------+------------------+----------+--------+");

// Loop through the updated student list and display each student's details
            for (int i = 0; i < updateList.size(); i++) {
                // Print the record number (starting from 1) with right-aligned formatting
                System.out.printf("|%5d", i + 1);
                // Print the student's information using the getReport() method
                System.out.println(updateList.get(i).getReport());
            }

// Print the table footer to close the display
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
     * This method used to sort ID of employee. Sort in the order of the number
     * behind the CE Using bubble sort.
     */
    public void sortByID() {
        for (int i = 0; i < studentStorage.size() - 1; i++) {
            for (int j = 0; j < studentStorage.size() - i - 1; j++) {
                // Get ID1 and ID2
                String id1 = studentStorage.get(j).getStudentID();
                String id2 = studentStorage.get(j + 1).getStudentID();
                // Cut string and convert to number.
                int num1 = Integer.parseInt(id1.substring(2));  // cut 2 first char ex: CE191249 -> num2 = 191249
                int num2 = Integer.parseInt(id2.substring(2)); // cut 2 first char ex: CE191242 -> num2 = 191242

                // compare if num1 higher than num2 change position.
                if (num1 > num2) {
                    StudentInfo temp = studentStorage.get(j); // Set temp = ID at array[j]
                    studentStorage.set(j, studentStorage.get(j + 1)); // set ID[j] = ID[j+1]
                    studentStorage.set(j + 1, temp); // set ID[j+1] = temp
                }
            }
        }
    }

    /**
     * This method used to sort ID of employee. Sort in the order of the number
     * behind the Semester code FA, SU, SP Using bubble sort.
     */
    public void sortSemester() {
        for (int i = 0; i < studentStorage.size() - 1; i++) {
            for (int j = 0; j < studentStorage.size() - i - 1; j++) {
                // Get ID1 and ID2
                String id1 = studentStorage.get(j).getStudentSemester();
                String id2 = studentStorage.get(j + 1).getStudentSemester();
                // Cut string and convert to number.
                int num1 = Integer.parseInt(id1.substring(2));  // cut 2 first char ex: SP12 -> num1 = 12
                int num2 = Integer.parseInt(id2.substring(2)); // cut 2 first char ex: SP11 -> num2 = 11

                // compare if num1 higher than num2 change position.
                if (num1 > num2) {
                    StudentInfo temp = studentStorage.get(j); // Set temp = ID at array[j]
                    studentStorage.set(j, studentStorage.get(j + 1)); // set ID[j] = ID[j+1]
                    studentStorage.set(j + 1, temp); // set ID[j+1] = temp
                }
            }
        }
    }

    /**
     * Sorts the student list in ascending order based on the first letter of
     * the first name. Uses the Bubble Sort algorithm.
     */
    public void sortCourse() {
        for (int i = 0; i < studentStorage.size() - 1; i++) {
            for (int j = 0; j < studentStorage.size() - 1 - i; j++) {
                // Compare the first letter of two adjacent student names.
                if (studentStorage.get(j).getStudentCourse().charAt(0) > studentStorage.get(j + 1).getStudentCourse().charAt(0)) {
                    // Swap the elements if they are in the wrong order.
                    StudentInfo temp = studentStorage.get(j); //temp == name at j.
                    studentStorage.set(j, studentStorage.get(j + 1)); // At j in array == name at j+1.
                    studentStorage.set(j + 1, temp);  // Name at j+1 == temp.
                }
            }
        }
    }

    /**
     * Sorts the student list in ascending order based on the first letter of
     * the first name. Uses the Bubble Sort algorithm.
     */
    public void sortFirstName() {
        for (int i = 0; i < studentStorage.size() - 1; i++) {
            for (int j = 0; j < studentStorage.size() - 1 - i; j++) {
                // Compare the first letter of two adjacent student names.
                if (studentStorage.get(j).getStudentName().charAt(0) > studentStorage.get(j + 1).getStudentName().charAt(0)) {
                    // Swap the elements if they are in the wrong order.
                    StudentInfo temp = studentStorage.get(j); //temp == name at j.
                    studentStorage.set(j, studentStorage.get(j + 1)); // At j in array == name at j+1.
                    studentStorage.set(j + 1, temp);  // Name at j+1 == temp.
                }
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
