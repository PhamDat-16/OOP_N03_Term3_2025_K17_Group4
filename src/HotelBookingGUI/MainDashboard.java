

package HotelBookingGUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainDashboard extends JFrame {
    public MainDashboard() {
        setTitle("Hệ Thống Quản Lý Khách Sạn");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(240, 248, 255)); // Alice blue background

        // Title label
        JLabel titleLabel = new JLabel("Bảng Điều Khiển Quản Lý Khách Sạn", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(25, 25, 112)); // Midnight blue
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Button panel with GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        // Buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        JButton bookingBtn = createStyledButton("Thêm Đặt Phòng", buttonFont);
        JButton customerBtn = createStyledButton("Quản Lý Khách Hàng", buttonFont);
        JButton roomBtn = createStyledButton("Kiểm Tra Phòng", buttonFont);
        JButton activeRoomsBtn = createStyledButton("Phòng Đang Hoạt Động", buttonFont);

        buttonPanel.add(bookingBtn);
        buttonPanel.add(customerBtn);
        buttonPanel.add(roomBtn);
        buttonPanel.add(activeRoomsBtn);

        // Add button panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Tệp");
        fileMenu.setFont(new Font("Arial", Font.PLAIN, 14));
        JMenuItem exitItem = new JMenuItem("Thoát");
        exitItem.setFont(new Font("Arial", Font.PLAIN, 14));
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Khởi tạo HotelManagement
        test.HotelManagement hotelManagement = new test.HotelManagement();

        // Gắn action listener
        bookingBtn.addActionListener(e -> new BookingForm(hotelManagement));
        customerBtn.addActionListener(e -> new CustomerForm(hotelManagement));
        roomBtn.addActionListener(e -> new RoomForm(hotelManagement));
        activeRoomsBtn.addActionListener(e -> new ActiveRoomsForm(hotelManagement));

        add(mainPanel);
        setVisible(true);
    }

    // Helper method to create styled buttons with hover effect
    private JButton createStyledButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(220, 50));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(100, 149, 237)); // Cornflower blue
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        return button;
    }


}