package HotelBookingGUI;

import model.Room;
import test.HotelManagement;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class RoomForm extends JFrame {
    private final HotelManagement hotelManagement;
    private DefaultTableModel tableModel;

    public RoomForm(HotelManagement hotelManagement) {
        this.hotelManagement = hotelManagement;
        setTitle("Kiểm Tra Phòng");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Title label
        JLabel titleLabel = new JLabel("Danh Sách Phòng", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table to display rooms
        String[] columns = {"Số Phòng", "Loại Phòng", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0);
        updateRoomTable();
        JTable roomTable = new JTable(tableModel);
        roomTable.setFont(new Font("Arial", Font.PLAIN, 14));
        roomTable.setRowHeight(25);
        JScrollPane tableScrollPane = new JScrollPane(roomTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add table to main panel
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    public void updateRoomTable() {
        tableModel.setRowCount(0);
        for (Room room : hotelManagement.getRooms()) {
            tableModel.addRow(new Object[]{room.getRoom(), room.getType(), room.isAvailable() ? "Trống" : "Đã đặt"});
        }
    }
}