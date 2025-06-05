package HotelBookingGUI;

import model.Booking;
import test.HotelManagement;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class ActiveRoomsForm extends JFrame {
    private final HotelManagement hotelManagement;
    private DefaultTableModel tableModel;

    public ActiveRoomsForm(HotelManagement hotelManagement) {
        this.hotelManagement = hotelManagement;
        setTitle("Phòng Đang Hoạt Động");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Title label
        JLabel titleLabel = new JLabel("Danh Sách Phòng Đang Hoạt Động", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table to display active bookings
        String[] columns = {"Tên Khách Hàng", "Số Phòng", "Loại Phòng"};
        tableModel = new DefaultTableModel(columns, 0);
        updateActiveRoomsTable();
        JTable activeRoomsTable = new JTable(tableModel);
        activeRoomsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        activeRoomsTable.setRowHeight(25);
        JScrollPane tableScrollPane = new JScrollPane(activeRoomsTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add table to main panel
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    public void updateActiveRoomsTable() {
        tableModel.setRowCount(0);
        for (Booking booking : hotelManagement.getBookings()) {
            if (booking.getCustomer() != null) { // Chỉ hiển thị các đặt phòng có khách
                tableModel.addRow(new Object[]{
                        booking.getCustomer().getName(),
                        booking.getRoom().getRoom(),
                        booking.getRoom().getType()
                });
            }
        }
    }
}