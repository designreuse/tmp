package com.softserve.osbb.common.repository;

import com.softserve.osbb.common.model.Osbbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Anastasiia Fedorak on 8/18/16.
 */
@Repository
public interface CommonOsbbsRepository extends JpaRepository<Osbbs, Integer> {
    List<Osbbs> findByName(String name);
}
