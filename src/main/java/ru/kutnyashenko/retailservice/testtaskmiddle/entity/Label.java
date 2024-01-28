package ru.kutnyashenko.retailservice.testtaskmiddle.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "label", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Label {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse owner;

    @Column(name = "label")
    private String label;
}
