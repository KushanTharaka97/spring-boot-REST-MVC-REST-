package com.example.ec.repo;

import com.example.ec.domain.TourPackage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Tour Package Repository Interface
 *
 * Created by Mary Ellen Bowman | Tutorial Followed By Kushan Tharaka
 */
@Repository
public interface TourPackageRepository extends CrudRepository<TourPackage, String> {
    Optional<TourPackage> findByName(String name);
}

