package com.example.ec.web;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    @GetMapping
    public Page<RatingDTO> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId,
                                                Pageable pageable){
        verifyTour(tourId);
        Page<TourRating> ratings = tourRatingRepository.findByPkTourId(tourId,pageable);

        return new PageImpl<>(
                ratings.get().map(RatingDTO::new).collect(Collectors.toList()),
                pageable,
                ratings.getTotalElements()
        );
//        return tourRatingRepository.findByPkTourId(tourId).stream().map(RatingDTO::new).collect(Collectors.toList());
    }

    //average
    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId")int tourId){
        verifyTour(tourId);
        return Map.of("Average", tourRatingRepository
                .findByPkTourId(tourId)
                .stream().mapToInt(TourRating::getScore)
                .average()
                .orElseThrow(()-> new NoSuchElementException("Tour Has No Rating")));
    }

    //PUT PATCH DELETE method have to implement here
    //PUT : UpdateWithPut PATCH:updateWithPatch DELETE: delete the rating

    //PUT method implementing 2021-08-20
        @PutMapping
        public RatingDTO updateWithPut(@PathVariable(value = "tourId")int tourId,
                                       @RequestBody @Validated RatingDTO ratingDTO){
            TourRating rating = verifyTourRating(tourId, ratingDTO.getCustomerId());
            rating.setScore(ratingDTO.getScore());
            rating.setComment(ratingDTO.getComment());
            return new RatingDTO(tourRatingRepository.save(rating));
        }

        //PATCH method implementing 2021-08-20
        @PatchMapping
        public RatingDTO updateWithPatch(@PathVariable(value = "tourId")int tourId,
                                         @RequestBody @Validated RatingDTO ratingDTO){
            TourRating rating = verifyTourRating(tourId, ratingDTO.getCustomerId());
                if(rating.getComment()!=null){
                    rating.setComment(ratingDTO.getComment());
                }
                if(rating.getScore()!=null){
                    rating.setScore(rating.getScore());
                }

            return new RatingDTO(tourRatingRepository.save(rating));
        }


        //DELETED
    @DeleteMapping(path = "/{customerId}")
    public void delete (@PathVariable(value = "tourId")int tourId,
                        @PathVariable(value = "customerId") int customerId){
        TourRating rating = verifyTourRating(tourId,customerId);
        tourRatingRepository.delete(rating);
    }
    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException{
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId,customerId).orElseThrow(()->
                new NoSuchElementException("Tour Does not exist "+tourId));

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
