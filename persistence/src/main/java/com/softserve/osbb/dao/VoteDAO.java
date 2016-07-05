package com.softserve.osbb.dao;

import com.softserve.osbb.model.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteDAO extends JpaRepository<VoteEntity, Integer> {
}