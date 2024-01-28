package ru.kutnyashenko.retailservice.testtaskmiddle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
@ToString(exclude = "resultPositions")
public class SuccessfulResult implements Result {

    private final String textResult;

    List<ResultPosition> resultPositions;

    public SuccessfulResult(List<ResultPosition> resultPositions) {
        this.textResult = "Набор содержится в документе.";
        this.resultPositions = resultPositions;
    }
}
