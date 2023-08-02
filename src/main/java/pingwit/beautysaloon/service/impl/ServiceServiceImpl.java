package pingwit.beautysaloon.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pingwit.beautysaloon.controller.dto.ServiceDTO;
import pingwit.beautysaloon.converter.ServiceConverter;
import pingwit.beautysaloon.exception.NotFoundException;
import pingwit.beautysaloon.repositiry.ServiceRepository;
import pingwit.beautysaloon.repositiry.model.SaloonService;
import pingwit.beautysaloon.service.ServiceService;
import pingwit.beautysaloon.validator.ServiceValidator;


import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceConverter serviceConverter;
    private final ServiceValidator validator;

    public ServiceServiceImpl(ServiceRepository serviceRepository, ServiceConverter serviceConverter, ServiceValidator validator) {
        this.serviceRepository = serviceRepository;
        this.serviceConverter = serviceConverter;
        this.validator = validator;
    }

    @Override
    public ServiceDTO getServiceById(Integer id) {
        SaloonService saloonService = serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Service not found: " + id));
        return serviceConverter.convertServiceToDTO(saloonService);
    }

    @Override
    public List<ServiceDTO> getAllServices() {
        return serviceConverter.convertServiceToDTO(serviceRepository.findAll());
    }

    @Override
    @Transactional
    public Integer createService(ServiceDTO serviceToCreate) {
        validator.validateService(serviceToCreate);
        SaloonService service = serviceConverter.convertServiceToEntity(serviceToCreate);
        SaloonService savedService = serviceRepository.save(service);
        return savedService.getId();
    }

    @Override
    public ServiceDTO updateService(Integer id, ServiceDTO serviceToUpdate) {
        validator.validateService(serviceToUpdate);
        SaloonService saloonService = serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Service not found: " + id));
        SaloonService entityToUpdate = serviceConverter.convertServiceToEntity(serviceToUpdate);
        entityToUpdate.setId(id);
        SaloonService updatedService = serviceRepository.save(entityToUpdate);
        return serviceConverter.convertServiceToDTO(updatedService);
    }

    @Override
    @Transactional
    public void deleteService(Integer id) {
        SaloonService saloonService = serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Service not found: " + id));
        serviceRepository.delete(saloonService);
    }
}
