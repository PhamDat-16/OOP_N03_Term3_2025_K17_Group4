package test.java;
import main.java.model.Room;
public class RoomTest {
public static void main(String[] args) {
Room room1 = new Room(101, "Phong don", 500000);
Room room2 = new Room(102, "Phong doi", 800000);
System.out.println("== Kiem thu them phong ==");
System.out.println(room1);
System.out.println(room2);
System.out.println("\nTrang thai ban dau cua phong 101:");
System.out.println("Co san?: " + room1.isAvailable());
System.out.println("Trang thai: " + room1.getRoomStatus());
room1.setAvailable(false);
room1.setRoomStatus("Da dat");
System.out.println("\nSau khi dat phong 101:");
System.out.println(room1);
room1.setAvailable(true);
room1.setRoomStatus("Available");
System.out.println("\nSau khi tra phong 101:");
System.out.println(room1);
}
}