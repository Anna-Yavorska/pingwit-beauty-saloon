package pingwit.beautysaloon.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pingwit.beautysaloon.repositiry.model.Master;

@Repository
public interface MasterRepository  extends JpaRepository<Master, Integer> {
}
