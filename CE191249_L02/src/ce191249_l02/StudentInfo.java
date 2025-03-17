/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce191249_l02;

/**
 * L02 - Title: Create a Java console program to manage students.
 *
 * @author Le Thien Tri - CE191249 - Date: 16/03/2025
 */
public class StudentInfo {

    // Stores the name of the student.
    private String studentName;

    // Stores the course of the student.
    private String studentCourse;

    // Stores the semester in which the student is currently enrolled.
    private String studentSemester;

    // Stores the unique student ID.
    private String studentID;

    /**
     * Default constructor for StudentInfo. Creates an empty student object.
     */
    public StudentInfo() {
    }

    /**
     * Parameterized constructor to initialize a student's details.
     *
     * @param studentID Unique identifier for the student.
     * @param studentName Name of the student.
     * @param studentSemester Current semester of the student.
     * @param studentCourse Course the student is enrolled in.
     */
    public StudentInfo(String studentID, String studentName, String studentSemester, String studentCourse) {
        this.studentName = studentName;
        this.studentCourse = studentCourse;
        this.studentSemester = studentSemester;
        this.studentID = studentID;
    }

    /**
     * Retrieves the student's name.
     *
     * @return Student's name.
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Sets the student's name.
     *
     * @param studentName Name to set.
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Retrieves the student's course.
     *
     * @return Student's course.
     */
    public String getStudentCourse() {
        return studentCourse;
    }

    /**
     * Sets the student's course.
     *
     * @param studentCourse Course to set.
     */
    public void setStudentCourse(String studentCourse) {
        this.studentCourse = studentCourse;
    }

    /**
     * Retrieves the student's semester.
     *
     * @return Student's semester.
     */
    public String getStudentSemester() {
        return studentSemester;
    }

    /**
     * Sets the student's semester.
     *
     * @param studentSemester Semester to set.
     */
    public void setStudentSemester(String studentSemester) {
        this.studentSemester = studentSemester;
    }

    /**
     * Retrieves the student's ID.
     *
     * @return Student's ID.
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Sets the student's ID.
     *
     * @param studentID ID to set.
     */
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    /**
     * Generates a formatted report containing student details.
     *
     * @return A formatted string containing student ID, name, semester, and
     * course.
     */
    public String getReport() {
        return String.format("|%-12s|%-18s|%10s|%8s|", studentID, studentName, studentSemester, studentCourse);
    }

}
