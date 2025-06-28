

# 💻 **Chương trình Quản lý Khách sạn**

## 📖 **Mô tả**
**Chương trình quản lý khách sạn** là một ứng dụng web được xây dựng bằng Java Spring Boot, được thiết kế để hỗ trợ quản lý các hoạt động của một khách sạn. Ứng dụng cung cấp các tính năng như **quản lý phòng**, **quản lý khách hàng**, **đặt và kiểm tra trạng thái phòng**, cũng như **hiển thị báo cáo** về các phòng đang hoạt động. Dữ liệu được lưu trữ trong **cơ sở dữ liệu MySQL**, thay vì file nhị phân như yêu cầu ban đầu, để đảm bảo **tính ổn định và mở rộng**.

## 📌 **Giao diện Người dùng (UI)**
- **Giao diện Quản lý Phòng**: Cho phép thêm thông tin đặt phòng (**tên khách hàng**, **CMND**, **số điện thoại**, **số phòng**, **ngày nhận**, **ngày trả**).
- **Giao diện Quản lý Khách hàng**: Hiển thị danh sách khách hàng, hỗ trợ **xóa** và **cập nhật** thông tin khách hàng.
- **Giao diện Kiểm tra Phòng**: Hiển thị **số phòng**, **loại phòng**, và **trạng thái** (trống/đã đặt).
- **Giao diện Báo cáo**: Hiển thị danh sách các phòng đang hoạt động với thông tin **tên khách hàng**, **số phòng**, và **loại phòng**.

## 👥 **Thành viên Nhóm**
1. Trần Văn Nhật
2. Nguyễn Duy Bảo
3. Phạm Văn Đạt
4. Nguyễn Lê Thu

## 🚀 **Công nghệ Sử dụng**
- **Ngôn ngữ**: Java
- **Framework**: Spring Boot
- **Cơ sở dữ liệu**: MySQL
- **Kết nối DB**: JDBC (qua Spring Data JPA)
- **Giao diện**: Thymeleaf (tích hợp với Spring Boot)

## 📋 **Hướng dẫn Sử dụng**
### 1. **Khởi động Chương trình**
- Mở terminal hoặc IDE, điều hướng đến thư mục dự án (`D:\hotelbooking`).
- Chạy file `HotelbookingApplication.java` để khởi động ứng dụng:
  ```bash
  mvn spring-boot:run
  ```
- Ứng dụng sẽ chạy trên **cổng 8081** (hoặc **8080** nếu cổng 8081 đã được thay đổi trong `application.properties`).

### 2. **Giao diện Chính**
- **📦 Thêm Đặt Phòng**: Nhấn nút "Thêm Đặt Phòng" để mở form nhập thông tin (**tên**, **CMND**, **số điện thoại**, **số phòng**, **ngày đến**, **ngày đi**). URL: `http://localhost:8081/bookings/add`.
- **👤 Quản Lý Khách Hàng**: Nhấn nút "Quản Lý Khách Hàng" để xem danh sách khách hàng, **xóa** hoặc **cập nhật** thông tin. URL: `http://localhost:8081/customers/active`.
- **🧾 Kiểm Tra Phòng**: Nhấn nút "Danh Sách Phòng" để xem danh sách **15 phòng** (**số phòng**, **loại phòng**, **trạng thái**). URL: `http://localhost:8081/rooms`.
- **🧱 Phòng Đang Hoạt Động**: Nhấn nút "Phòng Đang Hoạt Động" để xem danh sách đặt phòng hiện tại. URL: `http://localhost:8081/active-rooms`.

### 3. **Yêu cầu Hệ thống**
- **Java**: Phiên bản 17 (hoặc cao hơn).
- **Maven**: Đã cài đặt để quản lý dependency.
- **MySQL**: Đã cài đặt và tạo database `hotelbooking` với tài khoản `root` và mật khẩu `962005` (hoặc cấu hình lại trong `application.properties`).

## 🏆 **Phát triển trong Tương lai**
- Tính **tổng tiền thuê** cho từng khách hàng.
- **Thống kê** số lượng phòng đã có người ở và số khách thuê.
- Tìm kiếm khách hàng **thuê dài hạn**.
- Thêm giao diện **quản lý hóa đơn** và **lịch sử đặt phòng**.

