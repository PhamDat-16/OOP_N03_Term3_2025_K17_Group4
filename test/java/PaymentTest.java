package test.java;
import main.java.model.Payment;
import main.java.model.Booking;
import main.java.model.Customer;
import main.java.model.Room;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class PaymentTest {
    private List<Payment> payments = new ArrayList<>();

    public void addPayment(Payment payment) {
        payments.add(payment);
        System.out.println("Da them thanh toan: " + payment);
    }

    public void listPayments() {
        System.out.println("=== DANH SACH THANH TOAN ===");
        for (Payment payment : payments) {
            System.out.println(payment);
        }
    }

    public static void main(String[] args) {
        PaymentTest pt = new PaymentTest();
        Customer c = new Customer(1, "Nguyen Van A", "0123456789", "email@example.com", "Ha Noi", "123456789", "Viet Nam");
        Room r = new Room(101, "Standard", 500000);
        Booking b1 = new Booking(c, r, LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 5), 2);
        Booking b2 = new Booking(c, r, LocalDate.of(2025, 7, 10), LocalDate.of(2025, 7, 12), 1);
        pt.addPayment(new Payment(b1,200000, LocalDate.now(), Payment.PaymentMethod.CASH));
        pt.addPayment(new Payment(b2,1500000, LocalDate.now(), Payment.PaymentMethod.CARD));
        pt.listPayments();
    }
}

    