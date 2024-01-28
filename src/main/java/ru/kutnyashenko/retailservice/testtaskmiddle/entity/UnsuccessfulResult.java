package ru.kutnyashenko.retailservice.testtaskmiddle.entity;

import lombok.Data;

@Data
public class UnsuccessfulResult implements Result {

    private final String textResult;

    public UnsuccessfulResult() {
        this.textResult = "Набор не содержится в документе.";
    }
}
