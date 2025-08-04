package businesslayer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
import dataaccesslayer.RecipientDAO;
import dataaccesslayer.RecipientDAOImpl;
import transferobjects.Recipient;

import java.sql.*;
import java.util.List;
/**
 *
 * @author mihir
 */
/**
 * Main application to demonstrate DAO and Singleton patterns.
 * Displays metadata, retrieves all recipients, inserts a new one, deletes it, and displays the final state.
 */
public class OntarioApp {

    public static void main(String[] args) {
        RecipientDAO dao = new RecipientDAOImpl();
        printMetadata();

        System.out.println("\nAll Recipients:");
        printRecipients(dao.getAllRecipients());

        Recipient newRecipient = new Recipient(0, "Mistry; Mihir", 2025, "Ottawa", "Computer Programming");
        dao.insertRecipient(newRecipient);
        System.out.println("\nAfter Inserting New Recipient:");
        printRecipients(dao.getAllRecipients());

        int lastID = getLastAwardID();
        dao.deleteRecipientById(lastID);
        System.out.println("\nAfter Deleting Newly Inserted Recipient:");
        printRecipients(dao.getAllRecipients());

        // Footer
        System.out.println("\nProgram by: Mihir Mistry (041105676)");
        System.out.println("For: 25S CST8288 Section 01X Assignment 1");
    }

    private static void printMetadata() {
        String sql = "SELECT * FROM Recipients LIMIT 1";
        try (Connection conn = dataaccesslayer.DataSource.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            System.out.println("Recipients Table - Column Attributes:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-12s %-15s %-30s%n",
                        rsmd.getColumnName(i),
                        rsmd.getColumnTypeName(i),
                        rsmd.getColumnClassName(i));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching metadata: " + e.getMessage());
        }
    }

    private static void printRecipients(List<Recipient> recipients) {
        System.out.printf("%-8s %-30s %-6s %-10s %-30s%n", "AwardID", "Name", "Year", "City", "Category");
        System.out.println("-------------------------------------------------------------------------------");
        for (Recipient r : recipients) {
            System.out.printf("%-8d %-30s %-6d %-10s %-30s%n",
                    r.getAwardID(), r.getName(), r.getYear(), r.getCity(), r.getCategory());
        }
    }

    private static int getLastAwardID() {
        String sql = "SELECT MAX(AwardID) FROM Recipients";
        try (Connection conn = dataaccesslayer.DataSource.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving last AwardID: " + e.getMessage());
        }
        return -1;
    }
}
