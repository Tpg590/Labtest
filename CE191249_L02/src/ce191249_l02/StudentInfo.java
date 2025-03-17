/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce191249_l02; // Package declaration for organizing related classes

/**
 * L02 - Title: Create a Java console program to manage students.
 *
 * @author Le Thien Tri - CE191249 - Date: 16/03/2025
 * 
 * The StudentInfo class represents a student record in the system.
 * It encapsulates all the relevant information for a student, including:
 * - Student ID (unique identifier)
 * - Student Name
 * - Current Semester
 * - Course enrolled in
 * 
 * This class follows the JavaBean pattern with private fields and public getter/setter methods.
 */
public class StudentInfo { // Declaration of the StudentInfo class

    // Stores the name of the student.
    private String studentName; // Private field to store the student's name

    // Stores the course of the student.
    private String studentCourse; // Private field to store the course the student is taking

    // Stores the semester in which the student is currently enrolled.
    private String studentSemester; // Private field to store the semester the student is enrolled in

    // Stores the unique student ID.
    private String studentID; // Private field to store the student's unique identifier

    /**
     * Default constructor for StudentInfo. Creates an empty student object.
     * All fields will be initialized to null.
     * This constructor is used when a student object needs to be created
     * before its properties are set.
     */
    public StudentInfo() { // Default constructor with no parameters
        // All fields are automatically initialized to null by Java
    }

    /**
     * Parameterized constructor to initialize a student's details.
     * This constructor creates a fully initialized student object with all required information.
     * It is the preferred way to create a new student record with all details available.
     *
     * @param studentID Unique identifier for the student, typically in format CExxxxxx.
     * @param studentName Full name of the student.
     * @param studentSemester Current semester of the student (format: SPxx, SUxx, or FAxx).
     * @param studentCourse Course the student is enrolled in (JAVA, .NET, or C/C++).
     */
    public StudentInfo(String studentID, String studentName, String studentSemester, String studentCourse) { // Parameterized constructor
        this.studentName = studentName; // Initialize the student name with the provided value
        this.studentCourse = studentCourse; // Initialize the student course with the provided value
        this.studentSemester = studentSemester; // Initialize the student semester with the provided value
        this.studentID = studentID; // Initialize the student ID with the provided value
    }

    /**
     * Retrieves the student's name.
     * This getter method provides access to the private studentName field.
     *
     * @return Student's name as a String.
     */
    public String getStudentName() { // Getter method for student name
        return studentName; // Return the value of the student's name
    }

    /**
     * Sets the student's name.
     * This setter method allows modification of the private studentName field.
     *
     * @param studentName The new name to set for the student.
     */
    public void setStudentName(String studentName) { // Setter method for student name
        this.studentName = studentName; // Update the student's name with the provided value
    }

    /**
     * Retrieves the student's course.
     * This getter method provides access to the private studentCourse field.
     *
     * @return Student's course as a String (JAVA, .NET, or C/C++).
     */
    public String getStudentCourse() { // Getter method for student course
        return studentCourse; // Return the value of the student's course
    }

    /**
     * Sets the student's course.
     * This setter method allows modification of the private studentCourse field.
     *
     * @param studentCourse The new course to set for the student (JAVA, .NET, or C/C++).
     */
    public void setStudentCourse(String studentCourse) { // Setter method for student course
        this.studentCourse = studentCourse; // Update the student's course with the provided value
    }

    /**
     * Retrieves the student's semester.
     * This getter method provides access to the private studentSemester field.
     *
     * @return Student's semester as a String (format: SPxx, SUxx, or FAxx).
     */
    public String getStudentSemester() { // Getter method for student semester
        return studentSemester; // Return the value of the student's semester
    }

    /**
     * Sets the student's semester.
     * This setter method allows modification of the private studentSemester field.
     *
     * @param studentSemester The new semester to set for the student (format: SPxx, SUxx, or FAxx).
     */
    public void setStudentSemester(String studentSemester) { // Setter method for student semester
        this.studentSemester = studentSemester; // Update the student's semester with the provided value
    }

    /**
     * Retrieves the student's ID.
     * This getter method provides access to the private studentID field.
     *
     * @return Student's ID as a String (format: CExxxxxx).
     */
    public String getStudentID() { // Getter method for student ID
        return studentID; // Return the value of the student's ID
    }

    /**
     * Sets the student's ID.
     * This setter method allows modification of the private studentID field.
     *
     * @param studentID The new ID to set for the student (format: CExxxxxx).
     */
    public void setStudentID(String studentID) { // Setter method for student ID
        this.studentID = studentID; // Update the student's ID with the provided value
    }

    /**
     * Generates a formatted report containing student details.
     * This method formats the student information into a string suitable
     * for display in a tabular format. It uses fixed-width fields for
     * alignment in the console display.
     *
     * @return A formatted string containing student ID, name, semester, and course,
     *         ready to be displayed in a table row.
     */
    public String getReport() { // Method to generate a formatted report string
        return String.format("|%-12s|%-18s|%10s|%8s|", studentID, studentName, studentSemester, studentCourse); // Format and return student data as a table row
    }

}
