package app.model.time;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Period {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime periodStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

//    public LocalTime convertPeriodStartToPeriodStartTime() {
//        String localTimeString = this.getPeriodStart().getHour() + ":" + this.getPeriodStart().getMinute() + ":00";
//        LocalTime time = this.getPeriodStart().toLocalTime();
//        return LocalTime.parse(localTimeString, DateTimeFormatter.ISO_DATE_TIME);
//    }
//
//    public LocalTime convertPeriodEndToPeriodEndTime() {
//        String localTimeString = this.getPeriodEnd().getHour() + ":" + this.getPeriodEnd().getMinute() + ":00";
//        LocalTime time = this.getPeriodEnd().toLocalTime();
//        return LocalTime.parse(localTimeString, DateTimeFormatter.ISO_DATE_TIME);
//    }
}