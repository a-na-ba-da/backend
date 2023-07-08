package kr.anabada.anabadaserver.domain.change.respository;

import jakarta.persistence.LockModeType;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<MyProduct, Long>, ProductRepositoryCustom {
    List<MyProduct> findAllByOwner(User user);

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<MyProduct> findAllByOwnerAndStatus(User owner, ProductStatus productStatus);
}
