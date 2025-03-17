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
 * This is the main class that contains the entry point of the application.
 * It initializes the Program class and starts the student management system.
 * The application allows users to create, search, update, and delete student records.
 */
public class CE191249_L02 { // Main class declaration

    /**
     * The main method serves as the entry point for the application.
     * It creates an instance of the Program class and calls its run method
     * to start the student management system.
     * 
     * @param args Command line arguments passed to the application (not used in this program)
     */
    public static void main(String[] args) { // Main method declaration - application entry point
        // run the project
        Program run = new Program(); // Create a new instance of the Program class
        run.run(); // Call the run method to start the student management system
    }

}
