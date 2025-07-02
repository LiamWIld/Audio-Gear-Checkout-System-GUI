/**
 * Liam Wild
 * CEN 3024 - Software Development 1
 * July 2, 2025
 * CheckoutGUI.java
 * This class creates a Java Swing GUI to manage gear checkout records.
 * It supports adding, deleting, loading, and listing overdue gear with input validation.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class CheckoutGUI {
    private JFrame frame;
    private JTextField nameField, gearField, checkoutField, dueField, returnedField;
    private DefaultTableModel tableModel;
    private JTable table;
    private CheckoutManager manager = new CheckoutManager();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CheckoutGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Audio Gear Checkout System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(null);

        addFormComponents();
        addTable();
        addButtons();

        frame.setVisible(true);
    }

    private void addFormComponents() {
        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setBounds(20, 20, 100, 25);
        frame.add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(130, 20, 200, 25);
        frame.add(nameField);

        JLabel gearLabel = new JLabel("Gear Item:");
        gearLabel.setBounds(20, 60, 100, 25);
        frame.add(gearLabel);
        gearField = new JTextField();
        gearField.setBounds(130, 60, 200, 25);
        frame.add(gearField);

        JLabel checkoutLabel = new JLabel("Checkout Date:");
        checkoutLabel.setBounds(20, 100, 100, 25);
        frame.add(checkoutLabel);
        checkoutField = new JTextField();
        checkoutField.setBounds(130, 100, 200, 25);
        frame.add(checkoutField);

        JLabel dueLabel = new JLabel("Due Date:");
        dueLabel.setBounds(20, 140, 100, 25);
        frame.add(dueLabel);
        dueField = new JTextField();
        dueField.setBounds(130, 140, 200, 25);
        frame.add(dueField);

        JLabel returnedLabel = new JLabel("Returned?");
        returnedLabel.setBounds(20, 180, 100, 25);
        frame.add(returnedLabel);
        returnedField = new JTextField();
        returnedField.setBounds(130, 180, 200, 25);
        frame.add(returnedField);
    }

    private void addTable() {
        String[] columns = {"Name", "Gear Item", "Checkout Date", "Due Date", "Returned?"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(350, 20, 500, 300);
        frame.add(scrollPane);
    }

    private void addButtons() {
        JButton addButton = new JButton("Add");
        addButton.setBounds(20, 230, 90, 30);
        frame.add(addButton);
        addButton.addActionListener(e -> addRecord());

        JButton checkOverdueButton = new JButton("Check Overdue Gear");
        checkOverdueButton.setBounds(120, 230, 200, 30);
        frame.add(checkOverdueButton);
        checkOverdueButton.addActionListener(e -> checkOverdue());

        JButton loadButton = new JButton("Load from File");
        loadButton.setBounds(20, 270, 140, 30);
        frame.add(loadButton);
        loadButton.addActionListener(e -> loadFromFile());

        JButton deleteButton = new JButton("DELETE");
        deleteButton.setBounds(180, 270, 90, 30);
        frame.add(deleteButton);
        deleteButton.addActionListener(e -> deleteRecord());
    }

    private void addRecord() {
        String name = nameField.getText().trim();
        String gear = gearField.getText().trim();
        String checkout = checkoutField.getText().trim();
        String due = dueField.getText().trim();
        String returned = returnedField.getText().trim();

        // Input validation
        if (!name.matches("[a-zA-Z ]+") || !gear.matches("[a-zA-Z0-9]+") ||
                !isValidDate(checkout) || !isValidDate(due) ||
                !(returned.equalsIgnoreCase("yes") || returned.equalsIgnoreCase("no"))) {
            JOptionPane.showMessageDialog(frame, "Invalid input.\n- Name: letters and spaces only\n- Gear: alphanumeric\n- Dates: YYYY-MM-DD\n- Returned: yes or no");
            return;
        }

        CheckoutRecord r = new CheckoutRecord(name, gear, checkout, due, returned);
        if (!manager.addRecord(r)) {
            JOptionPane.showMessageDialog(frame, "Duplicate entry. Not added.");
            return;
        }

        tableModel.addRow(new Object[]{name, gear, checkout, due, returned});
        clearFields();
    }

    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String name = (String) tableModel.getValueAt(selectedRow, 0);
            manager.removeRecord(name);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
        }
    }

    private void checkOverdue() {
        String today = JOptionPane.showInputDialog(frame, "Enter today's date (YYYY-MM-DD):");
        if (!isValidDate(today)) {
            JOptionPane.showMessageDialog(frame, "Invalid date format. Use YYYY-MM-DD.");
            return;
        }
        String result = manager.listOverdueGear(today);
        JOptionPane.showMessageDialog(frame, result);
    }

    private void loadFromFile() {
        String path = JOptionPane.showInputDialog(frame, "Enter path to text file:");
        if (path == null || path.isEmpty()) return;

        if (!manager.loadFromFile(path)) {
            JOptionPane.showMessageDialog(frame, "File not found or failed to load.");
        } else {
            tableModel.setRowCount(0);
            for (CheckoutRecord r : manager.getRecords()) {
                tableModel.addRow(new Object[]{
                        r.getStudentName(),
                        r.getGearItem(),
                        r.getCheckoutDate(),
                        r.getDueDate(),
                        r.getReturned()
                });
            }
        }
    }

    private boolean isValidDate(String date) {
        return date != null && date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private void clearFields() {
        nameField.setText("");
        gearField.setText("");
        checkoutField.setText("");
        dueField.setText("");
        returnedField.setText("");
    }
}
