package com.example.ec.repo;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<Tour, TourRatingPk> {
        List<TourRating> findByTorRating(TourRatingPk tourRatingPk);


    Optional<Tour> findByPkTourIdAndPkCustomerId(TourRatingPk tourRatingPk);
}
