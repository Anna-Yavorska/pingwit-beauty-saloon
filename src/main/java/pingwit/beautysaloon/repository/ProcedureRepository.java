package pingwit.beautysaloon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pingwit.beautysaloon.repository.model.Procedure;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Integer> {
}
