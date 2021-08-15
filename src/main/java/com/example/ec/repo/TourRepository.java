package com.example.ec.repo;

import com.example.ec.domain.Tour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;


/**
 * Tour Repository Interface
 *
 * Created by Mary Ellen Bowman | Tutorial Followed By Kushan Tharaka
 * --Kushan I am following her course.
 */

@Repository
//@RepositoryRestResource(collectionResourceRel = "packages",path = "packages") //can change the URL path from this
public interface TourRepository extends PagingAndSortingRepository<Tour, Integer> {
//    List<Tour> findByTourPackage(String code, Pageable pageable);

    @Override
    @RestResource(exported = false) //in this we can hide it to the public
    <S extends Tour> S save(S s);

    @Override
    @RestResource(exported = false)
    <S extends Tour> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    void deleteById(Integer integer);

    @Override
    @RestResource(exported = false)
    void delete(Tour tour);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Tour> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
