package QuanLyKhachSan.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID tự động tăng làm khóa chính

    @Column(name = "id_card", unique = true, nullable = false)
    private String idCard;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    public Customer() {
    }

    public Customer(String name, String idCard, String phone) {
        this.name = name;
        this.idCard = idCard;
        this.phone = phone;
    }

    // Getter - Setter
    public Long getId() {
        return id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
