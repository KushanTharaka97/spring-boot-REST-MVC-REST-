package com.example.ec.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class TourRatingPk implements Serializable {


    @ManyToOne
    private Tour tour;

    @Column(insertable = false, updatable = false, nullable = false)
    private  Integer customerId;

    public TourRatingPk() {
    }
}
