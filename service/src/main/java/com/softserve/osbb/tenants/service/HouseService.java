package com.softserve.osbb.tenants.service;

import com.softserve.osbb.tenants.model.Apartment;
import com.softserve.osbb.tenants.model.House;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nazar.dovhyy on 14.07.2016.
 */

@Service
public interface HouseService {

    House addHouse(House house);

    House updateHouse(Integer houseId, House house);

    House findHouseById(Integer houseId);

    List<House> getAllHousesBySearchParameter(String searchTerm);

    List<House> findAllByCity(String city);

    List<House> findAllByStreet(String street);

    List<Apartment> findAllAppartmentsByHouseId(Integer houseId);

    boolean deleteHouseById(Integer id);

    void deleteAllHouses();

    Page<House> getAllHouses(Integer pageNumber, String sortedBy, Boolean order, Integer rowNum);
}
