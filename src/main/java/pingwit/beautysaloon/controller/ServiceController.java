package pingwit.beautysaloon.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pingwit.beautysaloon.controller.dto.ServiceDTO;
import pingwit.beautysaloon.service.ServiceService;

import java.util.List;
@Tag(name = "Service management API", description = "API for CRUD operations with services")
@RestController
@RequestMapping("/services")
public class ServiceController {
    private final ServiceService service;

    public ServiceController(ServiceService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ServiceDTO getServiceById(@PathVariable Integer id) {
        return service.getServiceById(id);
    }

    @GetMapping()
    public List<ServiceDTO> getAllServices() {
        return service.getAllServices();
    }

    @PostMapping()
    public Integer createService(@RequestBody ServiceDTO serviceToCreate) {
        return service.createService(serviceToCreate);
    }

    @PutMapping("/{id}")
    public ServiceDTO updateService(@PathVariable Integer id, @RequestBody ServiceDTO serviceToUpdate) {
        return service.updateService(id, serviceToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Integer id) {
        service.deleteService(id);
    }
}
