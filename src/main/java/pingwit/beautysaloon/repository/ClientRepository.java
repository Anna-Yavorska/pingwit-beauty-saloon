package pingwit.beautysaloon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pingwit.beautysaloon.repository.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByEmail(String email);
}
