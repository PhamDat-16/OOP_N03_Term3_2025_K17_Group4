# OOP_N03_Term3_2025_K17_Group4
#Chương trình quản lý khách sạn
Chương trình quản lý khách sạn là một ứng dụng phần mềm được thiết kế để giúp quản lý các hoạt động của một khách sạn, bao gồm việc theo dõi các phòng, khách hàng, và các dịch vụ liên quan. Chương trình này có thể bao gồm nhiều tính năng khác nhau để hỗ trợ các công việc quản lý, ví dụ như đặt phòng, trả phòng, xem thông tin phòng, và quản lý khách hàng.

#Giao diện người dùng (UI) có thể bao gồm các phần:

Giao diện đăng nhập: Quản lý sẽ đăng nhập vào hệ thống.

Giao diện quản lý phòng: Cho phép thêm, sửa, xóa các phòng.

Giao diện quản lý khách hàng: Cho phép thêm và quản lý thông tin khách hàng.

Giao diện đặt phòng: Quản lý quá trình khách hàng đặt phòng, xác nhận và cập nhật tình trạng phòng.

Giao diện báo cáo: Hiển thị các báo cáo thống kê về tình trạng phòng, doanh thu và các thông tin liên quan.

Trong tương lai sẽ cải tiến chuowg trình hoàn thiện hơn.
# Member:
# 1.Trần Văn Nhật
# 2.Nguyễn Duy Bảo
# 3.Phạm Văn Đạt
# 4.Nguyễn Lệ Thu
# Content:Project 04
# Nội Dung: 
Project 04. Xây dựng ứng dụng quản lý khách sạn.
Yêu cầu chính:
- Java Spring Boot
- Giao diện đơn giản
- Dữ liệu lưu vào file nhị phân
- Lưu trữ nội bộ bằng các Collection như ArrayList , Map , LinkedList,...
# Chức năng chính:
1.Quản ly Khách hàng(Customer):
- Thêm khách hàng
- Sửa thông tin khách hàng
- Xóa khách hàng
- Liệt kê danh sách khách hàng
- Lọc khách hàng theo tên, quốc tịch hoặc số ngày thuê
2. Quản lý phòng khách sạn(Room):
- Thêm phòng
- Sửa thông tin phòng
- Xóa phòng
- Liệt kê các phòng còn trống, phòng theo loại(thường , vip ,...)
3. Chức năng gán khách hàng vào phòng:
- Tạo mối liên kết giữa khách hàng và phòng thuê
4. Lưu và đọc dữ liệu từ file nhị phân:
- Dùng ObjectOutputStream / ObjectInputStream để đọc ghi danh sách khách hàng, phòng, và đặt phòng
5. Quản lý thêm đối tượng(Booking):
- Gồm thông tin về việc khách hàng thuê phòng nào, thời gian thuê, số ngày thuê,...

# Chức năng mở rộng(tùy chọn):
- Tính tổng tiền thuê cho từng khách hàng
- Thông kê các phòng đã có người ở, số lượng khách thuê
- Tìm kiêm khách sạn thuê dài hạn
# Các lớp cần thiết:
- Customer
- Booking
- Room



