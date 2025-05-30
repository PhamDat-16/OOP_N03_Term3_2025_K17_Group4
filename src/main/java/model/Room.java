package main.java.model;

public class Room {
private String roomId;
private int roomNumber;
private String roomType;
private double pricePerNight;
private boolean isAvailable;
private String roomStatus;
public Room(String roomId ,int roomNumber, String roomType, double pricePerNight , boolean isAvalable , String roomStatus) {
this.roomId = roomId;
this.roomNumber = roomNumber;
this.roomType = roomType;
this.pricePerNight = pricePerNight;
this.isAvailable = isAvalable;
this.roomStatus = roomStatus;
}
public Room(int roomNumber, String roomType, double pricePerNight) {
this.roomId = "RM" + roomNumber;
this.roomNumber = roomNumber;
this.roomType = roomType;
this.pricePerNight = pricePerNight;
this.isAvailable = true;
this.roomStatus = "Available";
}
public String getRoomId(){ return roomId;}
public int getRoomNumber() { return roomNumber; }
public String getType() { return roomType; }
public double getPricePerNight() { return pricePerNight; }
public boolean isAvailable() { return isAvailable; }
public String getRoomStatus() { return roomStatus;}
public void setAvailable(boolean available) { this.isAvailable = available; }
public void setRoomStatus(String roomStatus) { this.roomStatus = roomStatus;}
@Override
public String toString() {
return "Phong " + roomNumber + " [" + roomType + "] - " + pricePerNight + "d/dem - "
+ (isAvailable ? "Trong" : "Khong san sang") + "\n"
+ "Trang thai: " + roomStatus;
}
}