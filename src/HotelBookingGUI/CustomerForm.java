package HotelBookingGUI;

import model.Customer;
import test.HotelManagement;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class CustomerForm extends JFrame {
    private final HotelManagement hotelManagement;
    private DefaultTableModel tableModel;

    public CustomerForm(HotelManagement hotelManagement) {
        this.hotelManagement = hotelManagement;
        setTitle("Quản Lý Khách Hàng");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Title label
        JLabel titleLabel = new JLabel("Quản Lý Khách Hàng", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel for deleting customer
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(new Color(245, 245, 245));

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JLabel deleteLabel = new JLabel("Tên/CMND để xóa:");
        deleteLabel.setFont(labelFont);
        JTextField deleteField = new JTextField();
        deleteField.setFont(fieldFont);
        formPanel.add(deleteLabel);
        formPanel.add(deleteField);

        JButton deleteBtn = new JButton("Xóa Khách Hàng");
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
        deleteBtn.setBackground(new Color(220, 20, 60));
        deleteBtn.setForeground(Color.WHITE);
        formPanel.add(new JLabel("")); // Placeholder
        formPanel.add(deleteBtn);

        // Table to display customers
        String[] columns = {"Tên", "CMND/CCCD", "Số Điện Thoại"};
        tableModel = new DefaultTableModel(columns, 0);
        updateCustomerTable();
        JTable customerTable = new JTable(tableModel);
        customerTable.setFont(new Font("Arial", Font.PLAIN, 14));
        customerTable.setRowHeight(25);
        JScrollPane tableScrollPane = new JScrollPane(customerTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(245, 245, 245));

        // Add panels
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Action listener for deleting customer
        deleteBtn.addActionListener(e -> {
            String identifier = deleteField.getText().trim();
            if (identifier.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hoặc CMND để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (hotelManagement.removeCustomer(identifier)) {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                deleteField.setText("");
                updateCustomerTable();
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng với tên hoặc CMND: " + identifier, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void updateCustomerTable() {
        tableModel.setRowCount(0);
        for (Customer customer : hotelManagement.getCustomers()) {
            tableModel.addRow(new Object[]{customer.getName(), customer.getIdCard(), customer.getPhone()});
        }
    }
}