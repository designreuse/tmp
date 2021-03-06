package com.softserve.osbb.tenants.service.impl;

import com.softserve.osbb.tenants.model.Apartment;
import com.softserve.osbb.tenants.repository.ApartmentRepository;
import com.softserve.osbb.tenants.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ApartmentServiceImpl implements ApartmentService {
    @Autowired
    ApartmentRepository apartmentRepository;

    @Override
    public void saveApartment(Apartment apartment) {
        apartmentRepository.save(apartment);
    }

    @Override
    public void saveApartmentList(List<Apartment> list) {

        apartmentRepository.save(list);
    }

    @Override
    public Apartment findOneApartmentByID(Integer id) {

        return apartmentRepository.findOne(id);
    }

    @Override
    public List<Apartment> findAllApartmentByIDs(List<Integer> ids) {

        return apartmentRepository.findAll(ids);
    }

    @Override
    public List<Apartment> findAllApartment() {

        return apartmentRepository.findAll();
    }

    @Override
    public void deleteApartment(Apartment apartment) {

        apartmentRepository.delete(apartment);
    }

    @Override
    public void deleteApartmentByID(Integer id) {

        apartmentRepository.delete(id);
    }

    @Override
    public void deleteListApartments(List<Apartment> list) {

        apartmentRepository.delete(list);
    }

    @Override
    public void deleteAllApartmnets() {

        apartmentRepository.deleteAll();
    }
    @Override
    public Apartment updateApartment(Apartment apartment){
return
        apartmentRepository.save(apartment);
    }
    @Override
    public long countApartments() {

        return apartmentRepository.count();
    }

    @Override
    public boolean existsApartment(Integer id) {

        return apartmentRepository.exists(id);
    }

    @Override
    public Apartment findApartmentByNumber(Integer number){
        return apartmentRepository.findApartmentByNumber(number);
    }
}