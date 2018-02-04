package com.hibernate.learning.domain.embeddable;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class ArrangeTimeTable {

    private LocalDateTime departureTime;
    private LocalDateTime endTime;

}
