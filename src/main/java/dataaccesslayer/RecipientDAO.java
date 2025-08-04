/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;

import java.util.List;
import transferobjects.Recipient;

/**
 *
 * @author mihir
 */
/**
 * Interface defining DAO operations for Recipient.
 */
public interface RecipientDAO {
    List<Recipient> getAllRecipients();
    void insertRecipient(Recipient recipient);
    void deleteRecipientById(int awardID);
}