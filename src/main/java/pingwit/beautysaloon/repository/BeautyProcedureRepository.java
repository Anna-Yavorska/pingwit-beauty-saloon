package pingwit.beautysaloon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pingwit.beautysaloon.repository.model.BeautyProcedure;

@Repository
public interface BeautyProcedureRepository extends JpaRepository<BeautyProcedure, Integer> {
}
