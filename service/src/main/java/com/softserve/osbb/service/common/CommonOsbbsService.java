package com.softserve.osbb.service.common;

import com.softserve.osbb.model.common.Osbbs;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Anastasiia Fedorak on 8/24/16.
 */
@Service
public interface CommonOsbbsService {
        Osbbs findOneOsbbById(Integer id);
        List<Osbbs> findAllOsbbs();
        void saveOsbb(Osbbs osbb) throws Exception;

}
