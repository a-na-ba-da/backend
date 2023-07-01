package kr.anabada.anabadaserver.domain.change.respository;

import kr.anabada.anabadaserver.domain.change.entity.ChangeRequestProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeRequestProductRepository extends JpaRepository<ChangeRequestProduct, Long> {
}
