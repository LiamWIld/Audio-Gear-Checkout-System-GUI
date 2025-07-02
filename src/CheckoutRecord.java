/**
 * Liam Wild
 * CEN 3024 - Software Development 1
 * July 2, 2025
 * CheckoutRecord.java
 * This class represents a single gear checkout record with student name, gear item,
 * checkout date, due date, and return status.
 */

public class CheckoutRecord {
    private String studentName;
    private String gearItem;
    private String checkoutDate;
    private String dueDate;
    private String returned;

    /**
     * constructor
     * purpose: initializes a new checkout record
     * parameters: name, gear, checkoutDate, dueDate, returned
     */
    public CheckoutRecord(String studentName, String gearItem, String checkoutDate, String dueDate, String returned) {
        this.studentName = studentName;
        this.gearItem = gearItem;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returned = returned;
    }

    // Getters
    public String getStudentName() { return studentName; }
    public String getGearItem() { return gearItem; }
    public String getCheckoutDate() { return checkoutDate; }
    public String getDueDate() { return dueDate; }
    public String getReturned() { return returned; }

    // Setters
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setGearItem(String gearItem) { this.gearItem = gearItem; }
    public void setCheckoutDate(String checkoutDate) { this.checkoutDate = checkoutDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public void setReturned(String returned) { this.returned = returned; }

    /**
     * method: toString
     * purpose: converts the record to a comma-separated string
     * parameters: none
     * return: String
     */
    @Override
    public String toString() {
        return studentName + "," + gearItem + "," + checkoutDate + "," + dueDate + "," + returned;
    }
}
