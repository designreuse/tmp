package com.softserve.osbb.dto;

import com.softserve.osbb.tenants.model.Option;
import com.softserve.osbb.tenants.model.User;
import com.softserve.osbb.tenants.model.Vote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 14.08.2016.
 */
public class VoteDTOMapper {
    public static VoteDTO mapVoteEntityToDTO(Vote vote) {
        VoteDTO voteDTO = new VoteDTO();
        if(vote != null) {
            voteDTO.setVoteId(vote.getVoteId());
            voteDTO.setDescription(vote.getDescription());
            voteDTO.setOptions(OptionDTOMapper.mapOptionEntityToDTO(vote.getOptions()));
            voteDTO.setTime(vote.getTime());
            //voteDTO.setUser(vote.getUser());
            voteDTO.setUsersId(getALLUsersIdFromVote(vote.getOptions()));
        }
        return voteDTO;
    }

    private static List<Integer> getALLUsersIdFromVote(List<Option> options) {
        List<Integer> usersId = new ArrayList<>();
        for(Option option: options) {
            for(User user: option.getUsers()){
                usersId.add(user.getUserId());
            }
        }
        return usersId;
    }
}
