package com.softserve.osbb.tenants.repository;

import com.softserve.osbb.tenants.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
