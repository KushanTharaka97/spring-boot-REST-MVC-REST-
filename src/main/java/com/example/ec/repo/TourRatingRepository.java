package com.example.ec.repo;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPk> {
//        List<TourRating> findByTourRating(TourRatingPk tourRatingPk);
    List<TourRating> findByPkTourId(Integer tourId);
    //newly added that PkTourId and the PkCustomer
    Optional<TourRating> findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId);
    
    
    Page<TourRating> findByPkTourId(Integer tourId, Pageable pagable);

//        Optional<Tour> findByPkTourIdAndPkCustomerId(TourRatingPk tourRatingPk);
}

