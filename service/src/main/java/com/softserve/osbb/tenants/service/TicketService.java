package com.softserve.osbb.tenants.service;

import com.softserve.osbb.tenants.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kris on 12.07.2016.
 */

@Service
public interface TicketService {

    Ticket save(Ticket ticket);

    Ticket saveAndFlush(Ticket ticket);

    Ticket findOne(Integer id);

    Ticket findOne(String id);

    boolean exists(Integer id);

    List<Ticket> findAll();

    List<Ticket> findAll(Sort sort);

    Page<Ticket> findAll(Pageable pageable);

    List<Ticket> findAll(Iterable<Integer> iterable);

    long count();

    boolean delete(Integer id);

    boolean delete(Ticket ticket);

    boolean delete(Iterable<? extends Ticket> iterable);

    boolean deleteAll();

    void flush();

    Ticket getOne(Integer id);

    List<Ticket> save(Iterable<Ticket> iterable);

    Ticket update(Ticket ticket);

    public Page<Ticket> getAllTickets(Integer pageNumber, String sortBy, Boolean order);


}
