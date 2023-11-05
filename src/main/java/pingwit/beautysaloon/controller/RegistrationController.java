package pingwit.beautysaloon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pingwit.beautysaloon.controller.dto.RegistrationDTO;
import pingwit.beautysaloon.service.RegistrationService;

import java.util.List;

@Tag(name = "Operation management API", description = "API for CRUD operations with operation")
@RestController
@RequestMapping("/operations")
public class RegistrationController {
    private final RegistrationService operation;

    public RegistrationController(RegistrationService operation) {
        this.operation = operation;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get operation by ID",
            description = "Get all the information about operation. If operation doesn't exist, 404 response code will be returned.")
    public RegistrationDTO getServiceById(@PathVariable Integer id) {
        return operation.getOperationById(id);
    }

    @GetMapping()
    @Operation(summary = "Get all operations",
            description = "Get the collection of existing operations with all information about each.")
    public List<RegistrationDTO> getAllServices() {
        return operation.getAllOperations();
    }

    @PostMapping()
    @Operation(summary = "Create the operation",
            description = "Write the information about operation. If some field will be invalid, 400 response code will be returned.")
    public Integer createService(@RequestBody RegistrationDTO serviceToCreate) {
        return operation.createOperation(serviceToCreate);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update operation by ID",
            description = "Update the information about operation. If operation doesn't exist, 404 response code will be returned.")
    public RegistrationDTO updateService(@PathVariable Integer id, @RequestBody RegistrationDTO serviceToUpdate) {
        return operation.updateService(id, serviceToUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete operation by ID",
            description = "Delete all the information about operation. If operation doesn't exist, 404 response code will be returned.")
    public void deleteService(@PathVariable Integer id) {
        operation.deleteOperation(id);
    }
}
