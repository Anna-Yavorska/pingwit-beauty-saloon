package pingwit.beautysaloon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.service.ProcedureService;

import java.util.Collection;

@Tag(name = "Procedure management API", description = "API for CRUD operations with procedures")
@RestController
@RequestMapping("/procedures")
public class ProcedureController {
    private final ProcedureService procedureService;

    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get procedure by ID",
            description = "Get all the information about procedure. If procedure doesn't exist, 404 response code will be returned.")
    public ProcedureDTO getProcedureById(@PathVariable Integer id) {
        return procedureService.getProcedureById(id);
    }

    @GetMapping()
    @Operation(summary = "Get all procedures",
            description = "Get the collection of existing procedures with all information about each.")
    public Collection<ProcedureDTO> getAllProcedures() {
        return procedureService.getAllProcedures();
    }

    @PostMapping
    @Operation(summary = "Create the procedure",
            description = "Write the information about procedure. If some field will be invalid, 400 response code will be returned.")
    public Integer createProcedure(@RequestBody ProcedureDTO procedureToCreate) {
        return procedureService.createProcedure(procedureToCreate);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update procedure by ID",
            description = "Update the information about procedure. If procedure doesn't exist, 404 response code will be returned.")
    public ProcedureDTO updateProcedure(@PathVariable Integer id, @RequestBody ProcedureDTO procedureToUpdate) {
        return procedureService.updateProcedure(id, procedureToUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete procedure by ID",
            description = "Delete all the information about procedure. If procedure doesn't exist, 404 response code will be returned.")
    public void deleteProcedureById(@PathVariable Integer id) {
        procedureService.deleteProcedureById(id);
    }
}
