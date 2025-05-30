package main.java.model;
import java.time.LocalDate;
public class Payment {
    public enum PaymentMethod {
        CASH, CARD, BANK_TRANSFER, MOMO
    }
    private static int nextId = 1;
    private int paymentId;
    private Booking bookingId;
    private double amount;
    private LocalDate paymentDate;
    private PaymentMethod paymentMethod;

    public Payment(Booking bookingId, double amount, LocalDate paymentDate, PaymentMethod paymentMethod) {
        this.paymentId = nextId++;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    public int getPaymentId() { return paymentId; }
    public Booking getBooking() { return bookingId; }
    public double getAmount() { return amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }

    @Override
    public String toString() {
        return "\n Ma thanh toan: " + paymentId + "\n Ma dat phong: " + bookingId.getBookingId() +
               "\n So tien: " + amount + "\n Ngay thanh toan: " + paymentDate +
               "\n Phuong thuc: " + paymentMethod;
    }
    
}
