package app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportsDTO {
    private String chartName;
    private double data;

    public ReportsDTO() {
    }

    public ReportsDTO(String chartName, double data) {
        this.chartName = chartName;
        this.data = data;
    }
}
