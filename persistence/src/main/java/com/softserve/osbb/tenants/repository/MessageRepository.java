package com.softserve.osbb.tenants.repository;

import com.softserve.osbb.tenants.model.Message;
import com.softserve.osbb.tenants.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kris on 05.07.2016.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByTicket(Ticket ticket);
//    List<Message> findByTicketId(Ticket ticket);
}
