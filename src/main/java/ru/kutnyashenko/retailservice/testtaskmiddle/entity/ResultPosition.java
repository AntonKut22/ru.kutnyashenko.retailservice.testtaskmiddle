package ru.kutnyashenko.retailservice.testtaskmiddle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultPosition {

    private int warehouseId;
    private int count;
}
