# 💻 Chương Trình Quản lý Khách Sạn

## 📖 Giới thiệu
Ứng dụng web Quản lý Khách Sạn được phát triển bởi nhóm 4, lớp OOP_N03_K17, học kỳ 3 năm 2025, sử dụng Java Spring Boot. Sau nhiều lần chỉnh sửa và tối ưu hóa bởi cả nhóm, ứng dụng hiện cung cấp các chức năng quản lý phòng, khách hàng, và đặt phòng với giao diện thân thiện được xây dựng bằng Thymeleaf và TailwindCSS, tích hợp cơ sở dữ liệu MySQL. Đây là phiên bản đã được làm mới hoàn toàn để đáp ứng tốt hơn các yêu cầu thực tế, với trọng tâm là loại bỏ xung đột và cải thiện hiệu suất.

### Tính năng chính:
- 🏨 **Quản lý phòng**: Xem danh sách phòng, kiểm tra trạng thái (trống/đã đặt).
- 👤 **Quản lý khách hàng**: Thêm, xóa, sửa, và tìm kiếm thông tin khách hàng theo CCCD.
- 📅 **Quản lý đặt phòng**: Đặt phòng mới, cập nhật trạng thái phòng.
- 📊 **Báo cáo**: Theo dõi danh sách phòng đang hoạt động với khả năng tìm kiếm theo CCCD.

## 👥 Thành viên nhóm
| Họ tên          | Mã SV    | Vai trò                          |
|-----------------|----------|----------------------------------|
| Trần Văn Nhật   | 23010625 | Quản lý khách hàng (thêm/xóa/sửa) |
| Nguyễn Duy Bảo  | 23017133 | Hiển thị, kiểm tra danh sách phòng |
| Phạm Văn Đạt    | 23017150 | Đặt phòng, cập nhật trạng thái phòng |
| Nguyễn Lệ Thu   | -        | Hướng dẫn làm bài                |

## 🚀 Công nghệ sử dụng
- **Backend**: Java 17, Spring Boot
- **Frontend**: Thymeleaf, HTML, TailwindCSS
- **ORM & Database**: Spring Data JPA, MySQL 8+
- **Build Tool**: Maven

## 📦 Hướng dẫn cài đặt

### 1. Cấu hình cơ sở dữ liệu
Tạo database trong MySQL:
```sql
CREATE DATABASE hotelbooking;
```

Cấu hình file `src/main/resources/application.properties`:
```
spring.datasource.url=jdbc:mysql://localhost:3306/hotelbooking
spring.datasource.username=root
spring.datasource.password=962005
spring.jpa.hibernate.ddl-auto=update
server.port=8081
```

### 2. Yêu cầu hệ thống
- Java 17+
- Maven
- MySQL 8+

### 3. Chạy ứng dụng
- Clone repository:
  ```bash
  git clone https://github.com/PhamDat-16/OOP_N03_Term3_2025_K17_Group4.git
  cd gs-serving-web-content-main
  ```
- Cài đặt dependencies và chạy:
  ```bash
  mvn clean install
  mvn spring-boot:run
  ```
