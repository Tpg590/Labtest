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
     * Handles student ID input with validation.
     * This method prompts the user to enter a student ID and validates it against
     * the following rules:
     * - ID must follow format CExxxxxx where x is a digit
     * - Cannot be empty
     * - Cannot contain spaces or special characters
     * 
     * The method also checks if the ID already exists in the system. If found,
     * it retrieves the existing student's name and skips the name input step.
     * If not found, it sets studentName to null to indicate a new student.
     * 
     * There's special handling for the first student entry when the storage is empty.
     */
    public void inputStudentID() { // Method to handle student ID input
        // Special handling for the first student entry
        if (studentStorage.isEmpty()) { // Check if this is the first student being added to the system
            // Initialize miniLoop to true for input validation loop
            miniLoop = true; // Set loop control variable to true

            // Start a loop to continuously prompt the user until valid input is received
            while (miniLoop) { // Continue looping until valid input is provided
                System.out.print("Enter ID: "); // Prompt user to enter a student ID
                input = sc.nextLine().trim().toUpperCase(); // Read user input, remove spaces, and convert to uppercase
                stepOneInputID = input; // Store the input temporarily for later use

                // Check if the input is empty
                if (input.isEmpty()) { // If the input string is empty
                    System.out.println("Choice can't be empty."); // Warn the user if input is blank
                    continue; // Restart the loop to get valid input
                }

                // Validate that input does not contain special characters (except letters and numbers)
                if (checkInput(input, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) { // Check for special characters
                    continue; // Restart loop if validation fails
                }

                // Validate that input does not contain spaces
                if (checkInput(input, "Can't be enter space.", "[a-zA-Z0-9]+")) { // Check for spaces
                    continue; // Restart loop if validation fails
                }

                // Validate that the input follows the format "CExxxxxx" (CE followed by 6 digits)
                if (checkInput(input, "Student ID example form (CExxxxxx), Please enter again.", "^(CE)[0-9]{6,6}")) {
                    continue; // Restart loop if validation fails
                }

                // If all validations pass, assign the input to studentID
                studentID = input; // Store the validated ID
                studentName = null; // Reset studentName for a new student ID

                // Exit the loop since a valid student ID is entered
                miniLoop = false; // End the validation loop
            }

            // Return immediately since this is the first student entry
            return; // Exit the method since special handling is complete
        }

        // Normal ID input process (for subsequent students after the first one)
        miniLoop = true; // Initialize loop control variable

        while (miniLoop) { // Loop until valid input is received
            System.out.print("Enter ID: "); // Prompt user to enter a student ID
            input = sc.nextLine().trim().toUpperCase(); // Read input, trim spaces, convert to uppercase
            stepOneInputID = input; // Store the input temporarily for later use

            // Validate input format - Check if input is empty
            if (input.isEmpty()) { // If the input string is empty
                System.out.println("Choice can't be empty."); // Display error message
                continue; // Restart loop to get valid input
            }

            // Ensure input contains only letters and numbers (no special characters)
            if (checkInput(input, "Can't be enter special character.", "[a-zA-Z0-9 ]+")) { // Check for special characters
                continue; // Restart loop if validation fails
            }

            // Ensure input does not contain spaces
            if (checkInput(input, "Can't be enter space.", "[a-zA-Z0-9]+")) { // Check for spaces
                continue; // Restart loop if validation fails
            }

            // Ensure input follows the format "CExxxxxx" (CE followed by 6 digits)
            if (checkInput(input, "Student ID example form (CExxxxxx), Please enter again.", "^(CE)[0-9]{6,6}")) { // Check ID format
                continue; // Restart loop if validation fails
            }

            // Check for existing ID in the student storage
            boolean idExists = false; // Flag to track if the ID is found in storage

            for (StudentInfo studentInfo : studentStorage) { // Loop through all stored students
                if (studentInfo.getStudentID().equals(input)) { // Compare entered ID with stored ones
                    System.out.println("This ID existed so skip step enter name:"); // Notify user that ID exists
                    studentID = input; // Assign ID to the class variable
                    studentName = studentInfo.getStudentName(); // Retrieve existing name from storage
                    idExists = true; // Mark ID as found
                    break; // Exit loop since ID is found
                }
            }

            // If ID is not found, assign input to studentID and reset studentName for new entry
            if (!idExists) { // If the ID was not found in storage
                studentID = input; // Assign the validated ID to the class variable
                studentName = null; // Reset studentName for a new ID (will prompt for name later)
            }

            // Exit the input loop since a valid ID has been entered
            miniLoop = false; // End the validation loop
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
     * Updates an existing student's information in the system.
     * Allows modification of student name, semester, or course details.
     * Includes validation to ensure data integrity:
     * - Name updates affect all records with the same ID
     * - Semester updates check for course limit restrictions
     * - Course updates validate against existing courses and semester limits
     * 
     * @return void - Returns nothing but updates student records in studentStorage
     */
    private void updateStudent() {
        // Declare variable to store student ID for update operations
        String updateId;
        // Start an infinite loop for ID input validation
        while (true) {
            // Prompt user to enter the student ID to update
            System.out.print("Enter Student ID to update: ");
            // Read input, trim whitespace, and convert to uppercase for consistency
            updateId = sc.nextLine().trim().toUpperCase();

            // Check if the entered ID is empty
            if (updateId.isEmpty()) {
                // Display error message if ID is empty
                System.out.println("Student ID cannot be empty.");
                // Skip to next iteration to prompt for input again
                continue;
            }

            // Validate that ID contains only alphanumeric characters
            if (checkInput(updateId, "Can't enter special characters.", "[a-zA-Z0-9 ]+")) {
                // Skip to next iteration if validation fails
                continue;
            }
            // Validate that ID doesn't contain spaces
            if (checkInput(updateId, "Spaces are not allowed in the ID.", "[a-zA-Z0-9]+")) {
                // Skip to next iteration if validation fails
                continue;
            }
            // Validate that ID follows the correct format (CExxxxxx)
            if (checkInput(updateId, "Student ID format: CExxxxxx (Example: CE123456). Please enter again.", "^(CE)[0-9]{6,6}")) {
                // Skip to next iteration if validation fails
                continue;
            }
            // Exit the loop since all validations passed
            break;
        }

        // Create a list to store student records that match the given ID
        List<StudentInfo> updateList = new ArrayList<>();
        // Iterate through all students in storage
        for (StudentInfo student : studentStorage) {
            // Check if current student's ID matches the entered ID (case-insensitive)
            if (student.getStudentID().equalsIgnoreCase(updateId)) {
                // Add matching student to the update list
                updateList.add(student);
            }
        }

        // Check if no matching students were found
        if (updateList.isEmpty()) {
            // Display error message if no students found with the given ID
            System.out.println("No student found with ID: " + updateId);
            // Exit the function if no students to update
            return;
        }

        // Display a header for the table showing current student information
        System.out.println("\nCurrent Student Information:");
        // Print the top border of the table
        System.out.println("+-----+------------+------------------+----------+--------+");
        // Print the table header with column names
        System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
        // Print the header-content separator
        System.out.println("+-----+------------+------------------+----------+--------+");
        // Loop through each student in the update list
        for (int i = 0; i < updateList.size(); i++) {
            // Print the row number with proper formatting
            System.out.printf("|%5d", i + 1);
            // Print the student record details using the getReport method
            System.out.println(updateList.get(i).getReport());
        }
        // Print the bottom border of the table
        System.out.println("+-----+------------+------------------+----------+--------+");

        // Start an infinite loop for the update menu
        while (true) {
            // Display a header for the update options
            System.out.println("\nWhat would you like to update?");
            // Display option 1: Update name
            System.out.println("1. Name");
            // Display option 2: Update semester
            System.out.println("2. Semester");
            // Display option 3: Update course
            System.out.println("3. Course");
            // Display option 4: Return to previous menu
            System.out.println("4. Back");
            // Prompt user to select an option
            System.out.print("Enter your choice: ");

            // Read user's input and remove leading/trailing whitespace
            String updateChoice = sc.nextLine().trim();
            // Validate that input is not empty and is one of the valid options (1-4)
            if (updateChoice.isEmpty() || !updateChoice.matches("[1-4]")) {
                // Display error message for invalid input
                System.out.println("Please enter a number between 1-4.");
                // Skip to next iteration to prompt for input again
                continue;
            }

            // Use switch statement to process different update options
            switch (updateChoice) {
                case "1":
                    // Begin name update process
                    // Start an infinite loop for name validation
                    while (true) {
                        // Prompt user to enter a new name
                        System.out.print("Enter new name: ");
                        // Read input and remove leading/trailing whitespace
                        String newName = sc.nextLine().trim();

                        // Check if the entered name is empty
                        if (newName.isEmpty()) {
                            // Display error message if name is empty
                            System.out.println("Name cannot be empty.");
                            // Skip to next iteration to prompt for input again
                            continue;
                        }

                        // Validate that name contains only alphanumeric characters and spaces
                        if (checkInput(newName, "Name cannot contain special characters.", "[a-zA-Z0-9 ]+")) {
                            // Skip to next iteration if validation fails
                            continue;
                        }

                        // Validate that name contains only letters and spaces (no numbers)
                        if (checkInput(newName, "Name cannot contain numbers.", "[a-zA-Z ]+")) {
                            // Skip to next iteration if validation fails
                            continue;
                        }

                        // Initialize flag to check if new name is same as current name
                        boolean isSameName = false;
                        // Iterate through all students in storage
                        for (StudentInfo student : studentStorage) {
                            // Check if current student has matching ID and same name as new name
                            if (student.getStudentID().equalsIgnoreCase(updateId) && 
                                student.getStudentName().equalsIgnoreCase(newName)) {
                                // Display error message if name is unchanged
                                System.out.println("New name cannot be the same as current name.");
                                // Set flag to indicate same name was found
                                isSameName = true;
                                // Exit the loop early since a match was found
                                break;
                            }
                        }
                        
                        // Check if same name flag was set
                        if (isSameName) {
                            // Skip to next iteration if name is unchanged
                            continue;
                        }

                        // Update the name for all students with matching ID
                        for (StudentInfo student : studentStorage) {
                            // Check if current student has matching ID
                            if (student.getStudentID().equalsIgnoreCase(updateId)) {
                                // Set the new name for the student
                                student.setStudentName(newName);
                            }
                        }

                        // Display success message after updating name
                        System.out.println("Name updated successfully!");
                        // Exit the name update loop
                        break; 
                    }
                    // Exit the case statement for name update
                    break;
                case "2":
                    // Begin semester update process
                    // Start an infinite loop for semester validation
                    while (true) {
                        // Variable to store the semester to be updated
                        String oldSemester;
                        // Start an infinite loop to get valid old semester
                        while (true) {
                            // Prompt user to enter the semester to update
                            System.out.print("Enter semester to update (SPxx|SUxx|FAxx): ");
                            // Read input, trim whitespace, and convert to uppercase
                            oldSemester = sc.nextLine().trim().toUpperCase();
                            // Check if the entered semester is empty
                            if (oldSemester.isEmpty()) {
                                // Display error message if semester is empty
                                System.out.println("Semester cannot be empty.");
                                // Skip to next iteration to prompt for input again
                                continue;
                            }
                            // Validate semester format using regex
                            if (!oldSemester.matches("^(SP|SU|FA)[0-9]{2}$")) {
                                // Display error message for invalid format
                                System.out.println("Invalid semester format. Use SPxx, SUxx, or FAxx.");
                                // Skip to next iteration to prompt for input again
                                continue;
                            }
                            // Exit the loop since all validations passed
                            break;
                        }

                        // Initialize flag to check if old semester exists for this student
                        boolean oldSemesterExists = false;
                        // Iterate through all students in storage
                        for (StudentInfo student : studentStorage) {
                            // Check if current student has matching ID and semester
                            if (student.getStudentSemester().equals(oldSemester) && 
                                student.getStudentID().equals(updateId)) {
                                // Set flag to indicate semester exists
                                oldSemesterExists = true;
                                // Exit the loop early since a match was found
                                break;
                            }
                        }

                        // Check if old semester exists flag was not set
                        if (!oldSemesterExists) {
                            // Display error message if semester doesn't exist
                            System.out.println("Semester " + oldSemester + " does not exist for student ID " + updateId);
                            // Skip to next iteration to prompt for input again
                            continue;
                        }

                        // Variable to store the new semester
                        String newSemester;
                        // Start an infinite loop to get valid new semester
                        while (true) {
                            // Prompt user to enter new semester
                            System.out.print("Enter new semester (SPxx|SUxx|FAxx): ");
                            // Read input, trim whitespace, and convert to uppercase
                            newSemester = sc.nextLine().trim().toUpperCase();
                            // Check if the entered semester is empty
                            if (newSemester.isEmpty()) {
                                // Display error message if semester is empty
                                System.out.println("Semester cannot be empty.");
                                // Skip to next iteration to prompt for input again
                                continue;
                            }
                            // Validate semester format using regex
                            if (!newSemester.matches("^(SP|SU|FA)[0-9]{2}$")) {
                                // Display error message for invalid format
                                System.out.println("Invalid semester format. Use SPxx, SUxx, or FAxx.");
                                // Skip to next iteration to prompt for input again
                                continue;
                            }
                            // Exit the loop since all validations passed
                            break;
                        }

                        // Check if new semester is same as old semester
                        if (oldSemester.equals(newSemester)) {
                            // Display message if no change needed
                            System.out.println("New semester is same as current semester. No update needed.");
                            // Skip to next iteration to prompt for input again
                            continue;
                        }

                        // Initialize flag to check if new semester already exists
                        boolean newSemesterExists = false;
                        // Iterate through all students in storage
                        for (StudentInfo student : studentStorage) {
                            // Check if current student has matching ID and new semester
                            if (student.getStudentSemester().equals(newSemester) && 
                                student.getStudentID().equals(updateId)) {
                                // Display error message if semester already exists
                                System.out.println("Cannot update to semester " + newSemester + " as it already exists for student ID " + updateId);
                                // Set flag to indicate semester exists
                                newSemesterExists = true;
                                // Exit the loop early since a match was found
                                break;
                            }
                        }

                        // Check if new semester exists flag was set
                        if (newSemesterExists) {
                            // Skip to next iteration to prompt for input again
                            continue;
                        }

                        // Update semester for all courses of this student in the old semester
                        for (StudentInfo student : studentStorage) {
                            // Check if current student has matching ID and old semester
                            if (student.getStudentSemester().equals(oldSemester) && 
                                student.getStudentID().equals(updateId)) {
                                // Set the new semester for the student
                                student.setStudentSemester(newSemester);
                            }
                        }
                        // Display success message after updating semester
                        System.out.println("All courses updated from semester " + oldSemester + " to semester " + newSemester + " successfully!");
                        // Exit the semester update loop
                        break;
                    }
                    // Exit the case statement for semester update
                    break;
                case "3":
                    // Begin course update process
                    // Start an infinite loop for course validation
                    while (true) {
                        // Prompt user to enter semester for course update
                        System.out.print("Enter semester to update course (SPxx|SUxx|FAxx): ");
                        // Read input, trim whitespace, and convert to uppercase
                        String updateSemester = sc.nextLine().trim().toUpperCase();

                        // Check if the entered semester is empty
                        if (updateSemester.isEmpty()) {
                            // Display error message if semester is empty
                            System.out.println("Semester cannot be empty.");
                            // Skip to next iteration to prompt for input again
                            continue;
                        }

                        // Validate semester format using regex
                        if (!updateSemester.matches("^(SP|SU|FA)[0-9]{2}$")) {
                            // Display error message for invalid format
                            System.out.println("Invalid semester format. Use SPxx, SUxx, or FAxx.");
                            // Skip to next iteration to prompt for input again
                            continue;
                        }

                        // Initialize flag to check if semester exists for this student
                        boolean semesterExists = false;
                        // Iterate through all students in storage
                        for (StudentInfo student : studentStorage) {
                            // Check if current student has matching ID and semester
                            if (student.getStudentID().equals(updateId) && 
                                student.getStudentSemester().equals(updateSemester)) {
                                // Set flag to indicate semester exists
                                semesterExists = true;
                                // Exit the loop early since a match was found
                                break;
                            }
                        }

                        // Check if semester exists flag was not set
                        if (!semesterExists) {
                            // Display error message if no courses found in semester
                            System.out.println("No courses found in semester " + updateSemester + " for this student.");
                            // Exit the course update loop
                            break;
                        }

                        // Create a set to store unique courses in the semester
                        Set<String> coursesInSemester = new HashSet<>();
                        // Iterate through all students in storage
                        for (StudentInfo student : studentStorage) {
                            // Check if current student has matching ID and semester
                            if (student.getStudentID().equals(updateId) && 
                                student.getStudentSemester().equals(updateSemester)) {
                                // Add course to the set
                                coursesInSemester.add(student.getStudentCourse());
                            }
                        }

                        // Check if semester already has maximum allowed courses (3)
                        if (coursesInSemester.size() >= 3) {
                            // Display error message if semester has max courses
                            System.out.println("Cannot update courses in semester " + updateSemester + 
                                            " as it already has 3 courses.");
                            // Exit the course update loop
                            break;
                        }

                        // Create a list to store student records for the specified semester
                        List<StudentInfo> semesterRecords = new ArrayList<>();
                        // Iterate through all students in storage
                        for (StudentInfo student : studentStorage) {
                            // Check if current student has matching ID and semester
                            if (student.getStudentID().equals(updateId) && 
                                student.getStudentSemester().equals(updateSemester)) {
                                // Add student to semester records list
                                semesterRecords.add(student);
                            }
                        }

                        // Display a header for the semester courses
                        System.out.println("\nCourses in semester " + updateSemester + ":");
                        // Print the top border of the table
                        System.out.println("+-----+------------+------------------+----------+--------+");
                        // Print the table header with column names
                        System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
                        // Print the header-content separator
                        System.out.println("+-----+------------+------------------+----------+--------+");
                        // Loop through each student in the semester records
                        for (int i = 0; i < semesterRecords.size(); i++) {
                            // Print the row number with proper formatting
                            System.out.printf("|%5d", i + 1);
                            // Print the student record details using the getReport method
                            System.out.println(semesterRecords.get(i).getReport());
                        }
                        // Print the bottom border of the table
                        System.out.println("+-----+------------+------------------+----------+--------+");

                        // Prompt user to select a record to update
                        System.out.print("Enter record number to update (1-" + semesterRecords.size() + "): ");
                        // Read input and remove leading/trailing whitespace
                        String recordNum = sc.nextLine().trim();

                        // Check if input is a valid number
                        if (!recordNum.matches("\\d+")) {
                            // Display error message for invalid input
                            System.out.println("Please enter a valid number.");
                            // Skip to next iteration to prompt for input again
                            continue;
                        }

                        // Convert input to integer and adjust for zero-based indexing
                        int recordIndex = Integer.parseInt(recordNum) - 1;
                        // Validate record index is within valid range
                        if (recordIndex < 0 || recordIndex >= semesterRecords.size()) {
                            // Display error message for out-of-range index
                            System.out.println("Invalid record number.");
                            // Skip to next iteration to prompt for input again
                            continue;
                        }

                        // Prompt user to enter a new course
                        System.out.print("Enter new course (JAVA|.NET|C/C++): ");
                        // Read input, trim whitespace, and convert to uppercase
                        String newCourse = sc.nextLine().trim().toUpperCase();

                        // Check if the entered course is empty
                        if (newCourse.isEmpty()) {
                            // Display error message if course is empty
                            System.out.println("Course cannot be empty.");
                            // Skip to next iteration to prompt for input again
                            continue;
                        }

                        // Validate course format using regex
                        if (!newCourse.matches("^(JAVA)|^([.])(NET)$|^(C[/])(C)([+]{2,2})$")) {
                            // Display error message for invalid format
                            System.out.println("Invalid course format. Use JAVA, .NET, or C/C++");
                            // Skip to next iteration to prompt for input again
                            continue;
                        }

                        // Initialize flag to check if new course already exists in the semester
                        boolean courseExists = false;
                        // Iterate through all students in storage
                        for (StudentInfo student : studentStorage) {
                            // Check if current student has matching ID, semester, and course
                            if (student.getStudentID().equals(updateId) && 
                                student.getStudentSemester().equals(updateSemester) && 
                                student.getStudentCourse().equals(newCourse)) {
                                // Set flag to indicate course exists
                                courseExists = true;
                                // Exit the loop early since a match was found
                                break;
                            }
                        }

                        // Check if course exists flag was set
                        if (courseExists) {
                            // Display error message if course already exists
                            System.out.println("Course " + newCourse + " already exists in semester " + updateSemester);
                            // Skip to next iteration to prompt for input again
                            continue;
                        }

                        // Get the selected student record to update
                        StudentInfo selectedRecord = semesterRecords.get(recordIndex);
                        // Iterate through all students in storage
                        for (StudentInfo student : studentStorage) {
                            // Check if current student matches the selected record
                            if (student.getStudentID().equals(updateId) && 
                                student.getStudentSemester().equals(updateSemester) && 
                                student.getStudentCourse().equals(selectedRecord.getStudentCourse())) {
                                // Update the course for the matching student
                                student.setStudentCourse(newCourse);
                                // Exit the loop early since update is complete
                                break;
                            }
                        }

                        // Display success message after updating course
                        System.out.println("Course updated successfully!");
                        // Exit the course update loop
                        break;
                    }
                    // Exit the case statement for course update
                    break;
                case "4":
                    // Return to the previous menu if option 4 is selected
                    return;
            }

            // Display a header message indicating that the student information has been updated
            System.out.println("\nUpdated Student Information:");

            // Print the table header with column names
            System.out.println("+-----+------------+------------------+----------+--------+");
            // Print column names for the result table
            System.out.println("| No. | Student ID |   Student Name   | Semester | Course |");
            // Print the separator line between header and content
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

