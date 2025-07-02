/**
 * Liam Wild
 * CEN 3024 - Software Development 1
 * July 2, 2025
 * CheckoutManager.java
 * This class manages all gear checkout records. It provides methods for adding,
 * removing, loading, updating due dates, and listing overdue gear.
 */

import java.io.*;
import java.util.*;

public class CheckoutManager {
    private List<CheckoutRecord> records = new ArrayList<>();

    /**
     * method: addRecord
     * purpose: adds a new record if the student/gear combination does not already exist
     * parameters: CheckoutRecord record
     * return: boolean – true if added, false if duplicate
     */
    public boolean addRecord(CheckoutRecord record) {
        for (CheckoutRecord r : records) {
            if (r.getStudentName().equalsIgnoreCase(record.getStudentName()) &&
                    r.getGearItem().equalsIgnoreCase(record.getGearItem())) {
                return false;
            }
        }
        records.add(record);
        return true;
    }

    /**
     * method: removeRecord
     * purpose: removes all records by student name (case-insensitive)
     * parameters: String name
     * return: boolean – true if any record was removed
     */
    public boolean removeRecord(String name) {
        return records.removeIf(r -> r.getStudentName().equalsIgnoreCase(name));
    }

    /**
     * method: updateDueDate
     * purpose: updates due date for a student’s record
     * parameters: String name, String newDueDate
     * return: boolean – true if update succeeded
     */
    public boolean updateDueDate(String name, String newDueDate) {
        for (CheckoutRecord r : records) {
            if (r.getStudentName().equalsIgnoreCase(name)) {
                r.setDueDate(newDueDate);
                return true;
            }
        }
        return false;
    }

    /**
     * method: loadFromFile
     * purpose: reads a file and loads records
     * parameters: String filename
     * return: boolean – true if file loaded successfully
     */
    public boolean loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    CheckoutRecord r = new CheckoutRecord(
                            parts[0].trim(), parts[1].trim(),
                            parts[2].trim(), parts[3].trim(), parts[4].trim()
                    );
                    addRecord(r);
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * method: listOverdueGear
     * purpose: returns overdue records before given date
     * parameters: String today (format YYYY-MM-DD)
     * return: String – formatted overdue records or notice
     */
    public String listOverdueGear(String today) {
        StringBuilder result = new StringBuilder();
        for (CheckoutRecord r : records) {
            if (r.getDueDate().compareTo(today) < 0) {
                result.append(r.toString()).append("\n");
            }
        }
        return result.isEmpty() ? "No overdue gear found." : result.toString();
    }

    /**
     * method: getRecords
     * purpose: returns the full list of records
     * parameters: none
     * return: List<CheckoutRecord>
     */
    public List<CheckoutRecord> getRecords() {
        return records;
    }
}
