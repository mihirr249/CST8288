/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;

/**
 *
 * @author mihir
 */

import transferobjects.Recipient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * DAO implementation for Recipient operations using JDBC.
 */
public class RecipientDAOImpl implements RecipientDAO {

    @Override
    public List<Recipient> getAllRecipients() {
        List<Recipient> recipients = new ArrayList<>();
        String sql = "SELECT * FROM Recipients";

        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Recipient r = new Recipient(
                        rs.getInt("AwardID"),
                        rs.getString("Name"),
                        rs.getInt("Year"),
                        rs.getString("City"),
                        rs.getString("Category")
                );
                recipients.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching recipients: " + e.getMessage());
        }
        return recipients;
    }

    @Override
    public void insertRecipient(Recipient r) {
        String sql = "INSERT INTO Recipients (Name, Year, City, Category) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, r.getName());
            pstmt.setInt(2, r.getYear());
            pstmt.setString(3, r.getCity());
            pstmt.setString(4, r.getCategory());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting recipient: " + e.getMessage());
        }
    }

    @Override
    public void deleteRecipientById(int awardID) {
        String sql = "DELETE FROM Recipients WHERE AwardID = ?";

        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, awardID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting recipient: " + e.getMessage());
        }
    }
}