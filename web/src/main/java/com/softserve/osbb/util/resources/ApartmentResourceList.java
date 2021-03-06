package com.softserve.osbb.util.resources;

import com.softserve.osbb.tenants.controller.ApartmentController;
import com.softserve.osbb.tenants.model.Apartment;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by nazar.dovhyy on 19.07.2016.
 */
public class ApartmentResourceList extends EntityResourceList<Apartment> {
    @Override
    public Resource<Apartment> createLink(Resource<Apartment> apartmentResource) {

        Apartment apartment = apartmentResource.getContent();
        apartmentResource.add(linkTo(methodOn(ApartmentController.class)
                .getAppartmentByNumber(apartment.getApartmentId()))
                .withSelfRel());

        return apartmentResource;
    }
}
