package pingwit.beautysaloon.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import pingwit.beautysaloon.controller.dto.ClientDTO;
import pingwit.beautysaloon.service.ClientService;

import java.util.Collection;
@Tag(name = "Client management API", description = "API for CRUD operations with clients")
@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable Integer id) {
        return clientService.getClientById(id);
    }

    @GetMapping
    public Collection<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping()
    public Integer createClient(@RequestBody @Valid ClientDTO clientToCreate) {
        return clientService.createClient(clientToCreate);
    }

    @PutMapping("/{id}")
    public ClientDTO updateClient(@PathVariable Integer id, @RequestBody @Valid ClientDTO clientToUpdate) {
        return clientService.updateClient(id, clientToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
    }
}
