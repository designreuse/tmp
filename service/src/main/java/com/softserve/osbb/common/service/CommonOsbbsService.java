package com.softserve.osbb.common.service;

import com.softserve.osbb.common.model.Osbbs;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Anastasiia Fedorak on 8/24/16.
 */
@Service
public interface CommonOsbbsService {
        Osbbs findOneOsbbById(Integer id);
        List<Osbbs> findOsbbByName(String name);
        List<Osbbs> findAllOsbbs();
        void saveOsbb(Osbbs osbb) throws Exception;

}
