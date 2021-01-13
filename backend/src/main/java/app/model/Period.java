package app.model;

import javax.persistence.*;
import java.sql.Date;

@Embeddable
public class Period {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proba;

//    @Column
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date start;

//    @Column
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date end;

    public Period() {}

    public Long getId() {
        return proba;
    }

    public void setId(Long proba) {
        this.proba = proba;
    }

//    public Date getStart() {
//        return start;
//    }
//
//    public void setStart(Date start) {
//        this.start = start;
//    }
//
//    public Date getEnd() {
//        return end;
//    }
//
//    public void setEnd(Date end) {
//        this.end = end;
//    }
}