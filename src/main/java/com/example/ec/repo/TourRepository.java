package com.example.ec.repo;

import com.example.ec.domain.Tour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;


/**
 * Tour Repository Interface
 *
 * Created by Mary Ellen Bowman
 * --Kushan I am following her course.
 */
public interface TourRepository extends PagingAndSortingRepository<Tour, Integer> {
    List<Tour> findByTourPackage(String code, Pageable pageable);
}
