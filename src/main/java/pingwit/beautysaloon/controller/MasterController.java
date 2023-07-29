package pingwit.beautysaloon.controller;


import org.springframework.web.bind.annotation.*;
import pingwit.beautysaloon.controller.dto.MasterDTO;
import pingwit.beautysaloon.service.MasterService;

import java.util.Collection;

@RestController
@RequestMapping("/masters")
public class MasterController {
    private final MasterService masterService;

    public MasterController(MasterService masterService) {
        this.masterService = masterService;
    }
    @GetMapping("/{id}")
    public MasterDTO getClientById(@PathVariable Integer id) {
        return masterService.getMasterById(id);
    }

    @GetMapping
    public Collection<MasterDTO> getAllMasters() {
        return masterService.getAllClients();
    }

    @PostMapping()
    public Integer createMaster(@RequestBody MasterDTO masterToCreate) {
        return masterService.createMaster(masterToCreate);
    }

    @PutMapping("/{id}")
    public MasterDTO updateMaster(@PathVariable Integer id, @RequestBody MasterDTO masterToUpdate) {
        return masterService.updateMaster(id, masterToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteMaster(@PathVariable Integer id) {
        masterService.deleteMaster(id);
    }
}
