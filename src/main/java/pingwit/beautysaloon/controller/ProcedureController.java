package pingwit.beautysaloon.controller;

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
    public ProcedureDTO getProcedureById(@PathVariable Integer id) {
        return procedureService.getProcedureById(id);
    }

    @GetMapping()
    public Collection<ProcedureDTO> getAllProcedures() {
        return procedureService.getAllProcedures();
    }

    @PostMapping
    public Integer createProcedure(@RequestBody ProcedureDTO procedureToCreate) {
        return procedureService.createProcedure(procedureToCreate);
    }

    @PutMapping("/{id}")
    public ProcedureDTO updateProcedure(@PathVariable Integer id, @RequestBody ProcedureDTO procedureToUpdate) {
        return procedureService.updateProcedure(id, procedureToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteProcedureById(@PathVariable Integer id) {
        procedureService.deleteProcedureById(id);
    }
}
