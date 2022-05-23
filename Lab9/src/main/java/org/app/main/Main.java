package org.app.main;

import org.app.connection.DBConnection;
import org.app.gui.MainWindow;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            DBConnection.getConnection();
        } catch (Exception e){
            System.out.println("Error connecting to database.");
            return;
        }

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow window = new MainWindow();
                window.createAndShowGUI();
            }
        });

        /*try {
            DBConnection.connection().close();
        } catch (SQLException e) {
            System.out.println("Connection close error.");
        }*/
    }
}
