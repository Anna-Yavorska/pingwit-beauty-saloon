package pingwit.beautysaloon.service;

import pingwit.beautysaloon.controller.dto.ServiceDTO;

import java.util.List;

public interface ServiceService {
    ServiceDTO getServiceById(Integer id);

    List<ServiceDTO> getAllServices();

    Integer createServie(ServiceDTO serviceToCreate);

    ServiceDTO updateService(Integer id, ServiceDTO serviceToUpdate);

    void deleteService(Integer id);
}
