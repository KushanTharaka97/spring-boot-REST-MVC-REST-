package com.example.ec.web;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Tour Rating Controller
 *
 * Created by Mary Ellen Bowman
 */
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    protected TourRatingController() {

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId,
                                 @RequestBody @Validated RatingDTO ratingDTO){
        Tour tour = verifyTour(tourId);

    tourRatingRepository.save(new TourRating(new TourRatingPk(tour, ratingDTO.getCustomerId()), ratingDTO.getScore(), ratingDTO.getComment()));
    }


    //getAll the ratings for the Tour
    public List<RatingDTO> getAllRatingsForTour(int tourId){
        verifyTour(tourId);
        return tourRatingRepository.findByPkTourId(tourId).stream().map(RatingDTO::new).collect(Collectors.toList());
    }

    //average
    public Map<String, Double> getAverage(int tourId){
        verifyTour(tourId);
        return Map.of("Average", tourRatingRepository
                .findByPkTourId(tourId)
                .stream().mapToInt(TourRating::getScore)
                .average()
                .orElseThrow(()-> new NoSuchElementException("Tour Has No Rating")));
    }
    /**
     * Verify and return the Tour given a tourId.
     *
     * @param tourId tour identifier
     * @return the found Tour
     * @throws NoSuchElementException if no Tour found.
     */


    private Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() ->
            new NoSuchElementException("Tour does not exist " + tourId));
        }

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex exception
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }

}
