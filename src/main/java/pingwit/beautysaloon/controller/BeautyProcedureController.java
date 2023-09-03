package pingwit.beautysaloon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;
import pingwit.beautysaloon.service.BeautyProcedureService;

import java.util.Collection;

@Tag(name = "Procedure management API", description = "API for CRUD operations with procedures")
@RestController
@RequestMapping("/procedures")
public class BeautyProcedureController {
    private final BeautyProcedureService beautyProcedureService;

    public BeautyProcedureController(BeautyProcedureService beautyProcedureService) {
        this.beautyProcedureService = beautyProcedureService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get procedure by ID",
            description = "Get all the information about procedure. If procedure doesn't exist, 404 response code will be returned.")
    public BeautyProcedureDTO getProcedureById(@PathVariable Integer id) {
        return beautyProcedureService.getProcedureById(id);
    }

    @GetMapping()
    @Operation(summary = "Get all procedures",
            description = "Get the collection of existing procedures with all information about each.")
    public Collection<BeautyProcedureDTO> getAllProcedures() {
        return beautyProcedureService.getAllProcedures();
    }

    @PostMapping
    @Operation(summary = "Create the procedure",
            description = "Write the information about procedure. If some field will be invalid, 400 response code will be returned.")
    public Integer createProcedure(@RequestBody BeautyProcedureDTO procedureToCreate) {
        return beautyProcedureService.createProcedure(procedureToCreate);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update procedure by ID",
            description = "Update the information about procedure. If procedure doesn't exist, 404 response code will be returned.")
    public BeautyProcedureDTO updateProcedure(@PathVariable Integer id, @RequestBody BeautyProcedureDTO procedureToUpdate) {
        return beautyProcedureService.updateProcedure(id, procedureToUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete procedure by ID",
            description = "Delete all the information about procedure. If procedure doesn't exist, 404 response code will be returned.")
    public void deleteProcedureById(@PathVariable Integer id) {
        beautyProcedureService.deleteProcedureById(id);
    }
}
