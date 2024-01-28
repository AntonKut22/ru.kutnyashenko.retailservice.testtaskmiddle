package ru.kutnyashenko.retailservice.testtaskmiddle.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "warehouse", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "labels")
public class Warehouse {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @OneToMany(mappedBy = "owner")
    private List<Label> labels;
}
