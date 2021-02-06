package app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportIncomeDTO {
    private String chartName;
    private double income;
    private double expense;

    public ReportIncomeDTO() {
    }

    public ReportIncomeDTO(String chartName, double income, double expense) {
        this.chartName = chartName;
        this.income = income;
        this.expense = expense;
    }
}
