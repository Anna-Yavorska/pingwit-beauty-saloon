package pingwit.beautysaloon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pingwit.beautysaloon.repository.model.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
}
