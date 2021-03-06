package com.softserve.osbb.tenants.service.impl;

import com.softserve.osbb.tenants.model.Vote;
import com.softserve.osbb.tenants.repository.VoteRepository;
import com.softserve.osbb.tenants.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Roman on 10.07.2016.
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    VoteRepository voteRepository;

    @Override
    public Vote addVote(Vote vote) {
        return voteRepository.saveAndFlush(vote);
    }

    @Override
    public Vote getVoteById(Integer id) {
        return voteRepository.findOne(id);
    }

    @Override
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    @Override
    public boolean existsVote(Integer id) {
        return voteRepository.exists(id);
    }

    @Override
    public Vote updateVote(Vote vote) {
        if(voteRepository.exists(vote.getVoteId())) {
            return voteRepository.save(vote);
        } else {
            throw new IllegalArgumentException("Vote with id=" + vote.getVoteId()
                    + " doesn't exist.");
        }
    }

    @Override
    public void deleteVote(Integer id) {
        voteRepository.delete(id);
    }

    @Override
    public void deleteVote(Vote vote) {
        voteRepository.delete(vote);
    }

    @Override
    public void deleteAllVotes() {
        voteRepository.deleteAll();
    }

    @Override
    public long countVotes() {
        return voteRepository.count();
    }
}
