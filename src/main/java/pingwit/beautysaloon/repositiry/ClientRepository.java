package pingwit.beautysaloon.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pingwit.beautysaloon.repositiry.model.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findAllByEmail(String email);
}
