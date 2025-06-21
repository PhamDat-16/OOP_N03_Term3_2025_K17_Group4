import HotelBookingGUI.MainDashboard;

import javax.swing.*;
import test.RoomTest;
import test.CustomerTest;
import test.BookingTest;
import test.HotelManagement;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // new MainDashboard();
         CustomerTest.TestCustomer();
//
            //           BookingTest.TestBooking();
//         RoomTest.TestRoom();

       });




    }
}