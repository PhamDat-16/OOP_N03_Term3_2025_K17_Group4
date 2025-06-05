package HotelBookingGUI;

import model.Booking;
import model.Customer;
import model.Room;
import test.HotelManagement;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class BookingForm extends JFrame {
    private final HotelManagement hotelManagement;

    public BookingForm(HotelManagement hotelManagement) {
        this.hotelManagement = hotelManagement;
        setTitle("Thêm Đặt Phòng");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Title label
        JLabel titleLabel = new JLabel("Form Thêm Đặt Phòng", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(new Color(245, 245, 245));

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JLabel nameLabel = new JLabel("Tên Khách Hàng:");
        nameLabel.setFont(labelFont);
        JTextField nameField = new JTextField();
        nameField.setFont(fieldFont);
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        JLabel idLabel = new JLabel("CMND/CCCD:");
        idLabel.setFont(labelFont);
        JTextField idField = new JTextField();
        idField.setFont(fieldFont);
        formPanel.add(idLabel);
        formPanel.add(idField);

        JLabel phoneLabel = new JLabel("Số Điện Thoại:");
        phoneLabel.setFont(labelFont);
        JTextField phoneField = new JTextField();
        phoneField.setFont(fieldFont);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        JLabel roomLabel = new JLabel("Mã Phòng:");
        roomLabel.setFont(labelFont);
        JTextField roomField = new JTextField();
        roomField.setFont(fieldFont);
        formPanel.add(roomLabel);
        formPanel.add(roomField);

        JLabel checkinLabel = new JLabel("Ngày Đến (YYYY-MM-DD):");
        checkinLabel.setFont(labelFont);
        JTextField checkinField = new JTextField();
        checkinField.setFont(fieldFont);
        formPanel.add(checkinLabel);
        formPanel.add(checkinField);

        JLabel checkoutLabel = new JLabel("Ngày Đi (YYYY-MM-DD):");
        checkoutLabel.setFont(labelFont);
        JTextField checkoutField = new JTextField();
        checkoutField.setFont(fieldFont);
        formPanel.add(checkoutLabel);
        formPanel.add(checkoutField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton bookBtn = new JButton("Đặt Phòng");
        bookBtn.setFont(new Font("Arial", Font.BOLD, 14));
        bookBtn.setBackground(new Color(100, 149, 237));
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setPreferredSize(new Dimension(120, 35));
        buttonPanel.add(bookBtn);

        // Add form panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Action listener for booking
        bookBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String idCard = idField.getText().trim();
            String phone = phoneField.getText().trim();
            String roomId = roomField.getText().trim();
            String checkinDate = checkinField.getText().trim();
            String checkoutDate = checkoutField.getText().trim();

            if (name.isEmpty() || idCard.isEmpty() || phone.isEmpty() || roomId.isEmpty() || checkinDate.isEmpty() || checkoutDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!idCard.matches("\\d{9,12}")) {
                JOptionPane.showMessageDialog(this, "CMND/CCCD phải là số và có 9-12 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!phone.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải là số và có 10-11 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int roomNumber;
            try {
                roomNumber = Integer.parseInt(roomId);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Mã phòng phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!checkinDate.matches("\\d{4}-\\d{2}-\\d{2}") || !checkoutDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Ngày phải có định dạng YYYY-MM-DD!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra khách hàng
            Customer customer = hotelManagement.getCustomers().stream()
                    .filter(c -> c.getIdCard().equals(idCard))
                    .findFirst()
                    .orElse(null);

            if (customer == null) {
                customer = new Customer(name, idCard, phone);
                hotelManagement.addCustomer(customer);
            }

            // Kiểm tra phòng
            Room room = hotelManagement.getRooms().stream()
                    .filter(r -> r.getRoom() == roomNumber)
                    .findFirst()
                    .orElse(null);

            if (room == null) {
                JOptionPane.showMessageDialog(this, "Phòng " + roomNumber + " không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!room.isAvailable()) {
                JOptionPane.showMessageDialog(this, "Phòng " + roomNumber + " đã được đặt!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra ngày
            try {
                LocalDate checkIn = LocalDate.parse(checkinDate);
                LocalDate checkOut = LocalDate.parse(checkoutDate);
                if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
                    JOptionPane.showMessageDialog(this, "Ngày check-out phải sau ngày check-in!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Thêm đặt phòng
                Booking booking = new Booking(customer, room, checkIn, checkOut);
                if (hotelManagement.addBooking(booking)) {
                    JOptionPane.showMessageDialog(this, "Đặt phòng thành công!");
                    nameField.setText("");
                    idField.setText("");
                    phoneField.setText("");
                    roomField.setText("");
                    checkinField.setText("");
                    checkoutField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi đặt phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}