- Truy cập ứng dụng tại: [http://localhost:8081/](http://localhost:8081/)

### 4. Chạy trên Codespaces
- Cài đặt MySQL:
  ```bash
  sudo apt update
  sudo apt install mysql-server
  sudo service mysql start
  sudo mysql
  ```
- Cấu hình MySQL:
  ```sql
  ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '962005';
  CREATE DATABASE IF NOT EXISTS hotelbooking;
  FLUSH PRIVILEGES;
  EXIT;
  ```

## 🌐 Giao diện người dùng
| Chức năng                | URL                | Mô tả                              |
|--------------------------|---------------------|------------------------------------|
| Trang chủ                | `/`                | Giao diện chính                    |
| Đặt phòng                | `/bookings/new`    | Thêm đặt phòng mới                 |
| Quản lý khách hàng       | `/customers`       | Xem, tìm kiếm, cập nhật, xóa khách hàng |
| Danh sách phòng          | `/rooms`           | Hiển thị phòng và trạng thái       |
| Phòng đang hoạt động     | `/active-bookings` | Xem danh sách khách đã đặt, tìm kiếm theo CCCD |

## 🧱 Cấu trúc dự án
```
gs-serving-web-content-main/
├── src/
│   ├── main/
│   │   ├── java/com/example/servingwebcontent/
│   │   │   ├── controller/         # HomeController xử lý tất cả yêu cầu HTTP
│   │   │   ├── model/             # Booking, Customer, Room
│   │   │   ├── repository/        # Giao tiếp với cơ sở dữ liệu qua Spring Data JPA
│   │   │   └── service/           # Logic nghiệp vụ (BookingManagementService)
│   └── resources/
│       ├── static/
│       │   └── css/               # TailwindCSS hoặc CSS tùy chỉnh
│       ├── templates/             # index.html là giao diện chính
├── test/                         # Các bài kiểm thử (AddCustomer.java, BookRoom.java, FindRoom.java)
```

### Mô tả thành phần chính
- **Controller**: `HomeController` tập trung xử lý tất cả yêu cầu HTTP, loại bỏ xung đột từ các controller khác.
- **Model**: Định nghĩa các thực thể `Booking`, `Customer`, `Room`.
- **Repository**: Sử dụng Spring Data JPA để giao tiếp với MySQL.
- **Service**: `BookingManagementService` chứa logic nghiệp vụ cho đặt phòng, khách hàng, và phòng.
- **Templates**: `index.html` là giao diện chính, tích hợp menu và tìm kiếm CCCD; các tệp như `customers.html`, `new-booking.html`, `rooms.html` cần được tạo nếu chưa có.
- **Test**: Chứa các hàm kiểm thử để đảm bảo tính năng hoạt động đúng.

## 📌 Tính năng tương lai
- 🧾 Tạo hóa đơn và lịch sử đặt phòng.
- 🔍 Tìm kiếm khách thuê dài hạn.

## 📚 Tài liệu tham khảo
- **Sơ đồ**: [Link Sơ đồ](https://github-production-user-asset-6210df.s3.amazonaws.com/208896553/446329549-32a6823a-1911-4461-b014-6c90dadbf1f8.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250628%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250628T184250Z&X-Amz-Expires=300&X-Amz-Signature=35398fe21ff1c71d3f57409d3d1b6998d2733db2fec3e72398542d0800d6f96a&X-Amz-SignedHeaders=host)
- **Lưu đồ thuật toán**: [Link Lưu đồ](https://drive.google.com/file/d/1-YztSr3uTtsqaNtzF-dGCkJwRVqrML_D/view)
- **Biểu đồ UML**: [Link UML](https://drive.google.com/file/d/19ZqBlTzRiTUdUylslvrwirQt8ozASohF/view)

## ⚠️ Lưu ý
- Đảm bảo MySQL đang chạy trước khi khởi động ứng dụng.
- Kiểm tra cổng 8081 không bị chiếm dụng. Nếu gặp lỗi, chỉnh sửa `server.port` trong `application.properties`.
- Xác nhận không còn controller gây xung đột (như `CustomerController`); nếu có, xóa hoặc đổi tên endpoint.
- Sau khi chỉnh sửa toàn bộ mã, kiểm tra kỹ lưỡng các tệp `HomeController.java`, `index.html`, và các template khác để đảm bảo tính nhất quán.

## 👨‍💼 Phân công công việc
- **Trần Văn Nhật**: `addNewCustomer` (thêm, kiểm tra trùng khách hàng).
- **Nguyễn Duy Bảo**: `findAvailableRoom` (xem, kiểm tra phòng trống).
- **Phạm Văn Đạt**: `bookRoomAndCheckStatus` (đặt phòng, kiểm tra trạng thái).
- **Cả nhóm**: Tích hợp, tối ưu hóa mã trong `HomeController` và `index.html`, hoàn thiện dự án.


