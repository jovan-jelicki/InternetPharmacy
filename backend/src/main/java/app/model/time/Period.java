package app.model.time;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
public class Period {

    private LocalDateTime periodStart;

    private LocalDateTime periodEnd;

    public Period() {}

    public LocalDateTime getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDateTime periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDateTime getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDateTime periodEnd) {
        this.periodEnd = periodEnd;
    }
}