
package main.java.main;

import main.java.model.*;
import test.java.BookingTest;
import test.java.PaymentTest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Customer> customers = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        BookingTest bookingTest = new BookingTest();
        PaymentTest paymentTest = new PaymentTest();

        List<Service> serviceCatalog = new ArrayList<>();
        serviceCatalog.add(new Service("Nuoc suoi", 10000));
        serviceCatalog.add(new Service("Snack", 15000));
        serviceCatalog.add(new Service("Giat ui", 30000));
        serviceCatalog.add(new Service("Spa", 50000));
        serviceCatalog.add(new Service("Dua do san bay", 300000, 0));
        serviceCatalog.add(new Service("Dao", 500000));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Them khach hang");
            System.out.println("2. Them phong");
            System.out.println("3. Dat phong");
            System.out.println("4. Danh sach dat phong");
            System.out.println("5. Huy dat phong");
            System.out.println("6. Check-out & Thanh toan");
            System.out.println("7. In hoa don");
            System.out.println("8. Quan ly thanh toan");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Ten khach: ");
                    String name = sc.nextLine();
                    System.out.print("SDT: ");
                    String phone = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Que quan: ");
                    String address = sc.nextLine();
                    System.out.print("CCCD/CMND: ");
                    String idcard = sc.nextLine();
                    System.out.print("Quoc tich: ");
                    String nationality = sc.nextLine();
                    customers.add(new Customer(customers.size() + 1, name, phone,email,address,idcard,nationality ));
                    System.out.println("Da them: " + customers.get(customers.size() - 1));

                    break;

                case 2:
                    System.out.print("So phong: ");
                    int num = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Loai phong: ");
                    String type = sc.nextLine();
                    System.out.print("Gia/dem: ");
                    double price = sc.nextDouble();
                    rooms.add(new Room(num, type, price));
                    break;

                case 3:
                    System.out.print("ID khach: ");
                    int cid = sc.nextInt();
                    Customer c = customers.stream().filter(x -> x.getId() == cid).findFirst().orElse(null);
                    if (c == null) {
                        System.out.println("Khong tim thay khach.");
                        break;
                    }
                    System.out.println("=== DANH SACH PHONG ===");
                    for (Room room : rooms) {
                        boolean available = bookingTest.isRoomAvailable(room);
                        System.out.println("Phong " + room.getRoomNumber() +
                                " | Loai: " + room.getType() +
                                " | Gia: " + room.getPricePerNight() + " VND" +
                                " | Trang thai: " + (available ? "TRONG" : "DA DAT"));
                    }
                    System.out.print("So phong muon dat: ");
                    int rid = sc.nextInt();
                    Room r = rooms.stream().filter(x -> x.getRoomNumber() == rid).findFirst().orElse(null);
                    if (r == null || !bookingTest.isRoomAvailable(r)) {
                        System.out.println("Phong khong ton tai hoac da duoc dat.");
                        break;
                    }
                    sc.nextLine();
                    System.out.print("Check-in (dd/MM/yyyy): ");
                    LocalDate checkIn = LocalDate.parse(sc.nextLine(), dtf);
                    System.out.print("Check-out (dd/MM/yyyy): ");
                    LocalDate checkOut = LocalDate.parse(sc.nextLine(), dtf);
                    if (!bookingTest.isRoomAvailable(r, checkIn, checkOut)) {
                        System.out.println("Phong da duoc dat trong khoang thoi gian nay.");
                        break;
                    }

                    System.out.print("So khach o trong phong "+ r.getRoomNumber() + ": ");
                    int numberOfGuests = sc.nextInt();
                    sc.nextLine();

                    bookingTest.createBooking(c, r, checkIn, checkOut , numberOfGuests);

                    Booking latestBooking = bookingTest.findLatestBookingByCustomer(c.getId());
                    if (latestBooking != null) {
                        System.out.print("Ban co muon them dich vu? (y/n): ");
                        String addServiceChoice = sc.nextLine();
                        while (addServiceChoice.equalsIgnoreCase("y")) {
                            System.out.println("\n--- Danh sach dich vu ---");
                            for (int i = 0; i < serviceCatalog.size(); i++) {
                                Service s = serviceCatalog.get(i);
                                System.out.println((i + 1) + ". " + s.getServiceName() + " - " + s.getPrice() + " VND");
                            }

                            System.out.print("Chon dich vu (so): ");
                            int serviceIndex = sc.nextInt() - 1;
                            sc.nextLine();
                            if (serviceIndex >= 0 && serviceIndex < serviceCatalog.size()) {
                                Service selectedService = serviceCatalog.get(serviceIndex);
                                System.out.print("Nhap so luong: ");
                                int quantity = sc.nextInt();
                                sc.nextLine();
                                latestBooking.addService(new Service(
                                    selectedService.getServiceName(),
                                    selectedService.getPrice(),
                                    quantity
                                ));

                                System.out.println("Da them dich vu: " + selectedService.getServiceName());

                                System.out.print("Ban co muon them tiep dich vu khac? (y/n): ");
                                addServiceChoice = sc.nextLine();
                            }else {
                                System.out.println("Lua chon khong hop le.");
                            }
                        }     
                    }
                    break;
                //public void createBooking(Customer customerId, Room roomId, LocalDate checkIn, LocalDate checkOut , int numberOfGuests) {
                case 4:
                    bookingTest.listBookings(); 
                    break;
                case 5:
                    System.out.print("Booking ID can huy: ");

                    bookingTest.cancelBooking(sc.nextInt()); 
                    break;
                case 6:
                    System.out.print("Booking ID check-out: ");
                    // bookingTest.checkOut(sc.nextInt());
                    int bookingId = sc.nextInt();
                    sc.nextLine();
                    boolean checkOutResult = bookingTest.checkOut(bookingId);
                    if (checkOutResult) {
                        Booking booking = bookingTest.findBookingById(bookingId);
                        if (booking == null) {
                            System.out.println("Khong tim thay booking.");
                            break;
                        }
                        // Tạo thanh toán
                        System.out.println("Tao thanh toan cho booking ID " + bookingId);
                        double amount = booking.calculateTotal();
                        System.out.println("Tong tien thanh toan (gom phong và dich vu): " + amount + " VND");
                        System.out.println("Chon phuong thuc thanh toan:");
                        for (Payment.PaymentMethod pm : Payment.PaymentMethod.values()) {
                            System.out.println(pm.ordinal() + 1 + ". " + pm);
                        }
                        System.out.print("Lua chon: ");
                        int pmChoice = sc.nextInt();
                        sc.nextLine();
                        Payment.PaymentMethod method;
                        try {
                            method = Payment.PaymentMethod.values()[pmChoice - 1];
                        } catch (Exception e) {
                            System.out.println("Lua chon phuong thuc khong hop le. Mac dinh la CASH.");
                            method = Payment.PaymentMethod.CASH;
                        }

                        Payment payment = new Payment(booking, amount, LocalDate.now(), method);
                        PaymentTest pt = new PaymentTest();
                        pt.addPayment(payment);
                    } else {
                        System.out.println("Check-out khong thanh cong.");
                    } 
                    break;
                case 7:
                    System.out.print("Booking ID in hoa don: ");
                    bookingTest.printInvoice(sc.nextInt()); 
                    break;
                case 8:
                    // Quan ly thanh toan - menu con
                    while (true) {
                        System.out.println("\n--- QUAN LY THANH TOAN ---");
                        System.out.println("1. Xem danh sach thanh toan");
                        System.out.println("0. Tro ve menu chinh");
                        System.out.print("Chon: ");
                        int pmChoice2 = sc.nextInt();
                        sc.nextLine();
                        if (pmChoice2 == 1) {
                            PaymentTest PaymentTest = new PaymentTest();
                            PaymentTest.listPayments();
                        } else if (pmChoice2 == 0) {
                            break;
                        } else {
                            System.out.println("Lua chon khong hop le.");
                        }
                    }
                    break;
                case 0:
                    System.out.println("Tam biet!"); 
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }
}

 