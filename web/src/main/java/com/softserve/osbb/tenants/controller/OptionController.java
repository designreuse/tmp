package com.softserve.osbb.tenants.controller;

import com.softserve.osbb.dto.OptionDTO;
import com.softserve.osbb.dto.OptionDTOMapper;
import com.softserve.osbb.tenants.model.Option;
import com.softserve.osbb.tenants.model.User;
import com.softserve.osbb.tenants.service.OptionService;
import com.softserve.osbb.tenants.service.UserService;
import com.softserve.osbb.tenants.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * Created by Roman on 28.07.2016.
 */
@RestController
@CrossOrigin
@RequestMapping("/restful")
public class OptionController {

    private static Logger logger = LoggerFactory.getLogger(OptionController.class);

    @Autowired
    private OptionService optionService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

/*
    @RequestMapping(value = "/option/{id}", method = RequestMethod.POST)
    public ResponseEntity<Resource<Option>> createOption(@RequestBody Option option,
                                                         @PathVariable("id") Integer voteId) {
        logger.info("Saving option object " + option.toString());
        Vote vote = voteService.getVoteById(voteId);
        vote.getOptions().add(option);
        option.setVote(vote);
        option = optionService.addOption(option);
        voteService.updateVote(vote);
        return new ResponseEntity<>(addResourceLinkToOption(option), HttpStatus.OK);
    }
*/


/*
               @RequestMapping(value = "/option", method = RequestMethod.POST)
               public ResponseEntity<Resource<Option>> createOption(@RequestBody Option option) {
                   System.out.println("*************************************************");
                   System.out.println("Option.voteId" + option.getVote().getVoteId());
                   System.out.println("*************************************************");
                   logger.info("Create option.  " + option);
                   option = optionService.addOption(option);
                   return new ResponseEntity<>(addResourceLinkToOption(option), HttpStatus.OK);
               }
*/

    @RequestMapping(value = "/option/result/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getResultForOption(@PathVariable("id") Integer optionId) {
        logger.info("Get result for option.  optionId=" + optionId);
        Option option = optionService.getOption(optionId);
        return new ResponseEntity<>(option.getUsers(),HttpStatus.OK);
    }

    @RequestMapping(value = "/option/{optionId},{userId}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Option>> toScoreOption(@PathVariable("optionId") Integer optionId,
                                                          @PathVariable("userId") Integer userId) {
        logger.info("To score option: optionId=" + optionId + " userId=" + userId );
        Option option = optionService.getOption(optionId);
        User user = userService.findOne(userId);
        option.getUsers().add(user);
        user.getOptions().add(option);
        optionService.updateOption(option);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/option/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Option>> getOptionById(@PathVariable("id") Integer optionId) {
        logger.info("Get one option by id: " + optionId);
        Option option = optionService.getOption(optionId);
        return new ResponseEntity<>(addResourceLinkToOption(option), HttpStatus.OK);
    }

    @RequestMapping(value = "/option", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<OptionDTO>>> getAllOption() {
        logger.info("Get all optionDTO.");
        List<Option> optionList = optionService.getAllOption();
        List<OptionDTO> optionDTOList = new ArrayList<>();
        for(Option o: optionList) {
            optionDTOList.add(OptionDTOMapper.mapOptionEntityToDTO(o));
        }
        final List<Resource<OptionDTO>> resourceOptionList = new ArrayList<>();
        for(OptionDTO o: optionDTOList) {
            resourceOptionList.add(addResourceLinkToOption(o));
        }
        return new ResponseEntity<>(resourceOptionList, HttpStatus.OK);
    }


    @RequestMapping(value = "/option", method = RequestMethod.PUT)
    public ResponseEntity<Resource<Option>> updateOption(@RequestBody Option option) {
        logger.info("Update option with id: " + option.getOptionId());
        Option updatedOption = optionService.updateOption(option);
        return new ResponseEntity<>(addResourceLinkToOption(updatedOption), HttpStatus.OK);
    }

    @RequestMapping(value = "/option/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Resource<Option>> deleteOption(@PathVariable("id") Integer id) {
        logger.info("Delete option with id: " + id );
        optionService.deleteOption(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/option", method = RequestMethod.DELETE)
    public ResponseEntity<Option>  deleteAllOption() {
        logger.info("Delete all option.");
        optionService.deleteAllOption();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Resource<Option> addResourceLinkToOption(Option option) {
        if (option == null) return null;
        Resource<Option> optionResource = new Resource<>(option);
        optionResource.add(linkTo(methodOn(OptionController.class)
                .getOptionById(option.getOptionId()))
                .withSelfRel());
        return optionResource;
    }

    private Resource<OptionDTO> addResourceLinkToOption(OptionDTO option) {
        if (option == null) return null;
        Resource<OptionDTO> optionResource = new Resource<>(option);
        optionResource.add(linkTo(methodOn(OptionController.class)
                .getOptionById(option.getOptionId()))
                .withSelfRel());
        return optionResource;
    }

}
