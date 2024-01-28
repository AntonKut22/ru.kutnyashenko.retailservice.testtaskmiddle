package ru.kutnyashenko.retailservice.testtaskmiddle.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "booking", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "positions")
public class Booking {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "owner")
    private List<BookingPosition> positions;
}
