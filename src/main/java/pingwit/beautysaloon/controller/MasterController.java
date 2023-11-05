package pingwit.beautysaloon.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.service.MasterService;

import java.util.Collection;
import java.util.List;

@Tag(name = "Master management API", description = "API for CRUD operations with masters")
@RestController
@RequestMapping("/masters")
public class MasterController {
    private final MasterService masterService;

    public MasterController(MasterService masterService) {
        this.masterService = masterService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get master by ID",
            description = "Get all the information about master. If master doesn't exist, 404 response code will be returned.")
    public MasterDTO getMasterById(@PathVariable Integer id) {
        return masterService.getMasterById(id);
    }

    @GetMapping
    @Operation(summary = "Get all masters",
            description = "Get the collection of existing masters with all information about each.")
    public Collection<MasterDTO> getAllMasters() {
        return masterService.getAllMasters();
    }

    @PostMapping()
    @Operation(summary = "Create the master",
            description = "Write the information about master. If some field will be invalid, 400 response code will be returned.")
    public Integer createMaster(@RequestBody MasterDTO masterToCreate) {
        return masterService.createMaster(masterToCreate);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update master by ID",
            description = "Update the information about master. If master doesn't exist, 404 response code will be returned.")
    public MasterDTO updateMaster(@PathVariable Integer id, @RequestBody MasterDTO masterToUpdate) {
        return masterService.updateMaster(id, masterToUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete master by ID",
            description = "Delete all the information about master. If master doesn't exist, 404 response code will be returned.")
    public void deleteMaster(@PathVariable Integer id) {
        masterService.deleteMaster(id);
    }
    @GetMapping("/procedures")
    @Operation(summary = "Find all masters, who do BeautyProcedure",
            description = "Find List<MasterDTO> with beautyProcedure. If there are no any master, empty list will be returned")
    public List<MasterDTO> findMastersByBeautyProcedure(@RequestParam String beautyProcedure){
       return masterService.getMastersByBeautyProcedure(beautyProcedure);
    }
}
