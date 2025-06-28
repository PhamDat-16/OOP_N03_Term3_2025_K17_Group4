# 💻 HỆ THỐNG QUẢN LÝ KHÁCH SẠN

## 📖 Giới thiệu

Đây là ứng dụng web quản lý khách sạn được phát triển bằng **Java Spring Boot**, hỗ trợ các chức năng chính như:

- 🏨 **Quản lý phòng (xem danh sách phòng, trạng thái)
- 👤 **Quản lý khách hàng** (thêm, xóa, cập nhật)
- 📦 **Đặt phòng** và quản lý tình trạng phòng
- 📊 **Xem báo cáo các phòng đang hoạt động**

Hệ thống sử dụng cơ sở dữ liệu **MySQL** và giao diện thân thiện với người dùng qua **Thymeleaf + TailwindCSS**.

---
👥 Thành viên nhóm
| Họ tên         | Mã SV     | Vai trò                                 |
| -------------- | --------- | --------------------------------------- |
| Trần Văn Nhật  | 230170625 | Quản lý khách hàng (thêm/xóa/sửa)       |
| Nguyễn Duy Bảo | 23017133  | Hiển thị và kiểm tra danh sách phòng    |
| Phạm Văn Đạt   | 23017150  | Đặt phòng, cập nhật trạng thái phòng    |
| Nguyễn Lê Thu  | -         | Hướng dẫn làm bài                       |


## 🚀 Công nghệ sử dụng

| Thành phần          | Công nghệ                          |
|---------------------|------------------------------------|
| Backend             | Java 17 + Spring Boot              |
| View Layer          | Thymeleaf + HTML + TailwindCSS     |
| ORM & DB Access     | Spring Data JPA                    |
| Database            | MySQL 8+                           |
| Build Tool          | Maven                              |

---

## 📦 Cài đặt & Khởi chạy

### 1. Cấu hình cơ sở dữ liệu

- Tạo database trong MySQL:

```sql
CREATE DATABASE hotelbooking;



# 🌐 Giao diện Người dùng (UI)
| Chức năng            | URL                      | Mô tả                                   |
| -------------------- | ------------------------ | --------------------------------------- |
| Trang chủ            | `http://localhost:8081/` | Giao diện chọn chức năng                |
| Đặt phòng            | `/bookings/add`          | Thêm đặt phòng mới                      |
| Quản lý khách hàng   | `/customers/active`      | Xem, tìm kiếm, cập nhật, xóa khách hàng |
| Danh sách phòng      | `/rooms`                 | Hiển thị tất cả phòng và trạng thái     |
| Phòng đang hoạt động | `/active-rooms`          | Xem danh sách khách đã đặt phòng        |


🧱 Cấu trúc hệ thống
src/
├── com.hotelbooking
│   ├── controller          // Home, Booking, Customer, Room
│   ├── model               // Booking, Customer, Room
│   ├── repository          // BookingRepository, CustomerRepository, RoomRepository
│   ├── service             // BookingManagementService, CustomerManagementService, RoomManagementService
│   └── HotelbookingApplication.java
└── resources/
    ├── templates/          // Các view Thymeleaf
    └── application.properties


📌 Tính năng tương lai
💵 Tính tổng tiền thuê theo số ngày

📈 Thống kê phòng đang được sử dụng

🧾 Tạo hóa đơn và lịch sử đặt phòng

🔍 Tìm kiếm khách thuê dài hạn


📚 Tài liệu tham khảo
Sơ đồ : https://github-production-user-asset-6210df.s3.amazonaws.com/208896553/446329549-32a6823a-1911-4461-b014-6c90dadbf1f8.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250628%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250628T184250Z&X-Amz-Expires=300&X-Amz-Signature=35398fe21ff1c71d3f57409d3d1b6998d2733db2fec3e72398542d0800d6f96a&X-Amz-SignedHeaders=host

Lưu đồ thuật toán: https://drive.google.com/file/d/1-YztSr3uTtsqaNtzF-dGCkJwRVqrML_D/view

UML Diagram:  https://drive.google.com/file/d/19ZqBlTzRiTUdUylslvrwirQt8ozASohF/view


⚠️ Lưu ý khi chạy
Đảm bảo MySQL đang hoạt động và cổng 8081 không bị chiếm dụng.

Đảm bảo Java 17+ và Maven đã cài đặt.

Nếu xảy ra lỗi port: đổi server.port=8081 trong application.properties.
