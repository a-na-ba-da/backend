package kr.anabada.anabadaserver.domain.change.respository;

import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyProductRepository extends JpaRepository<MyProduct, Long>, MyProductRepositoryCustom {
}