## 📚 **Cấu trúc Dự án**
### **Các lớp chính**
- **Customer**: Lưu thông tin khách hàng (**tên**, **CMND**, **số điện thoại**).
- **Room**: Lưu thông tin phòng (**số phòng**, **loại phòng**, **trạng thái**).
- **Booking**: Lưu thông tin đặt phòng (**khách hàng**, **phòng**, **ngày nhận**, **ngày trả**).

### **Các gói (Packages)**
- `QuanLyKhachSan.controller`: Chứa các controller (**HomeController**, **BookingController**, **CustomerController**, **RoomController**).
- `QuanLyKhachSan.service`: Chứa các service (**RoomManagementService**, **CustomerManagementService**, **BookingManagementService**).
- `QuanLyKhachSan.repository`: Chứa các repository (**RoomRepository**, **CustomerRepository**, **BookingRepository**).
- `QuanLyKhachSan.model`: Chứa các model (**Customer**, **Room**, **Booking**).

## 📑 **Tài liệu Hỗ trợ**
### 01. **UML Class Diagram**
- Mô tả mối quan hệ giữa các lớp:
  - [Xem UML Class Diagram](https://github.com/user-attachments/assets/32a6823a-1911-4461-b014-6c90dadbf1f8)

### 02. **Lưu đồ Thuật toán**
- Lưu đồ thuật toán cho chức năng chính **addBooking**:
  - [Xem Lưu đồ Thuật toán](https://drive.google.com/file/d/1-YztSr3uTtsqaNtzF-dGCkJwRVqrML_D/view?usp=sharing)

### 03. **Tài liệu Thêm**
- [Tài liệu 01](https://drive.google.com/file/d/19ZqBlTzRiTUdUylslvrwirQt8ozASohF/view?usp=sharing)
- [Tài liệu 02](https://drive.google.com/file/d/1AsSR0mHNE9UGVOE0k1nJYVHmSVLO9Z0k/view?usp=sharing)

## 👨‍💻 **Phân công Công việc**
- **Trần Văn Nhật (MSV 230170625)**: Thực hiện chức năng **thêm**, **xóa**, và **cập nhật** thông tin khách hàng (`CustomerManagementService`).
- **Nguyễn Duy Bảo (MSV 23017133)**: Thực hiện chức năng **kiểm tra** và **hiển thị danh sách phòng** (`RoomManagementService`).
- **Phạm Văn Đạt (MSV 23017150)**: Thực hiện chức năng **đặt phòng** và **quản lý trạng thái phòng** (`BookingManagementService`).
- **Nguyễn Lê Thu**: Hỗ trợ **tích hợp** và **phát triển giao diện** (Thymeleaf).

## 📝 **Mô tả Các Phương thức**
- **addNewCustomer (Trần Văn Nhật)**:
  - Cho phép nhập thông tin khách hàng (**tên**, **CMND**, **số điện thoại**). Kiểm tra trùng lặp **CMND** trước khi thêm vào database thông qua `CustomerManagementService`.
- **findAvailableRoom (Nguyễn Duy Bảo)**:
  - Hiển thị danh sách phòng và kiểm tra **trạng thái trống/sử dụng** thông qua `RoomManagementService`.
- **bookRoomAndCheckStatus (Phạm Văn Đạt)**:
  - Thêm thông tin đặt phòng và cập nhật **trạng thái phòng**. Kiểm tra tính hợp lệ (**phòng trống**, **ngày hợp lệ**) thông qua `BookingManagementService`.

## ⚠️ **Lưu ý**
- Đảm bảo **cổng 8080** hoặc **8081** không bị chiếm dụng (cấu hình trong `application.properties`).
- Kiểm tra **kết nối MySQL** trước khi chạy ứng dụng.
- Gửi phản hồi nếu cần hỗ trợ thêm về lỗi hoặc phát triển tính năng.

---

### Lưu ý khi áp dụng
1. **Cập nhật file**: Sao chép nội dung trên vào file `README.md` trong thư mục dự án (`D:\hotelbooking`).
2. **Đồng bộ**: Sau khi cập nhật, chạy `mvn clean install` và `mvn spring-boot:run` để kiểm tra.
3. **Điều chỉnh**: Nếu muốn giữ package `com.hotelbooking` thay vì `QuanLyKhachSan`, thay đổi tất cả các tham chiếu trong README và mã nguồn.

Nếu bạn cần thêm chỉnh sửa hoặc hỗ trợ, hãy cho tôi biết! Hiện tại là **01:36 AM +07, Sunday, June 29, 2025**, tôi sẽ phản hồi ngay!
