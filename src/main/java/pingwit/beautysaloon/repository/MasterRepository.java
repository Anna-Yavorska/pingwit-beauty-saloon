package pingwit.beautysaloon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pingwit.beautysaloon.repository.model.Master;

@Repository
public interface MasterRepository extends JpaRepository<Master, Integer> {
}
