package pingwit.beautysaloon.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pingwit.beautysaloon.repositiry.model.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {
}
