package pingwit.beautysaloon.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pingwit.beautysaloon.repositiry.model.SaloonService;

@Repository
public interface ServiceRepository extends JpaRepository<SaloonService, Integer> {
}
