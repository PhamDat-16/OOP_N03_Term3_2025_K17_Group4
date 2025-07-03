// Booking.java
package com.example.servingwebcontent.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "idCard")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "room_number", referencedColumnName = "roomNumber")
    private Room room;

    private LocalDate checkIn;
    private LocalDate checkOut;
}
