package ru.kutnyashenko.retailservice.testtaskmiddle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking_position", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingPosition {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking owner;

    @Column(name = "label")
    private String label;

    @Column(name = "cnt")
    private int cnt;

}
