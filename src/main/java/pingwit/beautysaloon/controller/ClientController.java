package pingwit.beautysaloon.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get client by ID",
            description = "Get all the information about client. If client doesn't exist, 404 response code will be returned.")
    public ClientDTO getClientById(@PathVariable Integer id) {
        return clientService.getClientById(id);
    }

    @GetMapping
    @Operation(summary = "Get all clients",
            description = "Get the collection of existing clients with all information about each.")
    public Collection<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping()
    @Operation(summary = "Create the client",
            description = "Write the information about client. If some field will be invalid, 400 response code will be returned.")
    public Integer createClient(@RequestBody @Valid ClientDTO clientToCreate) {
        return clientService.createClient(clientToCreate);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update client by ID",
            description = "Update the information about client. If client doesn't exist, 404 response code will be returned.")
    public ClientDTO updateClient(@PathVariable Integer id, @RequestBody @Valid ClientDTO clientToUpdate) {
        return clientService.updateClient(id, clientToUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client by ID",
            description = "Delete all the information about client. If client doesn't exist, 404 response code will be returned.")
    public void deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
    }
}
