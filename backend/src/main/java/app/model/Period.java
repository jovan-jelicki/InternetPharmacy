package app.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date start;

//    @Column
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date end;

    public Period() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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