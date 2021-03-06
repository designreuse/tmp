package com.softserve.osbb.tenants.repository;


import com.softserve.osbb.tenants.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {

   @Query("Select ap From Apartment ap where ap.number=:number")
    Apartment findApartmentByNumber(@Param("number") Integer number);
}
