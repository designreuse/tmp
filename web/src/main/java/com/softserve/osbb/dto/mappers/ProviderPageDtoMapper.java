package com.softserve.osbb.dto.mappers;

import com.softserve.osbb.tenants.controller.ReportController;
import com.softserve.osbb.dto.ProviderPageDTO;
import com.softserve.osbb.tenants.model.Provider;
import com.softserve.osbb.tenants.model.ProviderType;
import com.softserve.osbb.tenants.model.enums.Periodicity;
import com.softserve.osbb.tenants.service.ProviderService;
import com.softserve.osbb.tenants.service.ProviderTypeService;
import org.slf4j.LoggerFactory;

/**
 * Created by Anastasiia Fedorak on 8/2/16.
 */
public class ProviderPageDtoMapper {
    private static ProviderPageDtoMapper providerPageDtoMapper = new ProviderPageDtoMapper();
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReportController.class);

    private ProviderPageDtoMapper(){

    }
    public  static ProviderPageDtoMapper getInstance(){
        return providerPageDtoMapper;
    }


    public ProviderPageDTO mapProviderEntityToDto(Integer providerId, Provider provider){
        ProviderPageDTO providerPageDTO = new ProviderPageDTO();
        if (provider != null) {
            providerPageDTO.setProviderId(provider.getProviderId());
            providerPageDTO.setName(provider.getName());
            providerPageDTO.setDescription(provider.getDescription());
            providerPageDTO.setLogoUrl(provider.getLogoUrl());
            if (provider.getPeriodicity()!=null) {
                providerPageDTO.setPeriodicity(provider.getPeriodicity().toString());
            } else providerPageDTO.setPeriodicity(Provider.DEFAULT_PERIODICITY.toString());
            providerPageDTO.setType(provider.getType());
            providerPageDTO.setEmail(provider.getEmail());
            providerPageDTO.setPhone(provider.getPhone());
            providerPageDTO.setAddress(provider.getAddress());
            providerPageDTO.setSchedule(provider.getSchedule());
            providerPageDTO.setActive(provider.isActive());
        } else {
            logger.error("provider is null");
        }
        return providerPageDTO;
    }

    public Provider getProviderEntityFromDto(ProviderService providerService, ProviderTypeService providerTypeService, ProviderPageDTO providerPageDTO) {
        if (providerPageDTO == null) logger.debug("empty request");
        Provider provider;
        Integer providerId = providerPageDTO.getProviderId();
        if (providerId !=null)
            provider = providerService.findOneProviderById(providerPageDTO.getProviderId());
        else provider = new Provider();
        provider.setName(providerPageDTO.getName());
        provider.setDescription(providerPageDTO.getDescription());
        provider.setLogoUrl(providerPageDTO.getLogoUrl());
        if (providerPageDTO.getPeriodicity()==null) provider.setPeriodicity(Provider.DEFAULT_PERIODICITY);
        if (providerPageDTO.getPeriodicity() != null) {
            provider.setPeriodicity(Periodicity.valueOf(providerPageDTO.getPeriodicity()));
        }
        if (providerPageDTO.getType() != null) {
            try {
                if (providerTypeService.existsProviderType(providerPageDTO.getType().getProviderTypeId())) {
                    ProviderType type = providerTypeService.findOneProviderTypeById(providerPageDTO.getType().getProviderTypeId());
                    if (type != null) {
                        logger.debug("successfully find provider type entity");
                        provider.setType(type);
                    }
                }
            } catch (Exception e) {
                logger.error("cannot get dto from provider" + e.getMessage());
            }
        }
        provider.setEmail(providerPageDTO.getEmail());
        provider.setPhone(providerPageDTO.getPhone());
        provider.setAddress(providerPageDTO.getAddress());
        provider.setActive(providerPageDTO.isActive());
        provider.setSchedule(providerPageDTO.getSchedule());
        if (providerId==null) {
            providerService.saveProvider(provider);
        }
        return provider;
    }
}
